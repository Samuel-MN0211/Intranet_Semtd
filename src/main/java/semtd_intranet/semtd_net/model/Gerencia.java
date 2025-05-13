package semtd_intranet.semtd_net.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "gerencias")
@Getter
@Setter
@NoArgsConstructor
public class Gerencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O nome da gerência não pode estar vazio")
    @Size(min = 2, max = 100, message = "O nome da gerência deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotEmpty(message = "Os objetivos não podem estar vazios")
    @Size(min = 2, max = 500, message = "Os objetivos devem ter entre 2 e 500 caracteres")
    private String objetivos;

    @OneToMany(mappedBy = "gerencia")
    private List<Usuarios> usuarios;

    @ManyToMany
    @JoinTable(name = "gerencia_diretriz", joinColumns = @JoinColumn(name = "gerencia_id"), inverseJoinColumns = @JoinColumn(name = "diretriz_id"))
    private Set<Diretrizes> diretrizes = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "gerencia_sistema", joinColumns = @JoinColumn(name = "gerencia_id"), inverseJoinColumns = @JoinColumn(name = "sistema_id"))
    private Set<Sistemas> sistemas = new HashSet<>();
}
