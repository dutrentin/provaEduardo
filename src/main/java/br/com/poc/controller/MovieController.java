package br.com.poc.controller;

import br.com.poc.entidade.Producer;
import br.com.poc.dto.WinnersDTO;
import br.com.poc.entidade.Movie;
import br.com.poc.entidade.Producer;
import br.com.poc.enuns.ColumnEnum;
import br.com.poc.dto.ReturnBaseDTO;
import br.com.poc.service.LoadCSVService;
import br.com.poc.service.MovieService;
import jxl.read.biff.BiffException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import br.com.poc.exception.AsqPersistenciaException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;
import java.util.Arrays;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import br.com.poc.enuns.ColumnEnum;
@RestController
@RequestMapping("/movie")
@CrossOrigin(origins = "*") // definição de quais domínios poderam acessar esta API
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private LoadCSVService loadCSVService;

    @GetMapping("/loadCSV")
    @ApiOperation(value = "Load and Save movies from CSV")
    public void loadMoviesCSV() throws IOException, BiffException{
        loadCSVService.loadMoviesCSV();
    }

    @GetMapping("/getWinners")
    @ApiOperation(value = "Catch winning producers ")
    public List<ReturnBaseDTO> getWinners() throws AsqPersistenciaException{
            return this.movieService.getWinners();
    }


}
