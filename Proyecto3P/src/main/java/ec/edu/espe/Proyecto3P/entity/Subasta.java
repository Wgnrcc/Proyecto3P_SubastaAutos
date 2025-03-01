package ec.edu.espe.Proyecto3P.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "subastas")
public class Subasta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    @Column(nullable = false)
    private LocalDateTime fechaFin;

    @ManyToOne
    @JoinColumn(name = "auto_id", nullable = false)
    private AutoEntity auto;

    // Getters y Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public AutoEntity getAuto() {
        return auto;
    }

    public void setAuto(AutoEntity auto) {
        this.auto = auto;
    }
}
