package semtd_intranet.semtd_net.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
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

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/basedigital/cadastraradmin", "/basedigital/cadastraradmin",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .httpBasic();
        return http.build();
    }

    // BEAN PARA INICIALIZAR O AUTENTICADOR
    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            UsuarioDetailsService userDetailsService,
            BCryptPasswordEncoder encoder) throws Exception {
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
