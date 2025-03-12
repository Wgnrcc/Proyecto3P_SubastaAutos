package subastas.subastasbackend.dto;

import lombok.Data;
import subastas.subastasbackend.model.Usuario;
import java.util.Date;

@Data
public class AutoDTO {
    private Integer id;
    private String marca;
    private String modelo;
    private Integer anio;
    private Double precioBase;
    private Boolean vendido;
    private Integer vendedorId;
    private Date fechaCreacion;
    private Boolean eliminado;

    public AutoDTO(Integer id, String marca, String modelo, Integer anio, Double precioBase, Boolean vendido, Usuario vendedor, Date fechaCreacion) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precioBase = precioBase;
        this.vendido = vendido;
        this.vendedorId = vendedor != null ? vendedor.getId() : null;
        this.fechaCreacion = fechaCreacion;
        this.eliminado = false; // Valor por defecto
    }

    public AutoDTO(Integer id, String marca, String modelo, Integer anio, Double precioBase, Boolean vendido, Integer vendedorId, Date fechaCreacion, Boolean eliminado) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precioBase = precioBase;
        this.vendido = vendido;
        this.vendedorId = vendedorId;
        this.fechaCreacion = fechaCreacion;
        this.eliminado = eliminado;
    }
}