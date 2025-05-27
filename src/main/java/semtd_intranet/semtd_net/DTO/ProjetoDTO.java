package semtd_intranet.semtd_net.DTO;

import jakarta.validation.constraints.*;
import lombok.*;
import semtd_intranet.semtd_net.enums.StatusProjeto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoDTO {

    private Long id;

    @NotNull(message = "A gerência associada é obrigatória")
    private Long gerenciaId;

    @NotBlank
    @Size(min = 2, max = 100)
    private String nome;

    @NotBlank
    @Size(min = 5, max = 500)
    private String descricao;

    @NotNull
    private StatusProjeto status;

    private String smartTexto;

    private String link;

    @Min(0)
    @Max(100)
    private Integer percentualEntrega;
}
