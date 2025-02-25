package ec.edu.espe.Proyecto3P.api.DTO;
import java.util.List;
import ec.edu.espe.Proyecto3P.api.DTO.AutoDTO;


public class VendedorDTO extends UsuarioDTO {
    private List<AutoDTO> autosVendidos;

    // Constructor vacío
    public VendedorDTO() {
    }

    // Constructor con parámetros
    public VendedorDTO(Long id, String username, String email, boolean activo, List<AutoDTO> autosVendidos) {
        super(id, username, email, activo);
        this.autosVendidos = autosVendidos;
    }

    // Getters y Setters
    public List<AutoDTO> getAutosVendidos() {
        return autosVendidos;
    }

    public void setAutosVendidos(List<AutoDTO> autosVendidos) {
        this.autosVendidos = autosVendidos;
    }
}
