package subastas.subastasbackend.controller;

import subastas.subastasbackend.dto.AutoDTO;
import subastas.subastasbackend.model.Auto;
import subastas.subastasbackend.model.Usuario;
import subastas.subastasbackend.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subastas.subastasbackend.repository.UsuarioRepository;
import java.util.Optional;

@RestController
@RequestMapping("/api/autos")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AutoController {

    @Autowired
    private AutoService autoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<?> getAllAutos() {
        try {
            return ResponseEntity.ok(autoService.getAllAutos());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener los autos: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutoDTO> getAutoById(@PathVariable Integer id) {
        try {
            return autoService.getAutoById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<AutoDTO> createAuto(@RequestBody Auto auto) {
        try {
            if (auto.getVendedor() == null || auto.getVendedor().getId() == null) {
                return ResponseEntity.badRequest().body(null);
            }

            Optional<Usuario> vendedorOpt = usuarioRepository.findById(auto.getVendedor().getId());
            if (!vendedorOpt.isPresent()) {
                return ResponseEntity.status(400).body(null);
            }

            auto.setVendedor(vendedorOpt.get());
            AutoDTO autoDTO = autoService.createAuto(auto);
            return ResponseEntity.ok(autoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutoDTO> updateAuto(@PathVariable Integer id, @RequestBody Auto autoDetails) {
        try {
            return ResponseEntity.ok(autoService.updateAuto(id, autoDetails));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuto(@PathVariable Integer id) {
        try {
            autoService.deleteAuto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}