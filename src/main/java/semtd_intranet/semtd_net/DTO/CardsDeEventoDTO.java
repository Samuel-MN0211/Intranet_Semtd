package semtd_intranet.semtd_net.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardsDeEventoDTO {

    @NotBlank
    private String titulo;

    @NotNull
    private LocalDate data;

    @NotNull
    private LocalTime horarioInicio;

    @NotNull
    private LocalTime horarioFim;

    @NotBlank
    private String localizacao;

    private String link;
}