package subastas.subastasbackend.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class SubastaDTO {
    private Integer id;
    private List<Integer> autoIds;  // Lista de IDs de autos asociados
    private Date fechaInicio;
    private Integer duracion;
    private String estado;
    private Date fechaCreacion;
    // Tiempo restante en minutos
    private Integer tiempoRestante;
    private Boolean eliminado;

    public SubastaDTO(Integer id, List<Integer> autoIds, Date fechaInicio, Integer duracion, String estado, Date fechaCreacion, Integer tiempoRestante, Boolean eliminado) {
        this.id = id;
        this.autoIds = autoIds;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.tiempoRestante = tiempoRestante;
        this.eliminado = eliminado;
    }

    public SubastaDTO(Integer id, List<Integer> autoIds, Date fechaInicio, Integer duracion, String estado, Date fechaCreacion, int tiempoRestante) {
    }

    public SubastaDTO() {
    }

// Getters y setters...

    public Integer getId() {
        return id;
    }

    public List<Integer> getAutoIds() {
        return autoIds;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public String getEstado() {
        return estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Integer getTiempoRestante() {
        return tiempoRestante;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAutoIds(List<Integer> autoIds) {
        this.autoIds = autoIds;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setTiempoRestante(Integer tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }
}
