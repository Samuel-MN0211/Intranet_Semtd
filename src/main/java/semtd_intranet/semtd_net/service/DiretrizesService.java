package semtd_intranet.semtd_net.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import semtd_intranet.semtd_net.model.Diretrizes;
import semtd_intranet.semtd_net.repository.DiretrizesRepository;

@Component
public class DiretrizesService implements Service<Diretrizes, Long> {

    @Autowired
    private DiretrizesRepository diretrizesRepository;

    @Override
    public List<Diretrizes> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Diretrizes findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Diretrizes save(Diretrizes t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

}
