package ec.edu.espe.Proyecto3P.api.DTO;


import java.util.List;

public class CompradorDTO extends UsuarioDTO {
    private List<PujaDTO> pujasRealizadas;

    // Constructor vacío
    public CompradorDTO() {
    }

    // Constructor con parámetros
    public CompradorDTO(Long id, String username, String email, boolean activo, List<PujaDTO> pujasRealizadas) {
        super(id, username, email, activo);
        this.pujasRealizadas = pujasRealizadas;
    }

    // Getters y Setters
    public List<PujaDTO> getPujasRealizadas() {
        return pujasRealizadas;
    }

    public void setPujasRealizadas(List<PujaDTO> pujasRealizadas) {
        this.pujasRealizadas = pujasRealizadas;
    }
}
