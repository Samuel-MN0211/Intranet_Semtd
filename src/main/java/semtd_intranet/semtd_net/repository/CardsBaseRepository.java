package semtd_intranet.semtd_net.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import semtd_intranet.semtd_net.model.CardsBase;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CardsBaseRepository extends JpaRepository<CardsBase, Long> {
    Optional<CardsBase> findByNome(String nome);

    boolean existsByNome(String nome);

    void deleteByNome(String nome);
}