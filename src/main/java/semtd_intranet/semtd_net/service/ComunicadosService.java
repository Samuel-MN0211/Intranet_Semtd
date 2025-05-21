package semtd_intranet.semtd_net.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import semtd_intranet.semtd_net.model.Comunicados;
import semtd_intranet.semtd_net.repository.ComunicadosRepository;

@Component
public class ComunicadosService implements Service<Comunicados, Long> {

    @Autowired
    private ComunicadosRepository comunicadosRepository;

    @Override
    public List<Comunicados> findAll() {
        return comunicadosRepository.findAll();
    }

    @Override
    public Comunicados findById(Long id) {
        return comunicadosRepository.findById(id).orElse(null);
    }

    @Override
    public Comunicados save(Comunicados t) {
        return comunicadosRepository.save(t);
    }

}
