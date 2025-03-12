package subastas.subastasbackend.config;

import subastas.subastasbackend.security.JwtTokenFilter;
import subastas.subastasbackend.security.JwtTokenProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Deshabilitar CSRF
                .cors().configurationSource(corsConfigurationSource()) // Configurar CORS
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sin sesiones
                .and()
                .authorizeHttpRequests()
                // Permitir acceso público a login y registro
                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()

                // Permitir conexiones WebSocket
                .requestMatchers("/ws/**").permitAll()

                // Permisos para administradores
                .requestMatchers("/api/admin/**").hasRole("ADMINISTRADOR")

                // Permisos para vendedores
                .requestMatchers(HttpMethod.POST, "/api/autos/**").hasRole("VENDEDOR") // Crear autos
                .requestMatchers(HttpMethod.PUT, "/api/autos/**").hasRole("VENDEDOR") // Actualizar autos
                .requestMatchers(HttpMethod.DELETE, "/api/autos/**").hasRole("VENDEDOR") // Eliminar autos
                .requestMatchers(HttpMethod.GET, "/api/autos/**").hasAnyRole("VENDEDOR", "COMPRADOR", "ADMINISTRADOR") // Listar autos

                // Permisos para compradores
                .requestMatchers(HttpMethod.POST, "/api/pujas/**").hasRole("COMPRADOR") // Crear pujas
                .requestMatchers(HttpMethod.GET, "/api/pujas/**").hasAnyRole("COMPRADOR", "VENDEDOR", "ADMINISTRADOR") // Listar pujas

                // Permisos para subastas
                .requestMatchers(HttpMethod.POST, "/api/subastas/**").hasRole("VENDEDOR") // Crear subastas
                .requestMatchers(HttpMethod.GET, "/api/subastas/**").hasAnyRole("COMPRADOR", "VENDEDOR", "ADMINISTRADOR") // Listar subastas

                // Permisos para usuarios
                .requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasRole("ADMINISTRADOR") // Listar usuarios (solo admin)
                .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasAnyRole("COMPRADOR", "VENDEDOR", "ADMINISTRADOR") // Actualizar perfil de usuario
                .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMINISTRADOR") // Eliminación lógica de usuarios (solo admin)

                // Cualquier otra solicitud debe estar autenticada
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Permitir solicitudes desde el frontend
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type")); // Headers permitidos
        configuration.setAllowCredentials(true); // Permitir credenciales
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplicar a todas las rutas
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}