package subastas.subastasbackend.repository;

import subastas.subastasbackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Aquí puedes agregar otros métodos si lo necesitas, como búsqueda por email, etc.
}
