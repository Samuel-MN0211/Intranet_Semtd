package semtd_intranet.semtd_net.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import semtd_intranet.semtd_net.service.UsuariosService;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuariosService usuariosService;

    // Ignora a filtragem JWT para endpoints públicos como login e cadastro de
    // usuário
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.equals("/auth/login");
    } // troubleshooting para spring ignorar o filtro jwt para o login e cadastro de
      // usuario (/auth/login) e outros métodos necessários

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException, java.io.IOException {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        String email = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) { // Verifica se o cabeçalho Authorization começa
                                                                      // com "Bearer "
            jwt = authHeader.substring(7); // Quando requisitar pelo front, o token deve ser enviado no
            // cabeçalho como: Authorization (Key) Bearer <token> (Value)
            // O token é o valor do cabeçalho Authorization. Para testar no postman, colocar
            // "bearer" antes do token
            email = jwtUtil.extractUsername(jwt);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) { // Verifica se o usuário
                                                                                               // já está autenticado
            // Se o usuário não estiver autenticado, carrega os detalhes do usuário e valida
            // o token
            UserDetails userDetails = usuariosService.loadUserByUsername(email);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);
    }
}
