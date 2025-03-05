package subastas.subastasbackend.controller;

import subastas.subastasbackend.dto.AutoDTO;
import subastas.subastasbackend.model.Auto;
import subastas.subastasbackend.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autos")
public class AutoController {

    @Autowired
    private AutoService autoService;

    // Obtener todos los Autos
    @GetMapping
    public List<AutoDTO> getAllAutos() {
        return autoService.getAllAutos();
    }

    // Obtener Auto por ID
    @GetMapping("/{id}")
    public ResponseEntity<AutoDTO> getAutoById(@PathVariable Integer id) {
        return autoService.getAutoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo Auto
    @PostMapping
    public ResponseEntity<AutoDTO> createAuto(@RequestBody Auto auto) {
        AutoDTO autoDTO = autoService.createAuto(auto);
        return ResponseEntity.ok(autoDTO);
    }

    // Actualizar Auto
    @PutMapping("/{id}")
    public ResponseEntity<AutoDTO> updateAuto(@PathVariable Integer id, @RequestBody Auto autoDetails) {
        return ResponseEntity.ok(autoService.updateAuto(id, autoDetails));
    }

    // Eliminar Auto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuto(@PathVariable Integer id) {
        autoService.deleteAuto(id);
        return ResponseEntity.noContent().build();
    }
}
