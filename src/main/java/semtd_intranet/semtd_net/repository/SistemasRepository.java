package semtd_intranet.semtd_net.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import semtd_intranet.semtd_net.model.Sistemas;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SistemasRepository extends JpaRepository<Sistemas, Long> {
    Optional<Sistemas> findByNome(String nome);

    boolean existsByNome(String nome);

    void deleteByNome(String nome);
}