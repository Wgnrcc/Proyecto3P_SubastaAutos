package subastas.subastasbackend.dto;
import lombok.Data;
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


    public AutoDTO(Integer id, String marca, String modelo, Integer anio, Double precioBase, Boolean vendido, Integer vendedorId, Date fechaCreacion) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precioBase = precioBase;
        this.vendido = vendido;
        this.vendedorId = vendedorId;
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

    public Integer getVendedorId() {
        return vendedorId;
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

    public void setVendedorId(Integer vendedorId) {
        this.vendedorId = vendedorId;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


}
