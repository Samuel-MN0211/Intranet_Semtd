package semtd_intranet.semtd_net.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import semtd_intranet.semtd_net.enums.Icones;

@Getter
@Setter
@NoArgsConstructor
public class DiretrizesDTO {

    private long id;

    @NotNull(message = "O título é obrigatório")
    @NotEmpty(message = "O título não pode estar vazio")
    @Size(min = 2, max = 100, message = "O título deve ter entre 2 e 100 caracteres")
    private String titulo;

    @NotNull(message = "A descrição é obrigatória")
    @NotEmpty(message = "A descrição não pode estar vazia")
    @Size(min = 5, max = 255, message = "A descrição deve ter entre 5 e 255 caracteres")
    private String descricao;

    @NotNull(message = "O ícone é obrigatório")
    private Icones icone;

}