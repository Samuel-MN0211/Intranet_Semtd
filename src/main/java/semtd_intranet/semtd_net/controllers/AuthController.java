package semtd_intranet.semtd_net.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import semtd_intranet.semtd_net.model.AuthRequest;
import semtd_intranet.semtd_net.model.AuthResponse;
import semtd_intranet.semtd_net.security.JwtUtil;
import semtd_intranet.semtd_net.service.UsuariosDetailsService;

// UTILIZADO PARA AUTENTICAÇÃO COM JWT
// Rota /auth/login recebe email e senha, autentica e retorna JWT.

// POST /auth/login
// Body:{"email":"admin@email.com","senha":"admin"}

// Todos as requisições que exigem autenticação devem incluir o JWT no cabeçalho Authorization como Bearer <token>

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuariosDetailsService usuariosDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }

        final UserDetails userDetails = usuariosDetailsService.loadUserByUsername(request.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
