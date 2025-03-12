package subastas.subastasbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "autos")
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private Integer anio;

    @Column(nullable = false)
    private Double precioBase;

    @Column(nullable = false)
    private Boolean vendido = false;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Usuario vendedor;

    @Column(nullable = false)
    private Date fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "subasta_id")
    private Subasta subasta;

    @Column(nullable = false)
    private Boolean eliminado = false;

    public Auto() {
    }

    public Auto(Integer id, String marca, String modelo, Integer anio, Double precioBase, Boolean vendido, Usuario vendedor, Date fechaCreacion, Subasta subasta, Boolean eliminado) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precioBase = precioBase;
        this.vendido = vendido;
        this.vendedor = vendedor;
        this.fechaCreacion = fechaCreacion;
        this.subasta = subasta;
        this.eliminado = eliminado;
    }
}