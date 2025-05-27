package semtd_intranet.semtd_net.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comunicados")
@Getter
@Setter
@NoArgsConstructor
public class Comunicados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    @Column(nullable = false)
    private String titulo;

    @NotNull
    @NotEmpty
    @Size(min = 10)
    @Column(columnDefinition = "TEXT", nullable = false)
    private String conteudo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "criado_por_id", nullable = false)
    private Usuarios criadoPor;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @NotNull
    @Column(name = "expira_em", nullable = false)
    private LocalDateTime expiraEm;
}
