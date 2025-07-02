package semtd_intranet.semtd_net.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FeriasCadastroDTO {
    @NotNull
    private LocalDate dataInicio;

    @NotNull
    private LocalDate dataFim;

    @NotNull
    private Long usuarioId; // ID do usuário que estará de férias
}
