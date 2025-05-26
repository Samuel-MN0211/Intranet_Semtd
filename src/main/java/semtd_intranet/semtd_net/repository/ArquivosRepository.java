package semtd_intranet.semtd_net.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import semtd_intranet.semtd_net.model.Arquivos;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArquivosRepository extends JpaRepository<Arquivos, Long> {
    List<Arquivos> findAllByNomeArquivo(String nomeArquivo);

    Arquivos findByNomeArquivo(String nomeArquivo);

    void deleteByNomeArquivo(String nomeArquivo);

    void deleteById(Long id);
}
