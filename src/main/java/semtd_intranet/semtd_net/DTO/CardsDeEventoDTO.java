package semtd_intranet.semtd_net.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class CardsDeEventoDTO {

    @NotBlank(message = "O título é obrigatório e não pode estar em branco.")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
    private String titulo;

    @NotNull(message = "A data do evento é obrigatória.")
    private LocalDate data;

    @NotNull(message = "O horário de início do evento é obrigatório.")
    private LocalTime horarioInicio;

    @NotNull(message = "O horário de fim do evento é obrigatório.")
    private LocalTime horarioFim;

    @NotBlank(message = "A localização do evento é obrigatória e não pode estar em branco.")
    @Size(max = 150, message = "A localização deve ter no máximo 150 caracteres.")
    private String localizacao;

    @Pattern(regexp = "^(https?://)?([\\w.-]+)\\.([a-z]{2,6})([/\\w .-]*)*/?$", message = "O link deve ser uma URL válida começando com http:// ou https://")
    private String link;

}
