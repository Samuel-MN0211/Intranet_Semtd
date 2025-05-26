package semtd_intranet.semtd_net.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import semtd_intranet.semtd_net.enums.TipoGerencia;
import semtd_intranet.semtd_net.model.Gerencia;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GerenciaDTO {

    @NotEmpty(message = "O nome da gerência não pode estar vazio")
    @Size(min = 2, max = 100)
    private String nome;

    @NotEmpty
    @Size(min = 2, max = 20)
    private String sigla;

    @NotEmpty
    @Size(min = 2, max = 500)
    private String descricao;

    @NotEmpty
    @Size(min = 2, max = 500)
    private String objetivos;

    @NotNull
    private TipoGerencia tipoGerencia;

    private List<UsuarioDTO> usuarios;

    // Construtor para criar DTO a partir da entidade
    public GerenciaDTO(Gerencia entity) {
        this.nome = entity.getNome();
        this.sigla = entity.getSigla();
        this.descricao = entity.getDescricao();
        this.objetivos = entity.getObjetivos();
        this.tipoGerencia = entity.getTipoGerencia();

        // Conversão segura: evita NullPointerException
        if (entity.getUsuarios() != null) {
            this.usuarios = entity.getUsuarios()
                    .stream()
                    .map(UsuarioDTO::new)
                    .toList();
        }
    }

    // Conversão para entidade (não inclui usuários, pois normalmente só se cria/atualiza a gerência em separado)
    public Gerencia toEntity() {
        Gerencia gerencia = new Gerencia();
        gerencia.setNome(this.nome);
        gerencia.setSigla(this.sigla);
        gerencia.setDescricao(this.descricao);
        gerencia.setObjetivos(this.objetivos);
        gerencia.setTipoGerencia(this.tipoGerencia);
        // Não setamos usuários aqui para evitar confusão no ciclo.
        return gerencia;
    }
}