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



    public PujaDTO(Integer id, Integer subastaId, Integer compradorId, Double monto, Date fechaPuja) {
        this.id = id;
        this.subastaId = subastaId;
        this.compradorId = compradorId;
        this.monto = monto;
        this.fechaPuja = fechaPuja;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubastaId() {
        return subastaId;
    }

    public void setSubastaId(Integer subastaId) {
        this.subastaId = subastaId;
    }

    public Integer getCompradorId() {
        return compradorId;
    }

    public void setCompradorId(Integer compradorId) {
        this.compradorId = compradorId;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Date getFechaPuja() {
        return fechaPuja;
    }

    public void setFechaPuja(Date fechaPuja) {
        this.fechaPuja = fechaPuja;
    }


}
