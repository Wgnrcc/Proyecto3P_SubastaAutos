package subastas.subastasbackend.controller;

import subastas.subastasbackend.dto.PujaDTO;
import subastas.subastasbackend.model.Puja;
import subastas.subastasbackend.service.PujaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pujas")
public class PujaController {

    @Autowired
    private PujaService pujaService;

    // Obtener todas las Pujas
    @GetMapping
    public List<PujaDTO> getAllPujas() {
        return pujaService.getAllPujas();
    }

    // Obtener Puja por ID
    @GetMapping("/{id}")
    public ResponseEntity<PujaDTO> getPujaById(@PathVariable Integer id) {
        return pujaService.getPujaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva Puja
    @PostMapping
    public ResponseEntity<PujaDTO> createPuja(@RequestBody Puja puja) {
        PujaDTO pujaDTO = pujaService.createPuja(puja);
        return ResponseEntity.ok(pujaDTO);
    }

    // Actualizar Puja
    @PutMapping("/{id}")
    public ResponseEntity<PujaDTO> updatePuja(@PathVariable Integer id, @RequestBody Puja pujaDetails) {
        return ResponseEntity.ok(pujaService.updatePuja(id, pujaDetails));
    }

    // Eliminar Puja
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePuja(@PathVariable Integer id) {
        pujaService.deletePuja(id);
        return ResponseEntity.noContent().build();
    }
}
