package ec.edu.espe.Proyecto3P.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;

@Entity
@Table(name = "administradores")
public class AdministradoresEntity extends UsuarioEntity {

    public AdministradoresEntity() {

    }
    public AdministradoresEntity(String username, String password, String email) {
        super.setUsername(username);
        super.setPassword(password);
        super.setEmail(email);
    }

}


