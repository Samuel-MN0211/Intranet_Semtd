package semtd_intranet.semtd_net.model;

import lombok.Data;

//DADOS PARA REQUEST DE AUTENTICAÇÃO (LOGIN)
@Data
public class AuthRequest {
    private String email;
    private String senha;
}
