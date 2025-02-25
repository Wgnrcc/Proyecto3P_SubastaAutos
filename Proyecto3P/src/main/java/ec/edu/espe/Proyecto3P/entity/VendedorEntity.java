package ec.edu.espe.Proyecto3P.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "vendedores")
public class VendedorEntity extends UsuarioEntity {

    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL)
    private List<AutoEntity> autosVendidos;

    // Constructor vacío
    public VendedorEntity() {
    }

    // Constructor con parámetros
    public VendedorEntity(String username, String password, String email) {
        super.setUsername(username);
        super.setPassword(password);
        super.setEmail(email);
    }

    // Getters y Setters
    public List<AutoEntity> getAutosVendidos() {
        return autosVendidos;
    }

    public void setAutosVendidos(List<AutoEntity> autosVendidos) {
        this.autosVendidos = autosVendidos;
    }
}

