package semtd_intranet.semtd_net.DTO;

import lombok.Getter;
import lombok.Setter;
import semtd_intranet.semtd_net.enums.Cargo;

@Getter
@Setter
public class UsuarioCadastroDTO {
    private String nome;
    private String senha;
    private String email;
    private Cargo cargo;
    private String formacao;
    private Long gerenciaId;
}
