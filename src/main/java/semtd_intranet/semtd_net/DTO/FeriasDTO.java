package semtd_intranet.semtd_net.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
}
