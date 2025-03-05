package subastas.subastasbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

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
    private Boolean vendido=false;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Usuario vendedor;

    @Column(nullable = false)
    private Date fechaCreacion;

    public Auto() {
    }

    public Auto(Integer id, String marca, String modelo, Integer anio, Double precioBase, Boolean vendido, Usuario vendedor, Date fechaCreacion) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precioBase = precioBase;
        this.vendido = vendido;
        this.vendedor = vendedor;
        this.fechaCreacion = fechaCreacion;
    }

    
    public Integer getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public Integer getAnio() {
        return anio;
    }

    public Double getPrecioBase() {
        return precioBase;
    }

    public Boolean getVendido() {
        return vendido;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public void setPrecioBase(Double precioBase) {
        this.precioBase = precioBase;
    }

    public void setVendido(Boolean vendido) {
        this.vendido = vendido;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


}
