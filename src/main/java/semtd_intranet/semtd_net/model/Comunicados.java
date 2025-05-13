package semtd_intranet.semtd_net.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "O título não pode ser nulo")
    @NotEmpty(message = "O título não pode estar vazio")
    @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres")
    @Column(nullable = false)
    private String titulo;

    @NotNull(message = "O conteúdo não pode ser nulo")
    @NotEmpty(message = "O conteúdo não pode estar vazio")
    @Size(min = 10, message = "O conteúdo deve ter no mínimo 10 caracteres")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String conteudo;

    @NotNull(message = "O criador do comunicado é obrigatório")
    @ManyToOne
    @JoinColumn(name = "criado_por_id", nullable = false)
    private Usuarios criadoPor;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
}
