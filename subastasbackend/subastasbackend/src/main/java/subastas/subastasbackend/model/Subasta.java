package subastas.subastasbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Data
@Entity
@Table(name = "subastas")
public class Subasta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "subasta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Auto> autos = new ArrayList<>();

    @Column(nullable = false)
    private Date fechaInicio;

    @Column(nullable = false)
    private Integer duracion;

    @Column(nullable = false)
    private String estado = "abierta";

    @Column(nullable = false)
    private Date fechaCreacion;

    @Column(nullable = false)
    private Boolean eliminado = false;

    public Subasta() {
    }

    public Subasta(Integer id, List<Auto> autos, Date fechaInicio, Integer duracion, String estado, Date fechaCreacion, Boolean eliminado) {
        this.id = id;
        this.autos = autos;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.eliminado = eliminado;
    }
}