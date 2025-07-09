package semtd_intranet.semtd_net.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedefinirSenhaAdminDTO {

    @NotNull
    private Long usuarioId;

    @NotBlank
    private String novaSenha;
}
