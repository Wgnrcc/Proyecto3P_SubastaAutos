package subastas.subastasbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import subastas.subastasbackend.model.Auto;
import java.util.List;
import java.util.Optional;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Integer> {
    List<Auto> findByEliminadoFalse();
    Optional<Auto> findByIdAndEliminadoFalse(Integer id);
}