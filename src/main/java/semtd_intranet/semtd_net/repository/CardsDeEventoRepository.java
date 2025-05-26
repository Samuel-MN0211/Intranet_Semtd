package semtd_intranet.semtd_net.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import semtd_intranet.semtd_net.model.CardsDeEvento;

import java.util.Optional;

public interface CardsDeEventoRepository extends JpaRepository<CardsDeEvento, Long> {
    Optional<CardsDeEvento> findByTitulo(String titulo);

    boolean existsByTitulo(String titulo);

    void deleteByTitulo(String titulo);
}
