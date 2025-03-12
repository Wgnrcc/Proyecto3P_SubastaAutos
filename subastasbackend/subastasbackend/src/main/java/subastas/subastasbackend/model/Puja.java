package subastas.subastasbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;

@Data
@Entity
@Table(name = "pujas")
public class Puja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "subasta_id", nullable = false)
    @JsonIgnore  // La propiedad subasta no se serializará en JSON
    private Subasta subasta;

    @ManyToOne
    @JoinColumn(name = "comprador_id", nullable = false)
    private Usuario comprador;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private Date fechaPuja;

    @Column(nullable = false)
    private Boolean eliminado = false;

    public Puja() {
    }

    public Puja(Integer id, Subasta subasta, Usuario comprador, Double monto, Date fechaPuja, Boolean eliminado) {
        this.id = id;
        this.subasta = subasta;
        this.comprador = comprador;
        this.monto = monto;
        this.fechaPuja = fechaPuja;
        this.eliminado = eliminado;
    }
}