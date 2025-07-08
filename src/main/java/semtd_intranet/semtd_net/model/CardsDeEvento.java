package semtd_intranet.semtd_net.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardsDeEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String titulo;

    @NotNull
    private LocalDate data;

    @NotNull
    private LocalTime horarioInicio;

    @NotNull
    private LocalTime horarioFim;

    @NotBlank
    private String localizacao;

    private String link;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] fotoEvento; // JPEG ou PNG, até 10MB

}
