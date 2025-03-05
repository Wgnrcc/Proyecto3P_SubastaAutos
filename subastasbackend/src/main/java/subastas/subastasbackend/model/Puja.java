package subastas.subastasbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

@Data
@Entity
@Table(name = "pujas")
public class Puja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "subasta_id", nullable = false)
    private Subasta subasta;

    @ManyToOne
    @JoinColumn(name = "comprador_id", nullable = false)
    private Usuario comprador;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private Date fechaPuja;


    public Puja() {
    }

    public Puja(Integer id, Subasta subasta, Usuario comprador, Double monto, Date fechaPuja) {
        this.id = id;
        this.subasta = subasta;
        this.comprador = comprador;
        this.monto = monto;
        this.fechaPuja = fechaPuja;
    }


    public Integer getId() {
        return id;
    }

    public Subasta getSubasta() {
        return subasta;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public Double getMonto() {
        return monto;
    }

    public Date getFechaPuja() {
        return fechaPuja;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSubasta(Subasta subasta) {
        this.subasta = subasta;
    }

    public void setComprador(Usuario comprador) {
        this.comprador = comprador;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public void setFechaPuja(Date fechaPuja) {
        this.fechaPuja = fechaPuja;
    }

}
