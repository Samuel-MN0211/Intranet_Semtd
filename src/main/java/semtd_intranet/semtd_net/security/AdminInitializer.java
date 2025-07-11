package semtd_intranet.semtd_net.security;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import semtd_intranet.semtd_net.enums.Cargo;
import semtd_intranet.semtd_net.enums.Role;
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
            Gerencia gerenciaPrincipal = gerenciaRepository.findById(1L)
                    .orElseThrow(
                            () -> new RuntimeException("Erro de inicialização: Gerência com ID 1 não encontrada! " +
                                    "Certifique-se de que o seu arquivo data.sql está criando a gerência principal."));

            Usuarios admin = new Usuarios();
            admin.setNome("Admin");
            admin.setEmail("admin@semtd.com");
            admin.setSenha(encoder.encode("admin123"));
            admin.setRoles(Set.of(Role.ADMIN));
            admin.setGerencia(gerenciaPrincipal);
            admin.setCargo(Cargo.DIRETOR);
            admin.setFormacao("Administração Pública");
            admin.setRealUsername("Samuel M");
            admin.setDataDeNascimento(LocalDate.of(1990, 5, 20));

            usuariosRepository.save(admin);
            System.out.println("Usuário 'admin' criado e associado à gerência '" + gerenciaPrincipal.getNome() + "'.");

            // --- Criação do usuário Padrão ---
            Usuarios usuario = new Usuarios();
            usuario.setNome("Usuário Padrão");
            usuario.setEmail("usuario@semtd.com");
            usuario.setSenha(encoder.encode("usuario123"));
            usuario.setRoles(Set.of(Role.USUARIO));
            usuario.setGerencia(gerenciaPrincipal);
            usuario.setCargo(Cargo.ANALISTA_ADMINISTRATIVO);
            usuario.setFormacao("Engenharia de Transportes");
            usuario.setRealUsername("Ramon C.");
            usuario.setDataDeNascimento(LocalDate.of(1995, 8, 15));

            usuariosRepository.save(usuario);
            System.out.println(
                    "Usuário 'Usuário Padrão' criado e associado à gerência '" + gerenciaPrincipal.getNome() + "'.");
        }
    }
}