package semtd_intranet.semtd_net.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semtd_intranet.semtd_net.DTO.FeriasCadastroDTO;
import semtd_intranet.semtd_net.DTO.FeriasDTO;
import semtd_intranet.semtd_net.model.Ferias;
import semtd_intranet.semtd_net.model.Usuarios;
import semtd_intranet.semtd_net.repository.FeriasRepository;
import semtd_intranet.semtd_net.repository.UsuariosRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeriasService {

        @Autowired
        private FeriasRepository feriasRepository;

        @Autowired
        private UsuariosRepository usuariosRepository;

        public FeriasDTO cadastrar(FeriasCadastroDTO dto, String supervisorUsername) {
                Usuarios usuario = usuariosRepository.findById(dto.getUsuarioId())
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                Usuarios supervisor = usuariosRepository.findByRealUsername(supervisorUsername)
                                .orElseThrow(() -> new RuntimeException("Supervisor não encontrado"));

                Ferias ferias = new Ferias();
                ferias.setUsuario(usuario);
                ferias.setSupervisor(supervisor);
                ferias.setDataInicio(dto.getDataInicio());
                ferias.setDataFim(dto.getDataFim());

                Ferias salva = feriasRepository.save(ferias);
                return mapToDTO(salva);
        }

        public List<FeriasDTO> listarTodos() {
                return feriasRepository.findAll()
                                .stream()
                                .map(this::mapToDTO)
                                .collect(Collectors.toList());
        }

        public Optional<FeriasDTO> buscarPorIdDTO(Long id) {
                return feriasRepository.findById(id).map(this::mapToDTO);
        }

        public FeriasDTO atualizar(Long id, FeriasCadastroDTO dto, String supervisorUsername) {
                Ferias ferias = feriasRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Férias não encontrada"));

                Usuarios usuario = usuariosRepository.findById(dto.getUsuarioId())
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                Usuarios supervisor = usuariosRepository.findByRealUsername(supervisorUsername)
                                .orElseThrow(() -> new RuntimeException("Supervisor não encontrado"));

                ferias.setUsuario(usuario);
                ferias.setSupervisor(supervisor);
                ferias.setDataInicio(dto.getDataInicio());
                ferias.setDataFim(dto.getDataFim());

                return mapToDTO(feriasRepository.save(ferias));
        }

        public void deletar(Long id) {
                Ferias ferias = feriasRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Férias não encontrada"));
                feriasRepository.delete(ferias);
        }

        public List<FeriasDTO> buscarPorRealUsername(String usernameFragment) {
                return feriasRepository.findByUsuarioRealUsernameContainingIgnoreCase(usernameFragment)
                                .stream()
                                .map(this::mapToDTO)
                                .collect(Collectors.toList());
        }

        private FeriasDTO mapToDTO(Ferias ferias) {
                return new FeriasDTO(
                                ferias.getId(),
                                ferias.getUsuario().getNome(),
                                ferias.getSupervisor().getRealUsername(),
                                ferias.getDataInicio(),
                                ferias.getDataFim(),
                                ferias.getUsuario().getRealUsername(),
                                ferias.getUsuario().getGerencia().getNome());
        }

        public List<FeriasDTO> listarPorMesEAno(int mes, int ano) {
                List<Ferias> ferias = feriasRepository.findByMesEAno(mes, ano);
                return ferias.stream()
                                .map(this::mapToDTO)
                                .collect(Collectors.toList());
        }

}
