package semtd_intranet.semtd_net.DTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ComunicadosCadastroDTO {

    @NotBlank
    @Size(min = 3, max = 100)
    private String titulo;

    @NotBlank
    @Size(min = 10)
    private String conteudo;

    @NotNull
    private LocalDateTime expiraEm;
}