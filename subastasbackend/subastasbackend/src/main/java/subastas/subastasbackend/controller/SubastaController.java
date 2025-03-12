package subastas.subastasbackend.controller;

import subastas.subastasbackend.dto.SubastaDTO;
import subastas.subastasbackend.model.Subasta;
import subastas.subastasbackend.service.SubastaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subastas")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class SubastaController {

    @Autowired
    private SubastaService subastaService;

    @GetMapping
    public ResponseEntity<List<SubastaDTO>> getAllSubastas() {
        try {
            List<SubastaDTO> subastas = subastaService.getAllSubastas();
            return ResponseEntity.ok(subastas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubastaDTO> getSubastaById(@PathVariable Integer id) {
        try {
            Optional<SubastaDTO> subastaDTO = subastaService.getSubastaById(id);
            return subastaDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<SubastaDTO> createSubasta(@RequestBody Subasta subasta) {
        try {
            SubastaDTO subastaDTO = subastaService.createSubasta(subasta);
            return ResponseEntity.ok(subastaDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubastaDTO> updateSubasta(@PathVariable Integer id, @RequestBody Subasta subastaDetails) {
        try {
            SubastaDTO subastaDTO = subastaService.updateSubasta(id, subastaDetails);
            return ResponseEntity.ok(subastaDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}/puja")
    public ResponseEntity<SubastaDTO> realizarPuja(@PathVariable Integer id) {
        try {
            SubastaDTO subastaDTO = subastaService.realizarPuja(id);
            return ResponseEntity.ok(subastaDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubasta(@PathVariable Integer id) {
        try {
            subastaService.deleteSubasta(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<SubastaDTO> finalizarSubasta(@PathVariable Integer id) {
        try {
            SubastaDTO subastaDTO = subastaService.finalizarSubasta(id);
            return ResponseEntity.ok(subastaDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}