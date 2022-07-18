package br.com.poc.service;

import br.com.poc.entidade.Movie;
import br.com.poc.entidade.Producer;
import br.com.poc.enuns.ColumnEnum;
import jxl.read.biff.BiffException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("loadCSVService")
public class LoadCSVService {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ProducerService producerService;

    public void loadMoviesCSV() throws IOException, BiffException{
        String arquivoCSV = "C://movielist.csv";
        BufferedReader br = null;
        String line = "";
        String csvDivisor = ";";

        try {

            br = new BufferedReader(new FileReader(arquivoCSV));
            while ((line = br.readLine()) != null) {
                if(!line.contains("year;")){
                    String[] columns = line.split(csvDivisor);

                    // Verifica integridade da quantidade de colunas
                    if(columns != null && columns.length > 3){
                        Movie newMovie = new Movie();
                        newMovie.setYear(Integer.parseInt(columns[ColumnEnum.YEAR.getColumn()]));
                        newMovie.setTitle(columns[ColumnEnum.TITLE.getColumn()]);
                        newMovie.setStudios(columns[ColumnEnum.STUDIOS.getColumn()]);

                        List<Producer> producers = new ArrayList<>();

                        String producersAll = columns[ColumnEnum.PRODUCERS.getColumn()];

                        String[] producersSplit = producersAll.split(",|and ");
                        if(producersSplit != null && producersSplit.length > 0){
                            for (String producer : producersSplit) {
                                Producer producerFinder = producerService.findByName(producer);
                                if(producerFinder != null){
                                    producers.add(producerFinder);
                                }else{
                                    producers.add(new Producer(producer));
                                }
                            }
                        }


                        newMovie.setProducers(producers);

                        if(columns != null && columns.length > 4){
                            String winner = columns[ColumnEnum.WINNER.getColumn()];
                            if(winner.contains("yes")){
                                newMovie.setWinner(Boolean.TRUE);
                            }else{
                                newMovie.setWinner(Boolean.FALSE);
                            }
                        }else{
                            newMovie.setWinner(Boolean.FALSE);
                        }

                        movieService.save(newMovie);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
