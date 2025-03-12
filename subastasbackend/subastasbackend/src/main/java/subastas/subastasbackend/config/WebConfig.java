package subastas.subastasbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081", "http://localhost:4200")  // Permitir los orígenes del frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Asegurar que OPTIONS está permitido
                .allowedHeaders("*")  // Permitir todos los headers
                .allowCredentials(true); // Permitir credenciales como cookies o tokens
    }
}
