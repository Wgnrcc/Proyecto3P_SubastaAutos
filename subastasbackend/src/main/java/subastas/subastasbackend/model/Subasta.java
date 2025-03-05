package subastas.subastasbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

@Data
@Entity
@Table(name = "subastas")

public class Subasta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "auto_id", nullable = false)
    private Auto auto;

    @Column(nullable = false)
    private Date fechaInicio;

    @Column(nullable = false)
    private Integer duracion;

    @Column(nullable = false)
    private String estado = "activa";

    @Column(nullable = false)
    private Date fechaCreacion;

    public Subasta() {
    }

    public Subasta(Integer id, Auto auto, Date fechaInicio, Integer duracion, String estado, Date fechaCreacion) {
        this.id = id;
        this.auto = auto;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }


    public Integer getId() {
        return id;
    }

    public Auto getAuto() {
        return auto;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public String getEstado() {
        return estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}
