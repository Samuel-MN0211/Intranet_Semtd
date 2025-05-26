package semtd_intranet.semtd_net.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SistemasDTO {

    private long id;

    @NotNull(message = "O nome é obrigatório")
    @NotEmpty(message = "O nome não pode estar vazio")
    @Size(min = 2, max = 100)
    private String nome;

    @NotNull(message = "A descrição é obrigatória")
    @NotEmpty(message = "A descrição não pode estar vazia")
    @Size(min = 5, max = 255)
    private String descricao;

    @NotNull(message = "O link é obrigatório")
    @NotEmpty(message = "O link não pode estar vazio")
    @Size(max = 255)
    @Pattern(regexp = "^(https?://)?([\\w.-]+)\\.([a-z]{2,6})([/\\w .-]*)*/?$", message = "O link deve ser uma URL válida começando com http:// ou https://")
    private String link;
}