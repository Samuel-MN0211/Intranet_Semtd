
//UTILIZADO PARA AUTENTICAÇÃO JWT COM FILTERCHAIN

package semtd_intranet.semtd_net.security;

import java.util.Set;

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

import semtd_intranet.semtd_net.repository.GerenciaRepository;
import semtd_intranet.semtd_net.repository.UsuariosRepository;
import semtd_intranet.semtd_net.service.UsuariosService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private UsuariosService usuariosService;

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
                        .requestMatchers("/auth/login", "/gerencia/**").permitAll()
                        .requestMatchers("/usuarios/criar", "/usuarios/cadastraradmin").hasRole("ADMIN") // Apenas ADMIN
                        .anyRequest()
                        .authenticated()) // Torna todas as outras rotas protegidas
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
                .userDetailsService(usuariosService) // <--- atualize aqui
                .passwordEncoder(encoder)
                .and()
                .build();
    }
}