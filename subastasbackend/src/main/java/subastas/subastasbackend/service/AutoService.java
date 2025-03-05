package subastas.subastasbackend.service;

import subastas.subastasbackend.dto.AutoDTO;
import subastas.subastasbackend.model.Auto;
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
        List<Auto> autos = autoRepository.findAll();
        return autos.stream()
                    .map(auto -> new AutoDTO(auto.getId(), auto.getMarca(), auto.getModelo(), auto.getAnio(), 
                                             auto.getPrecioBase(), auto.getVendido(), auto.getVendedor().getId(), auto.getFechaCreacion()))
                    .collect(Collectors.toList());
    }

    public Optional<AutoDTO> getAutoById(Integer id) {
        Optional<Auto> auto = autoRepository.findById(id);
        return auto.map(a -> new AutoDTO(a.getId(), a.getMarca(), a.getModelo(), a.getAnio(), 
                                         a.getPrecioBase(), a.getVendido(), a.getVendedor().getId(), a.getFechaCreacion()));
    }

    public AutoDTO createAuto(Auto auto) {
        Auto savedAuto = autoRepository.save(auto);
        return new AutoDTO(savedAuto.getId(), savedAuto.getMarca(), savedAuto.getModelo(), savedAuto.getAnio(), 
                           savedAuto.getPrecioBase(), savedAuto.getVendido(), savedAuto.getVendedor().getId(), savedAuto.getFechaCreacion());
    }

    public AutoDTO updateAuto(Integer id, Auto autoDetails) {
        Auto auto = autoRepository.findById(id).orElseThrow(() -> new RuntimeException("Auto no encontrado"));
        auto.setMarca(autoDetails.getMarca());
        auto.setModelo(autoDetails.getModelo());
        auto.setAnio(autoDetails.getAnio());
        auto.setPrecioBase(autoDetails.getPrecioBase());
        auto.setVendido(autoDetails.getVendido());
        auto.setVendedor(autoDetails.getVendedor());
        Auto updatedAuto = autoRepository.save(auto);
        return new AutoDTO(updatedAuto.getId(), updatedAuto.getMarca(), updatedAuto.getModelo(), updatedAuto.getAnio(), 
                           updatedAuto.getPrecioBase(), updatedAuto.getVendido(), updatedAuto.getVendedor().getId(), updatedAuto.getFechaCreacion());
    }

    public void deleteAuto(Integer id) {
        Auto auto = autoRepository.findById(id).orElseThrow(() -> new RuntimeException("Auto no encontrado"));
        autoRepository.delete(auto);
    }
}
