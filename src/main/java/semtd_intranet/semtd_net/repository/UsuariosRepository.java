package semtd_intranet.semtd_net.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import semtd_intranet.semtd_net.model.Usuarios;
import java.util.Optional;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {

}
