package semtd_intranet.semtd_net.security;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import semtd_intranet.semtd_net.enums.Role;
import semtd_intranet.semtd_net.model.Gerencia;
import semtd_intranet.semtd_net.model.Usuarios;
import semtd_intranet.semtd_net.repository.GerenciaRepository;
import semtd_intranet.semtd_net.repository.UsuariosRepository;

// BEAN PARA CRIAÇÃO DE ADMIN PADRAO E GERÊNCIA PADRÃO AO INICIAR A APLICAÇÃO
// (ADMIN PRECISA NECESSARIAMENTE DE UMA GERÊNCIA PARA SER CRIADO) -
// TROUBLESHOOTING

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private GerenciaRepository gerenciaRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public void run(String... args) {
        if (usuariosRepository.findByEmail("admin@semtd.com").isEmpty()) {

            // Cria a gerência do admin
            Gerencia gerenciaAdmin = new Gerencia();
            gerenciaAdmin.setNome("Gerência do Admin");
            gerenciaAdmin.setObjetivos("Gerência criada automaticamente para o usuário administrador.");
            gerenciaAdmin = gerenciaRepository.save(gerenciaAdmin);

            // Cria o admin
            Usuarios admin = new Usuarios();
            admin.setNome("Admin");
            admin.setEmail("admin@semtd.com");
            admin.setSenha(encoder.encode("admin123"));
            admin.setRoles(Set.of(Role.ADMIN));
            admin.setGerencia(gerenciaAdmin); // Associa a gerência ao admin

            usuariosRepository.save(admin);
        }
    }
}
