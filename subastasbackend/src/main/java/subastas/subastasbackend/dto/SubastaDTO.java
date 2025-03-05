package subastas.subastasbackend.dto;
import lombok.Data;
import java.util.Date;

@Data
public class SubastaDTO {
    private Integer id;
    private Integer autoId;
    private Date fechaInicio;
    private Integer duracion;
    private String estado;
    private Date fechaCreacion;



    public SubastaDTO(Integer id, Integer autoId, Date fechaInicio, Integer duracion, String estado, Date fechaCreacion) {
        this.id = id;
        this.autoId = autoId;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAutoId() {
        return autoId;
    }

    public void setAutoId(Integer autoId) {
        this.autoId = autoId;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


}
