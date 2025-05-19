package semtd_intranet.semtd_net.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sistemas")
@Getter
@Setter
@NoArgsConstructor
public class Sistemas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome é obrigatório")
    @NotEmpty(message = "O nome não pode estar vazio")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "A descrição é obrigatória")
    @NotEmpty(message = "A descrição não pode estar vazia")
    @Size(min = 5, max = 255, message = "A descrição deve ter entre 5 e 255 caracteres")
    @Column(nullable = false)
    private String descricao;

    @NotNull(message = "O link é obrigatório")
    @NotEmpty(message = "O link não pode estar vazio")
    @Size(max = 255, message = "O link deve ter no máximo 255 caracteres")
    @Column(nullable = false)
    private String link;

}
