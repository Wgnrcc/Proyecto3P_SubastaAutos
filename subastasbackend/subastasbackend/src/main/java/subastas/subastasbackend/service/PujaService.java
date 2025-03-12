package subastas.subastasbackend.service;

import org.hibernate.Hibernate;
import subastas.subastasbackend.dto.PujaDTO;
import subastas.subastasbackend.model.Puja;
import subastas.subastasbackend.model.Subasta;
import subastas.subastasbackend.model.Auto;
import subastas.subastasbackend.model.Usuario;
import subastas.subastasbackend.repository.PujaRepository;
import subastas.subastasbackend.repository.SubastaRepository;
import subastas.subastasbackend.repository.AutoRepository;
import subastas.subastasbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class PujaService {

    @Autowired
    private PujaRepository pujaRepository;

    @Autowired
    private SubastaRepository subastaRepository;

    @Autowired
    private AutoRepository autoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<PujaDTO> getAllPujas() {
        List<Puja> pujas = pujaRepository.findAll().stream()
                .filter(puja -> !puja.getEliminado()) // Filtra las eliminadas lógicamente
                .toList();
        return pujas.stream()
                .map(puja -> new PujaDTO(
                        puja.getId(),
                        puja.getSubasta().getId(),
                        puja.getComprador().getId(),
                        puja.getMonto(),
                        puja.getFechaPuja(),
                        puja.getEliminado()))
                .toList();
    }

    public Optional<PujaDTO> getPujaById(Integer id) {
        Optional<Puja> puja = pujaRepository.findById(id).filter(p -> !p.getEliminado());
        return puja.map(p -> new PujaDTO(
                p.getId(),
                p.getSubasta().getId(),
                p.getComprador().getId(),
                p.getMonto(),
                p.getFechaPuja(),
                p.getEliminado()));
    }

    @Transactional
    public PujaDTO createPuja(Puja puja) {
        if (puja.getSubasta() == null || puja.getComprador() == null) {
            throw new RuntimeException("Subasta o Comprador no pueden ser nulos");
        }

        Subasta subasta = puja.getSubasta();
        if (!"abierta".equalsIgnoreCase(subasta.getEstado())) {
            throw new RuntimeException("La subasta no está abierta para pujas");
        }

        for (Auto auto : subasta.getAutos()) {
            if (auto.getVendedor().getId().equals(puja.getComprador().getId())) {
                throw new RuntimeException("El comprador no puede pujar en autos que él mismo publicó");
            }
        }

        Optional<Puja> pujaMasAltaOpt = pujaRepository.findTopBySubastaOrderByMontoDesc(subasta);
        if (pujaMasAltaOpt.isPresent()) {
            Puja pujaMasAlta = pujaMasAltaOpt.get();
            if (puja.getMonto() <= pujaMasAlta.getMonto()) {
                throw new RuntimeException("La puja debe ser mayor que la puja actual más alta: " + pujaMasAlta.getMonto());
            }
        }

        if (puja.getFechaPuja() == null) {
            puja.setFechaPuja(new Date());
        }

        Puja savedPuja = pujaRepository.save(puja);
        return new PujaDTO(
                savedPuja.getId(),
                savedPuja.getSubasta().getId(),
                savedPuja.getComprador().getId(),
                savedPuja.getMonto(),
                savedPuja.getFechaPuja(),
                savedPuja.getEliminado());
    }

    @Transactional
    public PujaDTO createPujaw(Puja puja) {
        if (puja.getSubasta() == null || puja.getComprador() == null) {
            throw new RuntimeException("Subasta o Comprador no pueden ser nulos");
        }

        // Recuperar la subasta desde la base de datos con los autos cargados
        Subasta subasta = subastaRepository.findById(puja.getSubasta().getId())
                .orElseThrow(() -> new RuntimeException("Subasta no encontrada"));

        // Cargar la relación de autos para evitar LazyInitializationException
        Hibernate.initialize(subasta.getAutos());

        if (!"abierta".equalsIgnoreCase(subasta.getEstado())) {
            throw new RuntimeException("La subasta no está abierta para pujas");
        }

        // Verificar que el comprador no sea el vendedor del auto
        for (Auto auto : subasta.getAutos()) {
            if (auto.getVendedor().getId().equals(puja.getComprador().getId())) {
                throw new RuntimeException("El comprador no puede pujar en autos que él mismo publicó");
            }
        }

        // Verificar si hay una puja más alta existente
        Optional<Puja> pujaMasAltaOpt = pujaRepository.findTopBySubastaOrderByMontoDesc(subasta);
        if (pujaMasAltaOpt.isPresent() && puja.getMonto() <= pujaMasAltaOpt.get().getMonto()) {
            throw new RuntimeException("La puja debe ser mayor que la puja actual más alta: " + pujaMasAltaOpt.get().getMonto());
        }

        if (puja.getFechaPuja() == null) {
            puja.setFechaPuja(new Date());
        }

        Puja savedPuja = pujaRepository.save(puja);
        return new PujaDTO(
                savedPuja.getId(),
                savedPuja.getSubasta().getId(),
                savedPuja.getComprador().getId(),
                savedPuja.getMonto(),
                savedPuja.getFechaPuja(),
                savedPuja.getEliminado()
        );
    }


    public PujaDTO updatePuja(Integer id, Puja pujaDetails) {
        Puja puja = pujaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Puja no encontrada"));
        puja.setSubasta(pujaDetails.getSubasta());
        puja.setComprador(pujaDetails.getComprador());
        puja.setMonto(pujaDetails.getMonto());
        Puja updatedPuja = pujaRepository.save(puja);
        return new PujaDTO(
                updatedPuja.getId(),
                updatedPuja.getSubasta().getId(),
                updatedPuja.getComprador().getId(),
                updatedPuja.getMonto(),
                updatedPuja.getFechaPuja(),
                updatedPuja.getEliminado());
    }

    public void deletePuja(Integer id) {
        Puja puja = pujaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Puja no encontrada"));
        puja.setEliminado(true);  // Eliminación lógica
        pujaRepository.save(puja);
    }
}