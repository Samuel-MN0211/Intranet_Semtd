package semtd_intranet.semtd_net.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import semtd_intranet.semtd_net.model.Comunicados;

import org.springframework.stereotype.Repository;

@Repository
public interface ComunicadosRepository extends JpaRepository<Comunicados, Long> {

    Optional<Comunicados> findByTituloIgnoreCase(String titulo);

    List<Comunicados> findAllByTituloContainingIgnoreCase(String titulo);

    List<Comunicados> findByCriadoEmAfter(LocalDateTime data);
}
