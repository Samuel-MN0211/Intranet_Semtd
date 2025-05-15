package semtd_intranet.semtd_net.DTO;

import semtd_intranet.semtd_net.model.Arquivos;

//DTO NECESSÁRIO PARA RETORNAR METADADOS E LINKS DOS ARQUIVOS NO CONTROLADOR DE ARQUIVOS (GET listarLinksDosArquivos)
public class ArquivosDTO {
    private Long id;
    private String nomeArquivo;
    private String linkDownload;

    public ArquivosDTO(Long id, String nomeArquivo, String linkDownload) {
        this.id = id;
        this.nomeArquivo = nomeArquivo;
        this.linkDownload = linkDownload;
    }

    // Construtor que aceita um objeto Arquivos e inicializa os campos do DTO
    // necessário para o GET todos do controller
    public ArquivosDTO(Arquivos arquivo) {
        this.id = arquivo.getId();
        this.nomeArquivo = arquivo.getNomeArquivo();
        this.linkDownload = "/semtd/arquivos/" + arquivo.getId();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public String getLinkDownload() {
        return linkDownload;
    }
}
