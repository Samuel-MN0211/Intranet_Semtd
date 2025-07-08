package semtd_intranet.semtd_net.DTO;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardsDeEventoResponseDTO {

    private Long id;
    private String titulo;
    private LocalDate data;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private String localizacao;
    private String link;
}
