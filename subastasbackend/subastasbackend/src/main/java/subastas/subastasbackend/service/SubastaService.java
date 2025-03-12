package subastas.subastasbackend.service;

import jakarta.transaction.Transactional;
import subastas.subastasbackend.dto.SubastaDTO;
import subastas.subastasbackend.model.Auto;
import subastas.subastasbackend.model.Puja;
import subastas.subastasbackend.model.Subasta;
import subastas.subastasbackend.repository.SubastaRepository;
import subastas.subastasbackend.repository.PujaRepository;
import subastas.subastasbackend.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class SubastaService {

    @Autowired
    private SubastaRepository subastaRepository;

    @Autowired
    private AutoRepository autoRepository;

    @Autowired
    private PujaRepository pujaRepository;

    public SubastaService(SubastaRepository subastaRepository) {
        this.subastaRepository = subastaRepository;
    }

    private int calcularTiempoRestante(Subasta subasta) {
        long ahora = System.currentTimeMillis();
        long minutosTranscurridos = (ahora - subasta.getFechaCreacion().getTime()) / 60000;
        int restante = subasta.getDuracion() - (int) minutosTranscurridos;
        return restante > 0 ? restante : 0;
    }

    private void verificarYActualizarEstado(Subasta subasta) {
        if (calcularTiempoRestante(subasta) <= 0) {
            subasta.setEstado("cerrada");
            subastaRepository.save(subasta);
        }
    }

    @Transactional
    public List<SubastaDTO> getAllSubastas() {
        List<Subasta> subastas = subastaRepository.findAll();
        return subastas.stream().map(subasta -> {
            verificarYActualizarEstado(subasta);
            List<Integer> autoIds = subasta.getAutos().stream()
                    .map(Auto::getId)
                    .collect(Collectors.toList());
            return new SubastaDTO(
                    subasta.getId(),
                    autoIds,
                    subasta.getFechaInicio(),
                    subasta.getDuracion(),
                    subasta.getEstado(),
                    subasta.getFechaCreacion(),
                    calcularTiempoRestante(subasta),
                    subasta.getEliminado()
            );
        }).collect(Collectors.toList());
    }

    public Optional<SubastaDTO> getSubastaById(Integer id) {
        Optional<Subasta> subasta = subastaRepository.findById(id);
        return subasta.map(s -> {
            verificarYActualizarEstado(s);
            List<Integer> autoIds = s.getAutos().stream()
                    .map(Auto::getId)
                    .collect(Collectors.toList());
            return new SubastaDTO(
                    s.getId(),
                    autoIds,
                    s.getFechaInicio(),
                    s.getDuracion(),
                    s.getEstado(),
                    s.getFechaCreacion(),
                    calcularTiempoRestante(s),
                    s.getEliminado()
            );
        });
    }
    @Transactional
    public SubastaDTO createSubastaw(Subasta subasta) {
        if (subasta.getAutos() == null || subasta.getAutos().isEmpty()) {
            throw new RuntimeException("Al menos un auto es obligatorio para crear una subasta");
        }

        List<Auto> autosValidos = new ArrayList<>();

        for (Auto auto : subasta.getAutos()) {
            if (auto == null || auto.getId() == null) {
                throw new RuntimeException("Cada auto debe tener un ID");
            }

            // Recuperar el auto desde la base de datos para adjuntarlo a la sesión
            Optional<Auto> autoOpt = autoRepository.findById(auto.getId());
            if (!autoOpt.isPresent()) {
                throw new RuntimeException("El auto con ID " + auto.getId() + " no existe en la base de datos");
            }

            Auto autoValidado = autoOpt.get();

            if (autoValidado.getVendido() != null && autoValidado.getVendido()) {
                throw new RuntimeException("El auto con ID " + auto.getId() + " ya fue vendido y no puede ser subastado nuevamente");
            }

            autosValidos.add(autoValidado);
        }

        // Asignar autos recuperados a la subasta
        subasta.setAutos(autosValidos);

        // Establecer relación bidireccional
        for (Auto auto : autosValidos) {
            auto.setSubasta(subasta);
        }

        if (subasta.getFechaCreacion() == null) {
            subasta.setFechaCreacion(new Date());
        }

        subasta.setEstado("abierta");

        // Guardar subasta en la base de datos
        Subasta savedSubasta = subastaRepository.save(subasta);

        // Convertir a DTO para la respuesta
        List<Integer> autoIds = savedSubasta.getAutos().stream()
                .map(Auto::getId)
                .collect(Collectors.toList());

        return new SubastaDTO(
                savedSubasta.getId(),
                autoIds,
                savedSubasta.getFechaInicio(),
                savedSubasta.getDuracion(),
                savedSubasta.getEstado(),
                savedSubasta.getFechaCreacion(),
                savedSubasta.getEliminado() ? 1 : 0
        );
    }
    public SubastaDTO createSubasta(Subasta subasta) {
        if (subasta.getAutos() == null || subasta.getAutos().isEmpty()) {
            throw new RuntimeException("Al menos un auto es obligatorio para crear una subasta");
        }
        List<Auto> autosValidos = new ArrayList<>();
        for (Auto auto : subasta.getAutos()) {
            if (auto == null || auto.getId() == null) {
                throw new RuntimeException("Cada auto debe tener un ID");
            }
            Optional<Auto> autoOpt = autoRepository.findById(auto.getId());
            if (!autoOpt.isPresent()) {
                throw new RuntimeException("El auto con ID " + auto.getId() + " no existe");
            }
            Auto autoValidado = autoOpt.get();
            if (autoValidado.getVendido() != null && autoValidado.getVendido()) {
                throw new RuntimeException("El auto con ID " + auto.getId() + " ya fue vendido y no puede ser subastado nuevamente");
            }
            autosValidos.add(autoValidado);
        }
        subasta.setAutos(autosValidos);
        for (Auto auto : autosValidos) {
            auto.setSubasta(subasta);
        }
        if (subasta.getFechaCreacion() == null) {
            subasta.setFechaCreacion(new Date());
        }
        subasta.setEstado("abierta");

        Subasta savedSubasta = subastaRepository.save(subasta);
        List<Integer> autoIds = savedSubasta.getAutos().stream()
                .map(Auto::getId)
                .collect(Collectors.toList());
        return new SubastaDTO(
                savedSubasta.getId(),
                autoIds,
                savedSubasta.getFechaInicio(),
                savedSubasta.getDuracion(),
                savedSubasta.getEstado(),
                savedSubasta.getFechaCreacion(),
                calcularTiempoRestante(savedSubasta),
                savedSubasta.getEliminado()
        );
    }

    public SubastaDTO updateSubasta(Integer id, Subasta subastaDetails) {
        Subasta subastaExistente = subastaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subasta no encontrada"));

        List<Auto> autosValidos = new ArrayList<>();
        for (Auto auto : subastaDetails.getAutos()) {
            Optional<Auto> autoOpt = autoRepository.findById(auto.getId());
            if (!autoOpt.isPresent() || autoOpt.get().getVendido()) {
                throw new RuntimeException("El auto con ID " + auto.getId() + " no es válido");
            }
            autosValidos.add(autoOpt.get());
        }
        subastaExistente.getAutos().clear();
        subastaExistente.getAutos().addAll(autosValidos);
        for (Auto auto : autosValidos) {
            auto.setSubasta(subastaExistente);
        }

        subastaExistente.setFechaInicio(subastaDetails.getFechaInicio());
        subastaExistente.setDuracion(subastaDetails.getDuracion());
        if ("abierta".equalsIgnoreCase(subastaDetails.getEstado()) || "pendiente".equalsIgnoreCase(subastaDetails.getEstado())) {
            subastaExistente.setFechaCreacion(new Date());
        }
        subastaExistente.setEstado(subastaDetails.getEstado());

        subastaRepository.save(subastaExistente);

        List<Integer> autoIds = subastaExistente.getAutos().stream()
                .map(Auto::getId)
                .collect(Collectors.toList());
        return new SubastaDTO(
                subastaExistente.getId(),
                autoIds,
                subastaExistente.getFechaInicio(),
                subastaExistente.getDuracion(),
                subastaExistente.getEstado(),
                subastaExistente.getFechaCreacion(),
                calcularTiempoRestante(subastaExistente),
                subastaExistente.getEliminado()
        );
    }

    public void deleteSubasta(Integer id) {
        Subasta subasta = subastaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subasta no encontrada"));
        subasta.setEliminado(true); // Eliminación lógica
        subastaRepository.save(subasta);
    }

    public SubastaDTO finalizarSubasta(Integer id) {
        Subasta subasta = subastaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subasta no encontrada"));

        Optional<Puja> pujaGanadoraOpt = pujaRepository.findTopBySubastaOrderByMontoDesc(subasta);
        if (pujaGanadoraOpt.isPresent()) {
            Puja pujaGanadora = pujaGanadoraOpt.get();
            for (Auto auto : subasta.getAutos()) {
                if (pujaGanadora.getMonto() >= auto.getPrecioBase()) {
                    auto.setVendido(true);
                    autoRepository.save(auto);
                }
            }
        }
        subasta.setEstado("cerrada");
        subastaRepository.save(subasta);

        List<Integer> autoIds = subasta.getAutos().stream()
                .map(Auto::getId)
                .collect(Collectors.toList());
        return new SubastaDTO(
                subasta.getId(),
                autoIds,
                subasta.getFechaInicio(),
                subasta.getDuracion(),
                subasta.getEstado(),
                subasta.getFechaCreacion(),
                calcularTiempoRestante(subasta),
                subasta.getEliminado()
        );
    }

    public SubastaDTO realizarPuja(Integer id) {
        Subasta subasta = subastaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subasta no encontrada"));
        verificarYActualizarEstado(subasta);
        List<Integer> autoIds = subasta.getAutos().stream()
                .map(Auto::getId)
                .collect(Collectors.toList());
        return new SubastaDTO(
                subasta.getId(),
                autoIds,
                subasta.getFechaInicio(),
                subasta.getDuracion(),
                subasta.getEstado(),
                subasta.getFechaCreacion(),
                calcularTiempoRestante(subasta),
                subasta.getEliminado()
        );
    }
}