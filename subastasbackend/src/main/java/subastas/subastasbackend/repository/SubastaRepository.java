package subastas.subastasbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import subastas.subastasbackend.model.Subasta;

public interface SubastaRepository extends JpaRepository<Subasta, Integer> {

}
