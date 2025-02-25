package ec.edu.espe.Proyecto3P.api.DTO;


import java.time.LocalDateTime;

public class PujaDTO {
    private Long id;
    private double monto;
    private LocalDateTime fecha;
    private Long compradorId;
    private Long subastaId;

    // Constructor vacío
    public PujaDTO() {
    }

    // Constructor con parámetros
    public PujaDTO(Long id, double monto, LocalDateTime fecha, Long compradorId, Long subastaId) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.compradorId = compradorId;
        this.subastaId = subastaId;
    }

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

    public Long getCompradorId() {
        return compradorId;
    }

    public void setCompradorId(Long compradorId) {
        this.compradorId = compradorId;
    }

    public Long getSubastaId() {
        return subastaId;
    }

    public void setSubastaId(Long subastaId) {
        this.subastaId = subastaId;
    }
}
