package semtd_intranet.semtd_net.service;

import java.util.List;

public interface Service<T, ID> {

    public List<T> findAll() throws Exception;

    public T findById(ID id);

    public T save(T t);
}