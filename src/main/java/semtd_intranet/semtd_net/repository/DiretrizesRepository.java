package semtd_intranet.semtd_net.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import semtd_intranet.semtd_net.model.Diretrizes;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface DiretrizesRepository extends JpaRepository<Diretrizes, Long> {
    Optional<Diretrizes> findByTitulo(String titulo);

    boolean existsByTitulo(String titulo);

    void deleteByTitulo(String titulo);
}