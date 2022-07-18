package br.com.poc.rest;

import br.com.poc.dto.WinnersDTO;
import br.com.poc.entidade.Movie;
import br.com.poc.entidade.Producer;
import br.com.poc.enuns.ColumnEnum;
import br.com.poc.dto.ReturnBaseDTO;
import br.com.poc.service.MovieService;
import br.com.poc.controller.MovieController;
import jxl.read.biff.BiffException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping(value = "/movies")
public class MovieRest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieController movieController;

    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public ResponseEntity<String> loadFilmes() throws IOException, BiffException {

        movieController.loadMoviesCSV();

        return ResponseEntity.status(HttpStatus.OK).body("LOADED!");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<Movie>> getFilmes() throws IOException, BiffException {

        return ResponseEntity.status(HttpStatus.OK).body(movieService.list());
    }

    @RequestMapping(value = "/winner", method = RequestMethod.GET)
    public ResponseEntity<List<ReturnBaseDTO>> getWinners() throws IOException, BiffException {

        return ResponseEntity.status(HttpStatus.OK).body(movieController.getWinners());
    }

}
