package subastas.subastasbackend.service;

import subastas.subastasbackend.dto.UsuarioDTO;
import subastas.subastasbackend.model.Usuario;
import subastas.subastasbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Convertir Usuario a UsuarioDTO
    public UsuarioDTO convertirAUsuarioDTO(Usuario usuario) {
        return new UsuarioDTO(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getEmail(),
            usuario.getRol().getNombre(),  // Obtener nombre del rol
            usuario.getFechaCreacion(),
            usuario.getActivo()
        );
    }

    // Obtener un Usuario por ID y convertirlo a UsuarioDTO
    public Optional<UsuarioDTO> getUsuarioById(Integer id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(this::convertirAUsuarioDTO);
    }

    // Obtener todos los Usuarios y convertirlos a UsuarioDTO
    public List<UsuarioDTO> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                       .map(this::convertirAUsuarioDTO)
                       .collect(Collectors.toList());
    }

    // Crear un nuevo Usuario
    public UsuarioDTO createUsuario(Usuario usuario) {
        Usuario usuarioCreado = usuarioRepository.save(usuario);
        return convertirAUsuarioDTO(usuarioCreado);
    }

    // Actualizar Usuario existente
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

    // Eliminar Usuario
    public void deleteUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                                           .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }
}
