package semtd_intranet.semtd_net.DTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import semtd_intranet.semtd_net.enums.Categoria;
import semtd_intranet.semtd_net.enums.Icones;

@Getter
@Setter
@NoArgsConstructor
public class CardsBaseDTO {

    private long id;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String nome;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 255)
    private String descricao;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    @Pattern(regexp = "^(https?://)?([\\w.-]+)\\.([a-z]{2,6})([/\\w .-]*)*/?$", message = "O link deve ser uma URL válida")
    private String link;

    @NotNull
    private Icones icone;

    @NotNull
    private Categoria categoria;
}
