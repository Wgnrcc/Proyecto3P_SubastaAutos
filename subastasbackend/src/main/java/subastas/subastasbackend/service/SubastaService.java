package subastas.subastasbackend.service;

import subastas.subastasbackend.dto.SubastaDTO;
import subastas.subastasbackend.model.Subasta;
import subastas.subastasbackend.repository.SubastaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubastaService {

    @Autowired
    private SubastaRepository subastaRepository;

    public List<SubastaDTO> getAllSubastas() {
        List<Subasta> subastas = subastaRepository.findAll();
        return subastas.stream()
                    .map(subasta -> new SubastaDTO(subasta.getId(), subasta.getAuto().getId(), subasta.getFechaInicio(), 
                                                   subasta.getDuracion(), subasta.getEstado(), subasta.getFechaCreacion()))
                    .collect(Collectors.toList());
    }

    public Optional<SubastaDTO> getSubastaById(Integer id) {
        Optional<Subasta> subasta = subastaRepository.findById(id);
        return subasta.map(s -> new SubastaDTO(s.getId(), s.getAuto().getId(), s.getFechaInicio(), s.getDuracion(), 
                                               s.getEstado(), s.getFechaCreacion()));
    }

    public SubastaDTO createSubasta(Subasta subasta) {
        Subasta savedSubasta = subastaRepository.save(subasta);
        return new SubastaDTO(savedSubasta.getId(), savedSubasta.getAuto().getId(), savedSubasta.getFechaInicio(), 
                              savedSubasta.getDuracion(), savedSubasta.getEstado(), savedSubasta.getFechaCreacion());
    }

    public SubastaDTO updateSubasta(Integer id, Subasta subastaDetails) {
        Subasta subasta = subastaRepository.findById(id).orElseThrow(() -> new RuntimeException("Subasta no encontrada"));
        subasta.setAuto(subastaDetails.getAuto());
        subasta.setFechaInicio(subastaDetails.getFechaInicio());
        subasta.setDuracion(subastaDetails.getDuracion());
        subasta.setEstado(subastaDetails.getEstado());
        Subasta updatedSubasta = subastaRepository.save(subasta);
        return new SubastaDTO(updatedSubasta.getId(), updatedSubasta.getAuto().getId(), updatedSubasta.getFechaInicio(), 
                              updatedSubasta.getDuracion(), updatedSubasta.getEstado(), updatedSubasta.getFechaCreacion());
    }

    public void deleteSubasta(Integer id) {
        Subasta subasta = subastaRepository.findById(id).orElseThrow(() -> new RuntimeException("Subasta no encontrada"));
        subastaRepository.delete(subasta);
    }
}
