package semtd_intranet.semtd_net.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import semtd_intranet.semtd_net.DTO.UsuarioCadastroDTO;
import semtd_intranet.semtd_net.enums.Role;
import semtd_intranet.semtd_net.model.Gerencia;
import semtd_intranet.semtd_net.model.Usuarios;
import semtd_intranet.semtd_net.repository.GerenciaRepository;
import semtd_intranet.semtd_net.repository.UsuariosRepository;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private GerenciaRepository gerenciaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean emailExiste(String email) {
        return usuariosRepository.existsByEmail(email);
    }

    public Usuarios cadastrarUsuario(UsuarioCadastroDTO dto, Role role) throws IllegalArgumentException {
        Gerencia gerencia = gerenciaRepository.findById(dto.getGerenciaId()).orElse(null);
        if (gerencia == null) {
            throw new IllegalArgumentException("Gerência não encontrada");
        }

        Usuarios usuario = new Usuarios();
        usuario.setNome(dto.getNome());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setEmail(dto.getEmail());
        usuario.setCargo(dto.getCargo());
        usuario.setFormacao(dto.getFormacao());
        usuario.setGerencia(gerencia);
        usuario.setRoles(Set.of(role));
        usuario.setRealUsername(dto.getRealUsername());
        usuario.setDataDeNascimento(dto.getDataDeNascimento());

        return usuariosRepository.save(usuario);
    }

    public List<Usuarios> listarTodos() {
        return usuariosRepository.findAll();
    }

    public Optional<Usuarios> buscarPorEmail(String email) {
        return usuariosRepository.findByEmail(email);
    }

    public void deletarPorEmail(String email) {
        usuariosRepository.findByEmail(email).ifPresent(usuariosRepository::delete);
    }

    public List<Usuarios> listarPorGerencia(Long idGerencia) {
        return usuariosRepository.findByGerenciaId(idGerencia);
    }

    public List<Usuarios> listarPorNomeGerencia(String nome) {
        return usuariosRepository.findByGerenciaNomeIgnoreCaseContaining(nome);
    }

    public void redefinirSenhaPorId(Long id, String novaSenha) {
        Usuarios usuario = usuariosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuariosRepository.save(usuario);
    }

    public Optional<Usuarios> buscarPorId(Long id) {
        return usuariosRepository.findById(id);
    }

}
