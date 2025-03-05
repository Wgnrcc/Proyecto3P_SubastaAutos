package subastas.subastasbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import subastas.subastasbackend.model.Auto;

public interface AutoRepository extends JpaRepository<Auto, Integer> {
    
}
