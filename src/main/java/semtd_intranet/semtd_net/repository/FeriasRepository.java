package semtd_intranet.semtd_net.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import semtd_intranet.semtd_net.model.Ferias;

import java.util.List;

public interface FeriasRepository extends JpaRepository<Ferias, Long> {
    List<Ferias> findByUsuarioRealUsernameContainingIgnoreCase(String username);

    @Query("SELECT f FROM Ferias f WHERE " +
            "MONTH(f.dataInicio) = :mes AND YEAR(f.dataInicio) = :ano OR " +
            "MONTH(f.dataFim) = :mes AND YEAR(f.dataFim) = :ano")
    List<Ferias> findByMesEAno(@Param("mes") int mes, @Param("ano") int ano);
}
