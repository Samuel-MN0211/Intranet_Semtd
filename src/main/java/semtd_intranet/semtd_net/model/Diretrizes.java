package semtd_intranet.semtd_net.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import semtd_intranet.semtd_net.enums.IconeDiretriz;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "diretrizes")
@Getter
@Setter
@NoArgsConstructor
public class Diretrizes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O título é obrigatório")
    @NotEmpty(message = "O título não pode estar vazio")
    @Size(min = 2, max = 100, message = "O título deve ter entre 2 e 100 caracteres")
    @Column(nullable = false)
    private String titulo;

    @NotNull(message = "A descrição é obrigatória")
    @NotEmpty(message = "A descrição não pode estar vazia")
    @Size(min = 5, max = 255, message = "A descrição deve ter entre 5 e 255 caracteres")
    @Column(nullable = false)
    private String descricao;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IconeDiretriz icone;

}
