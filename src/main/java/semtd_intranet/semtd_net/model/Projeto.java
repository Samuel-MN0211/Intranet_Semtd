package semtd_intranet.semtd_net.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import semtd_intranet.semtd_net.enums.StatusProjeto;

@Entity
@Table(name = "projetos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "gerencia_id")
    private Gerencia gerencia;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(unique = true)
    private String nome;

    @Column(name = "o_que_e")
    @NotBlank
    @Size(min = 5, max = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @NotNull
    private StatusProjeto status;

    @Column(name = "smart_texto", length = 1000)
    private String smartTexto;

    private String link;

    @Min(0)
    @Max(100)
    private Integer percentualEntrega;
}
