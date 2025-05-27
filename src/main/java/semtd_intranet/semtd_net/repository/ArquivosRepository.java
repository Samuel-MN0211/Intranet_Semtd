package semtd_intranet.semtd_net.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import semtd_intranet.semtd_net.model.Arquivos;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArquivosRepository extends JpaRepository<Arquivos, Long> {
    List<Arquivos> findAllByNomeArquivo(String nomeArquivo);

    Arquivos findByNomeArquivo(String nomeArquivo);

    void deleteByNomeArquivo(String nomeArquivo);

    void deleteById(Long id);

    Optional<Arquivos> findByUsuario_Email(String email);

    List<Arquivos> findByUsuarioIsNull();

    @Query("SELECT a FROM Arquivos a WHERE a.usuario.gerencia.id = :gerenciaId AND (a.usuario.cargo = 'GERENTE_EXECUTIVO' OR a.usuario.cargo = 'GERENTE_OPERACIONAL')")
    List<Arquivos> findByGerenciaAndCargo(Long gerenciaId);
}
