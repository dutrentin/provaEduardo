package br.com.poc.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import java.util.List;
import javax.persistence.Query;
import br.com.poc.entidade.Producer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import br.com.poc.exception.AsqPersistenciaException;
import org.springframework.stereotype.Repository;
import br.com.poc.dto.WinnersDTO;

@Repository
@Qualifier("producerDAO")
public class ProducerDAO extends PersistenciaDao<Producer> {

    private static final long serialVersionUID = 6674632152884036203L;

    private static final Logger log = Logger.getLogger(ProducerDAO.class);


    @Transactional(propagation = Propagation.REQUIRED)
    public void salvar(Producer producer) throws AsqPersistenciaException {

        if(producer == null) {
            throw new AsqPersistenciaException("A entidade Producer deve ser preenchida.");
        }

        try {

            save(producer);
            getEntityManager().flush();

        } catch (Exception e) {
            log.error(e.getLocalizedMessage() + this.getClass().getName());
            throw new AsqPersistenciaException(e.getLocalizedMessage());
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(Integer idProducer) throws AsqPersistenciaException{



        if(idProducer == null) {
            throw new AsqPersistenciaException("The producer id must be informed");
        }

        try {

            delete(idProducer);
            getEntityManager().flush();

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new AsqPersistenciaException(e.getLocalizedMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Producer findByName(String name){
        Query query = getEntityManager().createQuery("SELECT p FROM Producer p WHERE p.producer LIKE '%'||:producer||'%' ");
        query.setParameter("producer", name);
        List<Producer> producers = query.getResultList();

        if(producers != null && producers.size() > 0){
            return producers.get(0);
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<WinnersDTO> findWinners(){
        StringBuilder hql = new StringBuilder().append(" SELECT new br.com.poc.dto.WinnersDTO(p.producer, movieList.year) FROM Producer p ")
                .append(" join Movie movieList ")
                .append(" ")
                .append("")
                .append("")
                .append("");


        Query query = getEntityManager().createQuery(hql.toString());
        //query.setParameter("producer", name);
        List<WinnersDTO> movies = query.getResultList();

        return movies;
    }

}
