package semtd_intranet.semtd_net.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import semtd_intranet.semtd_net.model.Projeto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    Optional<Projeto> findByNome(String nome);

    boolean existsByNome(String nome);

    void deleteByNome(String nome);

    List<Projeto> findAllByGerenciaId(Long gerenciaId);
}
