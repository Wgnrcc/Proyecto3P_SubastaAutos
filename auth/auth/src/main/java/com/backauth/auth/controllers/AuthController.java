package com.backauth.auth.controllers;

import com.backauth.auth.dto.RegisterRequest;
import com.backauth.auth.models.Rol;
import com.backauth.auth.models.Usuario;
import com.backauth.auth.repository.RolRepository;
import com.backauth.auth.repository.UsuarioRepository;
import com.backauth.auth.services.CustomUserDetailsService;
import com.backauth.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Usuario usuario) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getPasswordHash())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
        Usuario usuarioAutenticado = userDetailsService.getUsuarioByEmail(usuario.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails, usuarioAutenticado.getRol().getNombre());

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("rol", usuarioAutenticado.getRol().getNombre());

        return ResponseEntity.ok(response);
    }

    // Nuevo endpoint para registrar un usuario usando el DTO RegisterRequest
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        // Verifica si ya existe un usuario con el mismo email
        Optional<Usuario> existingUser = usuarioRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("El usuario con este correo ya existe.");
        }

        // Buscar el rol usando el string recibido
        Optional<Rol> rolOpt = rolRepository.findByNombre(request.getRol());
        if (!rolOpt.isPresent()) {
            return ResponseEntity.badRequest().body("El rol proporcionado no es válido.");
        }

        // Crear el usuario y encriptar la contraseña
        Usuario newUsuario = new Usuario();
        newUsuario.setNombre(request.getNombre());
        newUsuario.setEmail(request.getEmail());
        newUsuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        newUsuario.setRol(rolOpt.get());
        newUsuario.setActivo(true);
        newUsuario.setFechaCreacion(new java.util.Date());

        Usuario usuarioCreado = usuarioRepository.save(newUsuario);
        // Opcional: no devolver la contraseña
        usuarioCreado.setPasswordHash(null);

        return ResponseEntity.ok(usuarioCreado);
    }
}
