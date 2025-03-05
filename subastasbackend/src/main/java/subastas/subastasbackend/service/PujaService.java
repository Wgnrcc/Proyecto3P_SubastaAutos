package subastas.subastasbackend.service;

import subastas.subastasbackend.dto.PujaDTO;
import subastas.subastasbackend.model.Puja;
import subastas.subastasbackend.repository.PujaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PujaService {

    @Autowired
    private PujaRepository pujaRepository;

    public List<PujaDTO> getAllPujas() {
        List<Puja> pujas = pujaRepository.findAll();
        return pujas.stream()
                    .map(puja -> new PujaDTO(puja.getId(), puja.getSubasta().getId(), puja.getComprador().getId(), puja.getMonto(), puja.getFechaPuja()))
                    .collect(Collectors.toList());
    }

    public Optional<PujaDTO> getPujaById(Integer id) {
        Optional<Puja> puja = pujaRepository.findById(id);
        return puja.map(p -> new PujaDTO(p.getId(), p.getSubasta().getId(), p.getComprador().getId(), p.getMonto(), p.getFechaPuja()));
    }

    public PujaDTO createPuja(Puja puja) {
        Puja savedPuja = pujaRepository.save(puja);
        return new PujaDTO(savedPuja.getId(), savedPuja.getSubasta().getId(), savedPuja.getComprador().getId(), savedPuja.getMonto(), savedPuja.getFechaPuja());
    }

    public PujaDTO updatePuja(Integer id, Puja pujaDetails) {
        Puja puja = pujaRepository.findById(id).orElseThrow(() -> new RuntimeException("Puja no encontrada"));
        puja.setSubasta(pujaDetails.getSubasta());
        puja.setComprador(pujaDetails.getComprador());
        puja.setMonto(pujaDetails.getMonto());
        Puja updatedPuja = pujaRepository.save(puja);
        return new PujaDTO(updatedPuja.getId(), updatedPuja.getSubasta().getId(), updatedPuja.getComprador().getId(), updatedPuja.getMonto(), updatedPuja.getFechaPuja());
    }

    public void deletePuja(Integer id) {
        Puja puja = pujaRepository.findById(id).orElseThrow(() -> new RuntimeException("Puja no encontrada"));
        pujaRepository.delete(puja);
    }
}
