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

    @NotEmpty
    private String nomeArquivo;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] dados;

    private LocalDateTime criadoEm = LocalDateTime.now();

    @OneToOne(mappedBy = "fotoUsuario")
    private Usuarios usuario;
}
