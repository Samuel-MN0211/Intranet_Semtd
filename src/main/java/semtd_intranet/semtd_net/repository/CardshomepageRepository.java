package semtd_intranet.semtd_net.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import semtd_intranet.semtd_net.model.Cardshomepage;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CardshomepageRepository extends JpaRepository<Cardshomepage, Long> {

    List<Cardshomepage> findByTituloContainingIgnoreCase(String titulo);

    Optional<Cardshomepage> findByIdAndTituloContainingIgnoreCase(Long id, String titulo);
}
