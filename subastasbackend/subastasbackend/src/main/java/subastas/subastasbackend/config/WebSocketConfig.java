package subastas.subastasbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import subastas.subastasbackend.webSocket.JwtHandshakeInterceptor;
import subastas.subastasbackend.webSocket.SubastaWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final SubastaWebSocketHandler subastaWebSocketHandler;
    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;

    public WebSocketConfig(SubastaWebSocketHandler subastaWebSocketHandler, JwtHandshakeInterceptor jwtHandshakeInterceptor) {
        this.subastaWebSocketHandler = subastaWebSocketHandler;
        this.jwtHandshakeInterceptor = jwtHandshakeInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(subastaWebSocketHandler, "/ws/subastas")
                .addInterceptors(jwtHandshakeInterceptor)
                .setAllowedOrigins("*");
    }
}