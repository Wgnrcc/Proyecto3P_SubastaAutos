package subastas.subastasbackend.webSocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import subastas.subastasbackend.dto.PujaDTO;
import subastas.subastasbackend.dto.SubastaDTO;
import subastas.subastasbackend.model.Puja;
import subastas.subastasbackend.model.Subasta;
import subastas.subastasbackend.model.Auto;
import subastas.subastasbackend.model.Usuario;
import subastas.subastasbackend.repository.SubastaRepository;
import subastas.subastasbackend.repository.UsuarioRepository;
import subastas.subastasbackend.repository.AutoRepository;
import subastas.subastasbackend.service.PujaService;
import subastas.subastasbackend.service.SubastaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class SubastaWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(SubastaWebSocketHandler.class);
    private final SubastaService subastaService;
    private final PujaService pujaService;
    private final SubastaRepository subastaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AutoRepository autoRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, WebSocketSession> sessions = new HashMap<>();

    public SubastaWebSocketHandler(SubastaService subastaService, PujaService pujaService,
                                   SubastaRepository subastaRepository, UsuarioRepository usuarioRepository,
                                   AutoRepository autoRepository) {
        this.subastaService = subastaService;
        this.pujaService = pujaService;
        this.subastaRepository = subastaRepository;
        this.usuarioRepository = usuarioRepository;
        this.autoRepository = autoRepository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.put(session.getId(), session);
        logger.info("Nueva conexión WebSocket establecida: " + session.getId());
        try {
            sendAllSubastas(session);
        } catch (Exception e) {
            logger.error("Error al enviar subastas al conectar WebSocket", e);
            closeSession(session, CloseStatus.SERVER_ERROR);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        logger.info("Mensaje recibido: " + message.getPayload());

        try {
            String email = (String) session.getAttributes().get("email");
            String role = (String) session.getAttributes().get("role");

            logger.info("Email recibido en WebSocket: " + email);
            logger.info("Rol recibido en WebSocket: " + role);

            if (email == null || role == null) {
                throw new IllegalStateException("No se encontró email o rol en la sesión WebSocket.");
            }

            Map<String, String> messageMap = objectMapper.readValue(message.getPayload(), Map.class);
            String action = messageMap.get("action");

            switch (action) {
                case "pujar":
                    realizarPuja(session, message.getPayload(), email, role);
                    break;
                case "crearSubasta":
                    crearSubasta(session, message.getPayload(), email, role);
                    break;
                default:
                    throw new IllegalArgumentException("Acción no soportada: " + action);
            }
        } catch (Exception e) {
            logger.error("Error al manejar mensaje WebSocket", e);
            closeSession(session, CloseStatus.SERVER_ERROR);
        }
    }

    private void realizarPuja(WebSocketSession session, String payload, String email, String role) {
        try {
            if (!"ROLE_COMPRADOR".equals(role)) {
                throw new IllegalStateException("Solo los compradores pueden hacer pujas");
            }

            // Convertir el JSON en un Map para extraer solo los datos de la puja
            Map<String, Object> messageMap = objectMapper.readValue(payload, Map.class);

            // Eliminar la clave "action" para evitar el error de Jackson
            messageMap.remove("action");

            // Convertir el Map a un JSON String sin "action"
            String pujaJson = objectMapper.writeValueAsString(messageMap);

            // Deserializar el JSON limpio en un objeto PujaDTO
            PujaDTO pujaDTO = objectMapper.readValue(pujaJson, PujaDTO.class);

            // Asignar compradorId desde la sesión
            pujaDTO.setCompradorId(getUsuarioIdByEmail(email));

            // Convertir PujaDTO a Puja
            Puja nuevaPuja = convertToPujaEntity(pujaDTO);

            // Guardar la puja
            PujaDTO pujaGuardada = pujaService.createPujaw(nuevaPuja);

            // Enviar la puja a todos los clientes conectados
            broadcast(pujaGuardada);

        } catch (Exception e) {
            logger.error("Error al procesar puja", e);
            closeSession(session, CloseStatus.SERVER_ERROR);
        }
    }



    private void crearSubasta(WebSocketSession session, String payload, String email, String role) {
        try {
            if (!"ROLE_VENDEDOR".equals(role)) {
                throw new IllegalStateException("Solo los vendedores pueden crear subastas");
            }

            // Convertir el JSON en un Map para extraer solo los datos de la subasta
            Map<String, Object> messageMap = objectMapper.readValue(payload, Map.class);

            // Eliminar la clave "action" para evitar el error de Jackson
            messageMap.remove("action");

            // Convertir el Map a un JSON String sin "action"
            String subastaJson = objectMapper.writeValueAsString(messageMap);

            // Deserializar el JSON limpio en un objeto SubastaDTO
            SubastaDTO subastaDTO = objectMapper.readValue(subastaJson, SubastaDTO.class);

            // Convertir SubastaDTO a Subasta
            Subasta nuevaSubasta = convertToSubastaEntity(subastaDTO);

            // Guardar la subasta
            SubastaDTO subastaGuardada = subastaService.createSubastaw(nuevaSubasta);

            // Enviar la subasta creada a todos los clientes conectados
            broadcast(subastaGuardada);

        } catch (Exception e) {
            logger.error("Error al crear subasta", e);
            closeSession(session, CloseStatus.SERVER_ERROR);
        }
    }


    private void sendAllSubastas(WebSocketSession session) {
        try {
            List<SubastaDTO> subastas = subastaService.getAllSubastas();
            for (SubastaDTO subasta : subastas) {
                sendMessage(session, subasta);
            }
        } catch (Exception e) {
            logger.error("Error al enviar subastas al conectar WebSocket", e);
            closeSession(session, CloseStatus.SERVER_ERROR);
        }
    }


    private void sendMessage(WebSocketSession session, Object message) throws IOException {
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
    }

    private void broadcast(Object message) {
        sessions.values().forEach(session -> {
            try {
                sendMessage(session, message);
            } catch (IOException e) {
                logger.error("Error al enviar mensaje de broadcast", e);
            }
        });
    }

    private Integer getUsuarioIdByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
                .getId();
    }

    private Puja convertToPujaEntity(PujaDTO pujaDTO) {
        Puja puja = new Puja();
        puja.setMonto(pujaDTO.getMonto());
        puja.setFechaPuja(new Date());
        puja.setSubasta(subastaRepository.findById(pujaDTO.getSubastaId())
                .orElseThrow(() -> new RuntimeException("Subasta no encontrada")));
        puja.setComprador(usuarioRepository.findById(pujaDTO.getCompradorId())
                .orElseThrow(() -> new RuntimeException("Comprador no encontrado")));
        return puja;
    }

    private Subasta convertToSubastaEntity(SubastaDTO subastaDTO) {
        Subasta subasta = new Subasta();
        subasta.setFechaInicio(subastaDTO.getFechaInicio());
        subasta.setDuracion(subastaDTO.getDuracion());
        subasta.setEstado("abierta");
        subasta.setFechaCreacion(new Date());

        // Validar y recuperar autos desde la base de datos
        List<Auto> autos = subastaDTO.getAutoIds() != null ?
                subastaDTO.getAutoIds().stream()
                        .map(id -> autoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("El auto con ID " + id + " no existe en la base de datos"))
                        )
                        .collect(Collectors.toList())
                : new ArrayList<>();

        // Asignar autos a la subasta
        subasta.setAutos(autos);

        return subasta;
    }


    private void closeSession(WebSocketSession session, CloseStatus status) {
        try {
            session.close(status);
        } catch (IOException e) {
            logger.error("Error al cerrar la sesión WebSocket", e);
        }
    }
}
