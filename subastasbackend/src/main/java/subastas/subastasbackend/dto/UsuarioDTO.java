package subastas.subastasbackend.dto;

import lombok.Data;
import java.util.Date;

@Data
public class UsuarioDTO {

    private Integer id;
    private String nombre;
    private String email;
    private String rolNombre;  // Incluimos el nombre del rol
    private Date fechaCreacion;
    private Boolean activo;

    // Constructor
    public UsuarioDTO(Integer id, String nombre, String email, String rolNombre, Date fechaCreacion, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rolNombre = rolNombre;
        this.fechaCreacion = fechaCreacion;
        this.activo = activo;
    }

    // Getters y setters automáticamente generados por Lombok (@Data)

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

}
