package semtd_intranet.semtd_net.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "arquivos")
@Getter
@Setter
@NoArgsConstructor
public class Arquivos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O nome do arquivo não pode estar vazio")
    @Size(min = 2, max = 255, message = "O nome do arquivo deve ter entre 2 e 255 caracteres")
    private String nomeArquivo;

    @NotEmpty(message = "O caminho do arquivo não pode estar vazio")
    @Size(max = 1000, message = "O caminho do arquivo deve ter no máximo 1000 caracteres")
    private String caminhoArquivo;

    @NotNull(message = "A data de criação é obrigatória")
    private LocalDateTime criadoEm = LocalDateTime.now();

    @OneToOne(mappedBy = "fotoUsuario")
    private Usuarios usuario;

}
