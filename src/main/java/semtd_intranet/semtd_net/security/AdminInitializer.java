package semtd_intranet.semtd_net.security;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import semtd_intranet.semtd_net.enums.Role;
import semtd_intranet.semtd_net.enums.TipoGerencia;
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

        // Verifica se o admin já existe
        if (usuariosRepository.findByEmail("admin@semtd.com").isEmpty()) {

            // Cria a gerência exemplo
            Gerencia gerenciaAdmin = new Gerencia();
            gerenciaAdmin.setNome("Gerência do Admin");
            gerenciaAdmin.setDescricao("Pequeno exemplo para a gerência do admin");
            gerenciaAdmin.setSigla("GEDA");
            gerenciaAdmin.setObjetivos("Gerência criada automaticamente para o usuário administrador.");
            gerenciaAdmin.setTipoGerencia(TipoGerencia.valueOf("EXECUTIVA"));
            gerenciaAdmin = gerenciaRepository.save(gerenciaAdmin);

            // Cria o admin
            Usuarios admin = new Usuarios();
            admin.setNome("Admin");
            admin.setEmail("admin@semtd.com");
            admin.setSenha(encoder.encode("admin123"));
            admin.setRoles(Set.of(Role.ADMIN));
            admin.setGerencia(gerenciaAdmin);

            usuariosRepository.save(admin);
            System.out.println("Roles do admin: " + admin.getRoles());

            // Cria um usuário comum na mesma gerência
            Usuarios usuario = new Usuarios();
            usuario.setNome("Usuário Padrão");
            usuario.setEmail("usuario@semtd.com");
            usuario.setSenha(encoder.encode("usuario123"));
            usuario.setRoles(Set.of(Role.USUARIO));
            usuario.setGerencia(gerenciaAdmin);

            usuariosRepository.save(usuario);
            System.out.println("Usuário padrão criado com sucesso.");
        }
    }
}
