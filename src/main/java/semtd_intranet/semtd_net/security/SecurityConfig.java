
//UTILIZADO PARA AUTENTICAÇÃO JWT COM FILTERCHAIN

package semtd_intranet.semtd_net.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import semtd_intranet.semtd_net.model.Gerencia;
import semtd_intranet.semtd_net.model.Usuarios;
import semtd_intranet.semtd_net.service.UsuarioDetailsService;
import semtd_intranet.semtd_net.repository.GerenciaRepository;
import semtd_intranet.semtd_net.repository.UsuariosRepository;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private UsuarioDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { // Criptografa senhas dos usuários.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable()) // Desativa csrf expondo o endpoint de criação de admin para que possa
                                                 // ser acessado sem estar logado
                // TODO: REVER ROTA DE ADMIN EXPOSTA APÓS TESTE E VALIDAÇÃO
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/usuarios/cadastraradmin").permitAll() // Permite /auth/login e
                                                                                                // /usuarios/cadastraradmin
                                                                                                // sem autenticação

                        .anyRequest().authenticated()) // Torna todas as outras rotas protegidas
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Usa JWT
                                                                                                              // no
                                                                                                              // lugar
                                                                                                              // de
                                                                                                              // sessões.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // BEAN PARA INICIALIZAR O AUTENTICADOR
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder encoder)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }

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
                admin.setFuncao("ADMIN");
                admin.setGerencia(gerenciaAdmin); // Associa a gerência ao admin

                usuariosRepository.save(admin);
            }
        }
    }

}
