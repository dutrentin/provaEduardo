package br.com.poc.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.poc.dao.ProducerDAO;
import br.com.poc.entidade.Producer;
import br.com.poc.exception.AsqPersistenciaException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.io.Serializable;

@Service("producerService")
public class ProducerService implements Serializable {

    private static final long serialVersionUID = 8779242899624495574L;


    private static final Logger log = Logger.getLogger(ProducerService.class);

    @Autowired
    private ProducerDAO producerDAO;

    public Producer findByName(String name) throws AsqPersistenciaException{
        try {
            return this.producerDAO.findByName(name);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new AsqPersistenciaException(e.getMessage());
        }
    }

}
