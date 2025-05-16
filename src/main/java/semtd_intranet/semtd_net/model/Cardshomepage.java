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
@Table(name = "cards_homepage")
@Getter
@Setter
@NoArgsConstructor
public class Cardshomepage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O título não pode ser nulo")
    @NotEmpty(message = "O título não pode estar vazio")
    @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres")
    @Column(name = "titulo", nullable = false, unique = true)
    private String titulo;

    @NotNull(message = "A descrição não pode ser nula")
    @NotEmpty(message = "A descrição não pode estar vazia")
    @Size(min = 10, max = 500, message = "A descrição deve ter entre 10 e 500 caracteres")
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull(message = "O link não pode ser nulo")
    @NotEmpty(message = "O link não pode estar vazio")
    @Size(max = 255, message = "O link deve ter no máximo 255 caracteres")
    @Column(name = "link", nullable = false)
    private String link;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;
}
