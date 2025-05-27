package semtd_intranet.semtd_net.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import semtd_intranet.semtd_net.enums.TipoGerencia;

import java.util.List;

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
    @Column(unique = true)
    private String nome;

    @NotEmpty(message = "A sigla da gerência não pode estar vazia")
    @Size(min = 2, max = 20, message = "A sigla deve ter entre 2 e 20 caracteres")
    private String sigla;

    @NotEmpty(message = "A descrição não pode estar vazia")
    @Size(min = 2, max = 500, message = "A descrição deve ter entre 2 e 500 caracteres")
    private String descricao;

    @NotEmpty(message = "Os objetivos não podem estar vazios")
    @Size(min = 2, max = 500, message = "Os objetivos devem ter entre 2 e 500 caracteres")
    private String objetivos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoGerencia tipoGerencia;

    @OneToMany(mappedBy = "gerencia")
    private List<Usuarios> usuarios;

    @OneToMany(mappedBy = "gerencia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Projeto> projetos;

}
