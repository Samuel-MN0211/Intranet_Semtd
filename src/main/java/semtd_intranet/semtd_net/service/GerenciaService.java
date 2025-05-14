package semtd_intranet.semtd_net.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import semtd_intranet.semtd_net.model.Gerencia;
import semtd_intranet.semtd_net.repository.GerenciaRepository;

@Component
public class GerenciaService implements Service<Gerencia, Long> {

    @Autowired
    private GerenciaRepository gerenciaRepository;

    @Override
    public List<Gerencia> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Gerencia findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Gerencia save(Gerencia t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

}
