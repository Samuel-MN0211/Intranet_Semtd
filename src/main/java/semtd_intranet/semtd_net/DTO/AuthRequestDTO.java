package semtd_intranet.semtd_net.DTO;

import lombok.Data;

//DADOS PARA REQUEST DE AUTENTICAÇÃO (LOGIN)
@Data
public class AuthRequestDTO {
    private String email;
    private String senha;
}
