package br.com.poc.dao;

import br.com.poc.entidade.Movie;
import br.com.poc.entidade.Producer;
import br.com.poc.dto.WinnersDTO;
import br.com.poc.exception.AsqPersistenciaException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
@Qualifier("producerDAO")
public class MovieDAO extends PersistenciaDao<Movie> {

    private static final long serialVersionUID = 6644637152884036203L;

    private static final Logger log = Logger.getLogger(MovieDAO.class);

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveMovie(Movie movie) throws AsqPersistenciaException {

        if(movie == null) {
            throw new AsqPersistenciaException("Entity Movie is required");
        }

        try {

            save(movie);
            getEntityManager().flush();

        } catch (Exception e) {
            log.error(e.getLocalizedMessage() + this.getClass().getName());
            throw new AsqPersistenciaException(e.getLocalizedMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateMovie(Movie movie) throws AsqPersistenciaException{

        if(movie == null) {
            throw new AsqPersistenciaException("Entity Movie is required ");
        }

        try {

            update(movie);
            getEntityManager().flush();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new AsqPersistenciaException(e.getLocalizedMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(Integer idMovie) throws AsqPersistenciaException{



        if(idMovie == null) {
            throw new AsqPersistenciaException("The movie id must be informed");
        }

        try {

            delete(idMovie);
            getEntityManager().flush();

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new AsqPersistenciaException(e.getLocalizedMessage());
        }
    }

    public List<Movie> list() throws AsqPersistenciaException{

        try {

            return findAll();

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new AsqPersistenciaException(e.getLocalizedMessage());
        }
    }

    public Movie findMovieById(Integer idMovie) throws AsqPersistenciaException{

        try {

            return findById(idMovie);

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new AsqPersistenciaException(e.getLocalizedMessage());
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<WinnersDTO> findWinners(){
        StringBuilder hql = new StringBuilder().append(" SELECT new  br.com.poc.dto.WinnersDTO(p.producer, m.year) FROM Movie m  ")
                .append(" JOIN m.producers p ")
                .append(" WHERE 1 = 1 ")
                .append(" AND m.winner = 1 ")
                .append(" AND (select count(m2.id) from Movie m2 JOIN m2.producers p2 WHERE p2.id = p.id AND m2.winner = 1) > 1   ");


        Query query = getEntityManager().createQuery(hql.toString());
        //query.setParameter("producer", name);
        List<WinnersDTO> movies = query.getResultList();

        return movies;
    }

}
