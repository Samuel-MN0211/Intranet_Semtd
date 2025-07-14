package semtd_intranet.semtd_net.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import semtd_intranet.semtd_net.model.Gerencia;

import org.springframework.stereotype.Repository;

@Repository
public interface GerenciaRepository extends JpaRepository<Gerencia, Long> {
    List<Gerencia> findByNomeContainsIgnoreCase(String nome);

    List<Gerencia> findByGerenciaVinculadaId(Long id);
}
