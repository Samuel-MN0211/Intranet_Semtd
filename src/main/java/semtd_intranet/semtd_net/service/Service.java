package semtd_intranet.semtd_net.service;

import semtd_intranet.semtd_net.DTO.GerenciaDTO;
import semtd_intranet.semtd_net.model.Gerencia;

import java.util.List;

public interface Service<T, ID> {

    public List<T> findAll() throws Exception;

    public T findById(ID id);

    public T save(T t);
}