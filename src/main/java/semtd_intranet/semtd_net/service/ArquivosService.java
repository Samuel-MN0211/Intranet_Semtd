package semtd_intranet.semtd_net.service;

import semtd_intranet.semtd_net.model.Arquivos;
import semtd_intranet.semtd_net.repository.ArquivosRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class ArquivosService implements Service<Arquivos, Long> {

    @Autowired
    private ArquivosRepository arquivosRepository;

    @Override
    public List<Arquivos> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Arquivos findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Arquivos save(Arquivos t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

}
