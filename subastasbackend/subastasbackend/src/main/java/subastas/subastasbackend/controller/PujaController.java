package subastas.subastasbackend.controller;

import subastas.subastasbackend.dto.PujaDTO;
import subastas.subastasbackend.model.Puja;
import subastas.subastasbackend.model.Subasta;
import subastas.subastasbackend.model.Usuario;
import subastas.subastasbackend.service.PujaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subastas.subastasbackend.repository.PujaRepository;
import subastas.subastasbackend.repository.SubastaRepository;
import subastas.subastasbackend.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pujas")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class PujaController {

    @Autowired
    private PujaService pujaService;

    @Autowired
    private SubastaRepository subastaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PujaRepository pujaRepository;

    @GetMapping
    public ResponseEntity<List<PujaDTO>> getAllPujas() {
        try {
            List<PujaDTO> pujas = pujaService.getAllPujas();
            return ResponseEntity.ok(pujas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PujaDTO> getPujaById(@PathVariable Integer id) {
        try {
            Optional<PujaDTO> pujaDTO = pujaService.getPujaById(id);
            return pujaDTO.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<PujaDTO> createPuja(@RequestBody PujaDTO pujaDTO) {
        try {
            Subasta subasta = subastaRepository.findById(pujaDTO.getSubastaId())
                    .orElseThrow(() -> new RuntimeException("Subasta no encontrada"));
            Usuario comprador = usuarioRepository.findById(pujaDTO.getCompradorId())
                    .orElseThrow(() -> new RuntimeException("Comprador no encontrado"));
            Puja puja = new Puja();
            puja.setSubasta(subasta);
            puja.setComprador(comprador);
            puja.setMonto(pujaDTO.getMonto());
            puja.setFechaPuja(pujaDTO.getFechaPuja());
            PujaDTO savedPujaDTO = pujaService.createPuja(puja);
            return ResponseEntity.ok(savedPujaDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PujaDTO> actualizarPuja(@PathVariable Integer id, @RequestBody PujaDTO pujaDTO) {
        try {
            if (pujaDTO.getSubastaId() == null || pujaDTO.getCompradorId() == null) {
                return ResponseEntity.badRequest().body(null);
            }

            Puja puja = pujaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Puja no encontrada"));

            Optional<Subasta> subastaOpt = subastaRepository.findById(pujaDTO.getSubastaId());
            Optional<Usuario> compradorOpt = usuarioRepository.findById(pujaDTO.getCompradorId());

            if (!subastaOpt.isPresent() || !compradorOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            puja.setMonto(pujaDTO.getMonto());
            puja.setSubasta(subastaOpt.get());
            puja.setComprador(compradorOpt.get());
            puja.setFechaPuja(pujaDTO.getFechaPuja());

            Puja updatedPuja = pujaRepository.save(puja);

            PujaDTO updatedPujaDTO = new PujaDTO(
                    updatedPuja.getId(),
                    updatedPuja.getSubasta().getId(),
                    updatedPuja.getComprador().getId(),
                    updatedPuja.getMonto(),
                    updatedPuja.getFechaPuja()
            );

            return ResponseEntity.ok(updatedPujaDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePuja(@PathVariable Integer id) {
        try {
            pujaService.deletePuja(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}