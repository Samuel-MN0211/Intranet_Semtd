package semtd_intranet.semtd_net.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import semtd_intranet.semtd_net.model.Usuarios;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {

    Optional<Usuarios> findByEmail(String email);

    boolean existsByEmail(String email);

    public List<Usuarios> findAll();

    List<Usuarios> findByGerenciaId(Long idGerencia);

    List<Usuarios> findByGerenciaNomeIgnoreCaseContaining(String nome);

    Optional<Usuarios> findByRealUsername(String realUsername);

    boolean existsByRealUsername(String realUsername);

}
