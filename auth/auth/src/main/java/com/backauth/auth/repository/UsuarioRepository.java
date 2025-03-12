package com.backauth.auth.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.backauth.auth.models.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
}

