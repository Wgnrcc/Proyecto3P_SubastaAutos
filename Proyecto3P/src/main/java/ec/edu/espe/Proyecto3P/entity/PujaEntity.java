package ec.edu.espe.Proyecto3P.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pujas")
public class PujaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_puja")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_subasta", nullable = false)
    private SubastaEntity subasta;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private VehiculoEntity vehiculo;

    @ManyToOne
    @JoinColumn(name = "id_comprador", nullable = false)
    private UsuarioEntity comprador;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "fecha_puja", nullable = false)
    private LocalDateTime fechaPuja;

    public PujaEntity() {}

    // Getters y Setters...

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SubastaEntity getSubasta() {
        return subasta;
    }

    public void setSubasta(SubastaEntity subasta) {
        this.subasta = subasta;
    }

    public VehiculoEntity getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(VehiculoEntity vehiculo) {
        this.vehiculo = vehiculo;
    }

    public UsuarioEntity getComprador() {
        return comprador;
    }

    public void setComprador(UsuarioEntity comprador) {
        this.comprador = comprador;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaPuja() {
        return fechaPuja;
    }

    public void setFechaPuja(LocalDateTime fechaPuja) {
        this.fechaPuja = fechaPuja;
    }
}
