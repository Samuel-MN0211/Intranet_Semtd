package semtd_intranet.semtd_net.security;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import semtd_intranet.semtd_net.enums.Cargo;
import semtd_intranet.semtd_net.enums.Role;
import semtd_intranet.semtd_net.enums.TipoGerencia;
import semtd_intranet.semtd_net.model.Gerencia;
import semtd_intranet.semtd_net.model.Usuarios;
import semtd_intranet.semtd_net.repository.GerenciaRepository;
import semtd_intranet.semtd_net.repository.UsuariosRepository;

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

            // Criação da gerência (obrigatório para associar a qualquer usuário)
            Gerencia gerenciaAdmin = new Gerencia();
            gerenciaAdmin.setNome("Gerência do Admin");
            gerenciaAdmin.setDescricao("Pequeno exemplo para a gerência do admin");
            gerenciaAdmin.setSigla("GEDA");
            gerenciaAdmin.setObjetivos("Gerência criada automaticamente para o usuário administrador.");
            gerenciaAdmin.setTipoGerencia(TipoGerencia.EXECUTIVA);
            gerenciaAdmin = gerenciaRepository.save(gerenciaAdmin);

            // Criação do admin com todos os campos obrigatórios
            Usuarios admin = new Usuarios();
            admin.setNome("Admin");
            admin.setEmail("admin@semtd.com");
            admin.setSenha(encoder.encode("admin123"));
            admin.setRoles(Set.of(Role.ADMIN));
            admin.setGerencia(gerenciaAdmin);
            admin.setCargo(Cargo.DIRETOR); // ou qualquer outro valor do enum
            admin.setFormacao("Administração Pública");

            usuariosRepository.save(admin);
            System.out.println("Admin criado com sucesso. Roles: " + admin.getRoles());

            // Criação do usuário padrão com todos os campos obrigatórios
            Usuarios usuario = new Usuarios();
            usuario.setNome("Usuário Padrão");
            usuario.setEmail("usuario@semtd.com");
            usuario.setSenha(encoder.encode("usuario123"));
            usuario.setRoles(Set.of(Role.USUARIO));
            usuario.setGerencia(gerenciaAdmin);
            usuario.setCargo(Cargo.ANALISTA_ADMINISTRATIVO); // ou outro valor válido do enum
            usuario.setFormacao("Engenharia de Transportes");

            usuariosRepository.save(usuario);
            System.out.println("Usuario Padrão criado com sucesso. Roles: " + usuario.getRoles());
        }
    }
}
