package subastas.subastasbackend.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerarHash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Aquí están las contraseñas que quieres hashear
        String[] passwords = {"hashed_password_1", "hashed_password_2","hashed_password_3", "hashed_password_4"};

        for (String password : passwords) {
            String hashedPassword = encoder.encode(password);
            System.out.println("Hash generado para '" + password + "': " + hashedPassword);
        }
    }
}