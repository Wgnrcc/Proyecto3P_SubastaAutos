package subastas.subastasbackend.dto;

import lombok.Data;
import java.util.Date;

@Data
public class PujaDTO {
    private Integer id;
    private Integer subastaId;
    private Integer compradorId;
    private Double monto;
    private Date fechaPuja;
    private Boolean eliminado;

    public PujaDTO() {
    }

    public PujaDTO(Integer id, Integer subastaId, Integer compradorId, Double monto, Date fechaPuja, Boolean eliminado) {
        this.id = id;
        this.subastaId = subastaId;
        this.compradorId = compradorId;
        this.monto = monto;
        this.fechaPuja = fechaPuja;
        this.eliminado = eliminado;
    }

    public PujaDTO(Integer id, Integer subastaId, Integer compradorId, Double monto, Date fechaPuja) {
        this(id, subastaId, compradorId, monto, fechaPuja, false);
    }
}