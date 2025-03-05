package subastas.subastasbackend.security;

import io.jsonwebtoken.*;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class JwtTokenProvider {

    private final String SECRET_KEY = "9878a787fcf3a0126b499bf4c671bb3181aa1ad04d6a7a40c9a688bd022bae2ff1737bc3f12cc15be787fd4ea6e222163e72722e5723ccb1ab33bc73e0c35d8f2d18277a05be3f7e88fabf257c6363e48dc7ccb36c3ce187548e092ce8f2f8d4e1ab26b049d84c24fbdf94a152375727c7df4cf3becc6e76465d76cbd1a0c1a7dfa4759fd89de0366604b3a5091350ed38e3a71d53d4b0d68fca7516a207aaca667c9a5c1fc09203c6064123eec897325e787dfc62b5461cf816e59931bd487ade984984e539e902ec33341c3e4de4395e7a8868803517d0c560dfd08889d5142162a8952748ee5920ecc8b207ce149f676735c09e99e9e60cb3b459eb4678e9";  // Cambia esta clave
    private final long EXPIRATION_TIME = 864_000_000;  // 10 días en milisegundos

    // Método para generar el token JWT
    public String generateToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // Método para obtener el nombre de usuario del token
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Método para resolver el token desde el encabezado Authorization
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Método para validar el token JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Si el token es inválido o expiró, se considera inválido
            return false;
        }
    }

    // Método para obtener la autenticación del token
    public Authentication getAuthentication(String token) {
        String username = getUsernameFromToken(token);
        User principal = new User(username, "", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String token) {
        // TODO Auto-generated method stub
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
