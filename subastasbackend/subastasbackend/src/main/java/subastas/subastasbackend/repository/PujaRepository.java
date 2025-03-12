package subastas.subastasbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import subastas.subastasbackend.model.Puja;
import subastas.subastasbackend.model.Subasta;
import java.util.Optional;
import java.util.List;

@Repository
public interface PujaRepository extends JpaRepository<Puja, Integer> {
    Optional<Puja> findTopBySubastaOrderByMontoDesc(Subasta subasta);
    List<Puja> findBySubasta(Subasta subasta);
}