package subastas.subastasbackend.service;

import subastas.subastasbackend.dto.AutoDTO;
import subastas.subastasbackend.model.Auto;
import subastas.subastasbackend.model.Usuario;
import subastas.subastasbackend.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutoService {

    @Autowired
    private AutoRepository autoRepository;

    public List<AutoDTO> getAllAutos() {
        List<Auto> autos = autoRepository.findByEliminadoFalse();
        return autos.stream()
                .map(auto -> new AutoDTO(
                        auto.getId(),
                        auto.getMarca(),
                        auto.getModelo(),
                        auto.getAnio(),
                        auto.getPrecioBase(),
                        auto.getVendido(),
                        auto.getVendedor(),
                        auto.getFechaCreacion()
                ))
                .collect(Collectors.toList());
    }

    public Optional<AutoDTO> getAutoById(Integer id) {
        Optional<Auto> auto = autoRepository.findByIdAndEliminadoFalse(id);
        return auto.map(a -> new AutoDTO(
                a.getId(),
                a.getMarca(),
                a.getModelo(),
                a.getAnio(),
                a.getPrecioBase(),
                a.getVendido(),
                a.getVendedor(),
                a.getFechaCreacion()
        ));
    }

    public AutoDTO createAuto(Auto auto) {
        if (auto.getVendedor() == null) {
            throw new RuntimeException("El vendedor no puede ser nulo");
        }
        auto.setEliminado(false);
        Auto savedAuto = autoRepository.save(auto);
        return new AutoDTO(
                savedAuto.getId(),
                savedAuto.getMarca(),
                savedAuto.getModelo(),
                savedAuto.getAnio(),
                savedAuto.getPrecioBase(),
                savedAuto.getVendido(),
                savedAuto.getVendedor(),
                savedAuto.getFechaCreacion()
        );
    }

    public AutoDTO updateAuto(Integer id, Auto autoDetails) {
        Auto auto = autoRepository.findByIdAndEliminadoFalse(id)
                .orElseThrow(() -> new RuntimeException("Auto no encontrado"));
        auto.setMarca(autoDetails.getMarca());
        auto.setModelo(autoDetails.getModelo());
        auto.setAnio(autoDetails.getAnio());
        auto.setPrecioBase(autoDetails.getPrecioBase());
        auto.setVendido(autoDetails.getVendido());
        auto.setVendedor(autoDetails.getVendedor());
        Auto updatedAuto = autoRepository.save(auto);
        return new AutoDTO(
                updatedAuto.getId(),
                updatedAuto.getMarca(),
                updatedAuto.getModelo(),
                updatedAuto.getAnio(),
                updatedAuto.getPrecioBase(),
                updatedAuto.getVendido(),
                updatedAuto.getVendedor(),
                updatedAuto.getFechaCreacion()
        );
    }

    public void deleteAuto(Integer id) {
        Auto auto = autoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auto no encontrado"));
        auto.setEliminado(true); // Eliminación lógica
        autoRepository.save(auto);
    }
}