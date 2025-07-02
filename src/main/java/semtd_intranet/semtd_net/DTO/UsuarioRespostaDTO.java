package semtd_intranet.semtd_net.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioRespostaDTO {
    private Long id;
    private String nome;
    private String email;
    private String tipo;
    private String realUsername;
}
