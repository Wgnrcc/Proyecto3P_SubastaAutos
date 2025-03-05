package subastas.subastasbackend.controller;

import subastas.subastasbackend.dto.SubastaDTO;
import subastas.subastasbackend.model.Subasta;
import subastas.subastasbackend.service.SubastaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subastas")
public class SubastaController {

    @Autowired
    private SubastaService subastaService;

    // Obtener todas las Subastas
    @GetMapping
    public List<SubastaDTO> getAllSubastas() {
        return subastaService.getAllSubastas();
    }

    // Obtener Subasta por ID
    @GetMapping("/{id}")
    public ResponseEntity<SubastaDTO> getSubastaById(@PathVariable Integer id) {
        return subastaService.getSubastaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva Subasta
    @PostMapping
    public ResponseEntity<SubastaDTO> createSubasta(@RequestBody Subasta subasta) {
        SubastaDTO subastaDTO = subastaService.createSubasta(subasta);
        return ResponseEntity.ok(subastaDTO);
    }

    // Actualizar Subasta
    @PutMapping("/{id}")
    public ResponseEntity<SubastaDTO> updateSubasta(@PathVariable Integer id, @RequestBody Subasta subastaDetails) {
        return ResponseEntity.ok(subastaService.updateSubasta(id, subastaDetails));
    }

    // Eliminar Subasta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubasta(@PathVariable Integer id) {
        subastaService.deleteSubasta(id);
        return ResponseEntity.noContent().build();
    }
}
