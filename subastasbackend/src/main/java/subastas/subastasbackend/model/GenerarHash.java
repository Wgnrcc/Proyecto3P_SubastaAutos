package subastas.subastasbackend.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerarHash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "hashed_password_1"; // Contraseña en texto plano
        String hashedPassword = encoder.encode(password);
        System.out.println("Hash generado: " + hashedPassword);
    }
}