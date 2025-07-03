package semtd_intranet.semtd_net.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import semtd_intranet.semtd_net.enums.Role;

import java.util.Set;

// DADOS PARA RESPONSE DE AUTENTICAÇÃO (LOGIN)
@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String realUsername;
    private String email;
    private Set<Role> roles;
}
