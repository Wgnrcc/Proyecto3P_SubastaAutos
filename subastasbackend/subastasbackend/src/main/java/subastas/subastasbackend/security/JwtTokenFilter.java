package subastas.subastasbackend.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = extractJwtFromRequest(request);
            System.out.println("Token recibido: " + token);  // Log para verificar el token recibido

            if (token != null && jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUsernameFromToken(token);
                System.out.println("Usuario autenticado: " + username); // Log para verificación del usuario

                // Extraer el rol del token
                String role = jwtTokenProvider.getRoleFromToken(token);
                System.out.println("Rol del usuario: " + role); // Log para verificar el rol

                // Crear UserDetails con el rol
                UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                        username, "", Collections.singletonList(new SimpleGrantedAuthority(role)));

                // Crear la autenticación
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("Token inválido o nulo"); // Log si el token es inválido
            }
        } catch (Exception e) {
            System.err.println("Error en el filtro JWT: " + e.getMessage()); // Log de errores
        }

        filterChain.doFilter(request, response); // Continuar el filtro
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Extraer el token de "Bearer <token>"
        }
        return null;
    }
}