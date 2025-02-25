package ec.edu.espe.Proyecto3P.api.DTO;



public class AdministradorDTO extends UsuarioDTO {

    // Constructor vacío
    public AdministradorDTO() {
    }

    // Constructor con parámetros
    public AdministradorDTO(Long id, String username, String email, boolean activo) {
        super(id, username, email, activo);
    }
}
