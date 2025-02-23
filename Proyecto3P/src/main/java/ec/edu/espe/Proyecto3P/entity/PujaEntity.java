package ec.edu.espe.Proyecto3P.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pujas")
public class PujaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double monto;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "comprador_id", nullable = false)
    private Comprador comprador;

    @ManyToOne
    @JoinColumn(name = "subasta_id", nullable = false)
    private Subasta subasta;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Comprador getComprador() {
        return comprador;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public Subasta getSubasta() {
        return subasta;
    }

    public void setSubasta(Subasta subasta) {
        this.subasta = subasta;
    }
}
