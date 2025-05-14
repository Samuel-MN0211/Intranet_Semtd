package semtd_intranet.semtd_net.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import semtd_intranet.semtd_net.model.Sistemas;
import semtd_intranet.semtd_net.repository.SistemasRepository;

@Component
public class SistemasService implements Service<Sistemas, Long> {

    @Autowired
    private SistemasRepository sistemasRepository;

    @Override
    public List<Sistemas> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Sistemas findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Sistemas save(Sistemas t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

}
