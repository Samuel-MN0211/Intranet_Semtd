
//UTILIZADO PARA AUTENTICAÇÃO HTTP SIMPLES. DESCARTAR AO IMPLEMENTAR JWT 

package semtd_intranet.semtd_net.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import semtd_intranet.semtd_net.model.Usuarios;
import semtd_intranet.semtd_net.repository.UsuariosRepository;
import semtd_intranet.semtd_net.security.SecurityConfig;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/cadastraradmin")
    public ResponseEntity<?> cadastrarAdmin(@RequestBody Usuarios admin) {
        if (usuariosRepository.existsByEmail(admin.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail já cadastrado");
        }

        admin.setSenha(passwordEncoder.encode(admin.getSenha()));
        admin.setFuncao("ADMIN");
        usuariosRepository.save(admin);

        return ResponseEntity.status(HttpStatus.CREATED).body("Admin cadastrado com sucesso");
    }

}
