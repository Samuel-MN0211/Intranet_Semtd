package semtd_intranet.semtd_net.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import semtd_intranet.semtd_net.model.Comunicados;

import org.springframework.stereotype.Repository;

@Repository
public interface ComunicadosRepository extends JpaRepository<Comunicados, Long> {

}
