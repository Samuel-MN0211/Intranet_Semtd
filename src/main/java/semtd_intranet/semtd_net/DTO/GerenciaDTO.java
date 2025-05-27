package semtd_intranet.semtd_net.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import semtd_intranet.semtd_net.enums.TipoGerencia;

@Getter
@Setter
@NoArgsConstructor

public class GerenciaDTO {

    private Long id;

    @NotEmpty(message = "O nome da gerência não pode estar vazio")
    @Size(min = 2, max = 100)
    private String nome;

    @NotEmpty
    @Size(min = 2, max = 20)
    private String sigla;

    @NotEmpty
    @Size(min = 2, max = 500)
    private String descricao;

    @NotEmpty
    @Size(min = 2, max = 500)
    private String objetivos;

    @NotNull
    private TipoGerencia tipoGerencia;

    // Não possui usuários associados no DTO. Para visualizar usuarios por gerencia,
    // verificar endpoint de listagem em usuariosController

}