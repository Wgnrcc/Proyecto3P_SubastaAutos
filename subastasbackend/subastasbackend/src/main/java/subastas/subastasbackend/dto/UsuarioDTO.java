package subastas.subastasbackend.dto;

import lombok.Data;
import java.util.Date;

@Data
public class UsuarioDTO {

    private Integer id;
    private String nombre;
    private String email;
    private String rolNombre;
    private Date fechaCreacion;
    private Boolean activo;
    private Boolean eliminado;

    public UsuarioDTO(Integer id, String nombre, String email, String rolNombre, Date fechaCreacion, Boolean activo, Boolean eliminado) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rolNombre = rolNombre;
        this.fechaCreacion = fechaCreacion;
        this.activo = activo;
        this.eliminado = eliminado;
    }

    public UsuarioDTO(Integer id, String nombre, String email, String rolNombre, Date fechaCreacion, Boolean activo) {
        this(id, nombre, email, rolNombre, fechaCreacion, activo, false);
    }
}