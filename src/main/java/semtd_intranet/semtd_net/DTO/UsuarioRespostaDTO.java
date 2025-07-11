package semtd_intranet.semtd_net.DTO;

import java.time.LocalDate;

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
    private LocalDate dataDeNascimento;
    private String gerencia;
}
