package semtd_intranet.semtd_net.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ComunicadoDTO {

    private Long id;
    private String titulo;
    private String conteudo;
    private String criadoPor;
    private LocalDateTime criadoEm;
    private LocalDateTime expiraEm;
}
