package semtd_intranet.semtd_net.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import semtd_intranet.semtd_net.model.Gerencia;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class FeriasDTO {
    private Long id;
    private String nomeUsuario;
    private String supervisor;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String realUsername;
    private String gerencia;
}
