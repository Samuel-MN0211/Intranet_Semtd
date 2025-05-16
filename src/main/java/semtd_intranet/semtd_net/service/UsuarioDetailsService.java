package semtd_intranet.semtd_net.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import semtd_intranet.semtd_net.model.Usuarios;
import semtd_intranet.semtd_net.repository.UsuariosRepository;
import semtd_intranet.semtd_net.security.UsuarioDetailsImpl;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuarios usuario = usuariosRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return new UsuarioDetailsImpl(usuario);
    }
}
