package br.com.poc.service;

import java.io.Serializable;

import br.com.poc.dto.ObjectAuxDTO;
import br.com.poc.dto.WinnersDTO;
import br.com.poc.dto.ReturnBaseDTO;
import br.com.poc.dto.ReturnDTO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.poc.dao.MovieDAO;
import br.com.poc.dao.ProducerDAO;
import br.com.poc.entidade.Movie;
import br.com.poc.entidade.Producer;
import br.com.poc.exception.AsqPersistenciaException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


@Service("movieService")
public class MovieService implements Serializable {

    private static final long serialVersionUID = 8774542899624495574L;


    private static final Logger log = Logger.getLogger(MovieService.class);

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private ProducerDAO producerDAO;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Movie movie) throws AsqPersistenciaException {

        try {

            this.movieDAO.save(movie);

        } catch (Exception e) {
            log.error(e.getLocalizedMessage() + this.getClass().getName()+ " save");
            throw new AsqPersistenciaException(e.getMessage());
        }

    }

    /**
     * Método responsável por atualizar os dados
     * da entidade Movie na base de dados
     *
     * @param Movie
     */

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateMovie(Movie movie) throws AsqPersistenciaException{

        try {

            this.movieDAO.updateMovie(movie);

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw new AsqPersistenciaException(e.getMessage());
        }

    }


    /**
     * Método responsável por remover os dados
     * da entidade Movie na base de dados
     *
     * @param Movie
     */

    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(Movie movie) throws AsqPersistenciaException{

        try {
            if(movie == null) {
                throw new AsqPersistenciaException("Entity Movie is required");
            }

            if(movie.getProducers() != null && !movie.getProducers().isEmpty()){
                for(Producer producer : movie.getProducers()){
                    producerDAO.remove(producer.getId());
                }
                movie.setProducers(null);
            }

            this.movieDAO.remove(movie.getId());

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw new AsqPersistenciaException(e.getMessage());
        }

    }


    /**
     * Método responsável por listar os dados
     * da entidade Movie na base de dados
     *
     * @param Movie
     */

    public List<Movie> list() throws AsqPersistenciaException{

        try {
            return this.movieDAO.list();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw new AsqPersistenciaException(e.getMessage());
        }

    }

    /**
     * Método responsável por consultar os dados
     * de uma única entidade Movie na base de dados
     *
     * @param Movie
     */

    public Movie findMovieById(Integer idMovie) throws AsqPersistenciaException{

        try {

            return this.movieDAO.findMovieById(idMovie);

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw new AsqPersistenciaException(e.getMessage());
        }

    }

    public List<ReturnBaseDTO> getWinners() throws AsqPersistenciaException{
        List<ReturnBaseDTO> returnBase = new ArrayList<>();
        try {

            //List<ObjectAuxDTO> objectAuxDTOList = new ArrayList<>();

            List<String> listAux = new ArrayList<>();

            List<WinnersDTO> returnList =  this.movieDAO.findWinners();

            if(returnList != null) {

                for(WinnersDTO winner: returnList){
                    List<WinnersDTO> winnersAux = new ArrayList<>();
                    if(!listAux.contains(winner.getProducer())){
                        for(WinnersDTO winnerIterator: returnList){
                            if(winner.getProducer().equals(winnerIterator.getProducer())){
                                winnersAux.add(winnerIterator);
                            }
                        }
                        listAux.add(winner.getProducer());
                    }
                    Collections.sort(winnersAux);
                    if(winnersAux.size() > 1){
                        int sizePosition = winnersAux.size() - 1;
                        int intervalYears = winnersAux.get(0).getYear() - winnersAux.get(sizePosition).getYear();

                        returnBase.add(new ReturnBaseDTO("", new ReturnDTO(winnersAux.get(sizePosition).getProducer(),
                                        intervalYears,winnersAux.get(sizePosition).getYear(), winnersAux.get(0).getYear() )));
                        System.out.println();
                    }
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new AsqPersistenciaException(e.getMessage());
        }

        if(returnBase != null && returnBase.size() > 1){
            Collections.sort(returnBase);
            int sizeList = returnBase.size() - 1;
            returnBase.get(0).setDesc("max");
            returnBase.get(sizeList).setDesc("min");
        }
        return returnBase;
    }

}
