package semtd_intranet.semtd_net.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import semtd_intranet.semtd_net.model.Ferias;

import java.util.List;

public interface FeriasRepository extends JpaRepository<Ferias, Long> {
    List<Ferias> findByUsuarioRealUsernameContainingIgnoreCase(String username);
}
