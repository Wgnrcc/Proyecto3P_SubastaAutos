package subastas.subastasbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import subastas.subastasbackend.model.Subasta;

import java.util.List;

@Repository
public interface SubastaRepository extends JpaRepository<Subasta, Integer> {

    @Query("SELECT s FROM Subasta s LEFT JOIN FETCH s.autos WHERE s.eliminado = false")
    List<Subasta> findAllWithAutos();

}
