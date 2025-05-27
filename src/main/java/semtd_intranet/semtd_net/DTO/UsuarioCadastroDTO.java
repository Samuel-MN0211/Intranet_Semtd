package semtd_intranet.semtd_net.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import semtd_intranet.semtd_net.enums.Cargo;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioCadastroDTO {

    @NotBlank
    @Size(min = 2, max = 100)
    private String nome;

    @NotBlank
    @Size(min = 6)
    private String senha;

    @NotBlank
    @Email
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    @NotNull
    private Cargo cargo;

    @NotBlank
    private String formacao;

    @NotNull
    private Long gerenciaId;

    @NotBlank
    @Size(min = 3, max = 50)
    private String realUsername;

}
