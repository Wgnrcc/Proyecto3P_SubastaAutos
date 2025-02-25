package ec.edu.espe.Proyecto3P.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "compradores")
public class CompradoresEntity extends UsuarioEntity {


    @OneToMany(mappedBy = "comprador", cascade = CascadeType.ALL)
    private List<PujaEntity> pujasRealizadas;

    // Constructor vacío
    public CompradoresEntity() {
    }

    // Constructor con parámetros
    public CompradoresEntity(String username, String password, String email) {
        super.setUsername(username);
        super.setPassword(password);
        super.setEmail(email);
    }

    // Getters y Setters solo si se necesitan
    public List<PujaEntity> getPujasRealizadas() {
        return pujasRealizadas;
    }

    public void setPujasRealizadas(List<PujaEntity> pujasRealizadas) {
        this.pujasRealizadas = pujasRealizadas;
    }
}