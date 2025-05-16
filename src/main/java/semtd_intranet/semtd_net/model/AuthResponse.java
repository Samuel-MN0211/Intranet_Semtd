package semtd_intranet.semtd_net.model;

import lombok.AllArgsConstructor;
import lombok.Data;

//DADOS PARA RESPONSE DE AUTENTICAÇÃO (LOGIN)
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
}
