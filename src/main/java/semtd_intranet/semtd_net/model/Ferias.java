package semtd_intranet.semtd_net.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "ferias")
@Getter
@Setter
@NoArgsConstructor
public class Ferias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate dataInicio;

    @NotNull
    private LocalDate dataFim;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "supervisor_id", nullable = false)
    private Usuarios supervisor;
}
