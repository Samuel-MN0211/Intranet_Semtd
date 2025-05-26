package semtd_intranet.semtd_net.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import semtd_intranet.semtd_net.enums.Cargo;
import semtd_intranet.semtd_net.model.Usuarios;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCadastroDTO {

        private Long id;
        private String nome;
        private String email;
        private Cargo cargo;

        public UsuarioCadastroDTO(Usuarios usuario) {
            this.id = usuario.getId();
            this.nome = usuario.getNome();
            this.email = usuario.getEmail();
            this.cargo = usuario.getCargo();
        }
}
