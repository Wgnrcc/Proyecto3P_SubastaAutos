package subastas.subastasbackend.service;

import subastas.subastasbackend.dto.UsuarioDTO;
import subastas.subastasbackend.model.Rol;
import subastas.subastasbackend.model.Usuario;
import subastas.subastasbackend.repository.RolRepository;
import subastas.subastasbackend.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UsuarioDTO convertirAUsuarioDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol().getNombre(),
                usuario.getFechaCreacion(),
                usuario.getActivo(),
                usuario.getEliminado()
        );
    }

    public Optional<UsuarioDTO> getUsuarioById(Integer id) {
        return usuarioRepository.findById(id).filter(Usuario::getActivo).map(this::convertirAUsuarioDTO);
    }

    public Optional<Usuario> getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email).filter(Usuario::getActivo);
    }

    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .filter(Usuario::getActivo)
                .map(this::convertirAUsuarioDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO createUsuario(Usuario usuario) {
        Optional<Usuario> existingUser = usuarioRepository.findByEmail(usuario.getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("El usuario con este correo ya existe.");
        }

        usuario.setPasswordHash(passwordEncoder.encode(usuario.getPasswordHash()));

        if (usuario.getRol() == null) {
            Rol defaultRol = rolRepository.findByNombre("comprador")
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            usuario.setRol(defaultRol);
        }

        usuario.setActivo(true);
        usuario.setFechaCreacion(new java.util.Date());

        Usuario usuarioCreado = usuarioRepository.save(usuario);
        return convertirAUsuarioDTO(usuarioCreado);
    }

    public UsuarioDTO updateUsuario(Integer id, Usuario usuarioDetails) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(usuarioDetails.getNombre());
        usuario.setEmail(usuarioDetails.getEmail());
        usuario.setRol(usuarioDetails.getRol());
        usuario.setActivo(usuarioDetails.getActivo());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return convertirAUsuarioDTO(usuarioActualizado);
    }

    public void deleteUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
}