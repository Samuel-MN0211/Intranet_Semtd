package semtd_intranet.semtd_net.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import semtd_intranet.semtd_net.model.Cardshomepage;
import semtd_intranet.semtd_net.repository.CardshomepageRepository;

@Component
public class CardshomepageService implements Service<Cardshomepage, Long> {

    @Autowired
    private CardshomepageRepository cardshomepageRepository;

    @Override
    public List<Cardshomepage> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Cardshomepage findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Cardshomepage save(Cardshomepage t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

}
