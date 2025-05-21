package semtd_intranet.semtd_net.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import semtd_intranet.semtd_net.model.Usuarios;
import semtd_intranet.semtd_net.repository.UsuariosRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;

@Component
public class UsuariosService implements Service<Usuarios, Long>, UserDetailsService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public List<Usuarios> findAll() {
        return usuariosRepository.findAll();
    }

    @Override
    public Usuarios findById(Long id) {
        return usuariosRepository.findById(id).orElse(null);
    }

    @Override
    public Usuarios save(Usuarios t) {
        return usuariosRepository.save(t);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuariosRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

}
