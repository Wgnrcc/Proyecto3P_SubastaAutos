package ec.edu.espe.Proyecto3P.api.DTO;

public class AutoDTO {
    private Long id;
    private String marca;
    private String modelo;
    private int anio;
    private double precioBase;
    private Long vendedorId; // Referencia al vendedor

    // Constructor vacío
    public AutoDTO() {
    }

    // Constructor con parámetros
    public AutoDTO(Long id, String marca, String modelo, int anio, double precioBase, Long vendedorId) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precioBase = precioBase;
        this.vendedorId = vendedorId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public Long getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(Long vendedorId) {
        this.vendedorId = vendedorId;
    }
}
