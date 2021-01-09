package com.exercicio.movie.services;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercicio.movie.api.nyt.NewYorkTimesApi;
import com.exercicio.movie.api.themoviedb.TheMovieDbApi;
import com.exercicio.movie.exception.BussinessException;
import com.exercicio.movie.exception.IntegrationException;
import com.exercicio.movie.response.Movie;
import com.exercicio.movie.utils.Constants;
import com.exercicio.movie.utils.PowerPointConverter;

@Service
public class MovieService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public TheMovieDbApi theMovieDbApi;
	
	@Autowired
	public NewYorkTimesApi newYorkTimesApi;
	
	public OutputStream export(List<Movie> filmes) throws IOException {
		return PowerPointConverter.convertMovieToFileOutputStream(filmes);
	}
	
	public List<Movie> searchByRealizador(String nome) throws IntegrationException {
		List<Movie> filmes = null;
		try{
			List<Movie> filmesTheMovieDb = theMovieDbApi.getMoviesByRealizador(nome);
			
			if(filmesTheMovieDb != null && filmesTheMovieDb.size() > 0) {
				logger.info("############# FILMES TheMovieDb ####################");
				filmesTheMovieDb.forEach(System.out::println);
				logger.info("############# FILMES TheMovieDb ####################");
			}
			
			List<Movie> filmesNewYorkTimes = newYorkTimesApi.getMoviesByRealizador(nome);
			if(filmesNewYorkTimes != null && filmesNewYorkTimes.size() > 0) {
				logger.info("############# FILMES NEW YORK TIMES ####################");
				filmesNewYorkTimes.forEach(System.out::println);
				logger.info("############# FILMES NEW YORK TIMES ####################");				
			}
			
			if(filmesTheMovieDb != null && filmesTheMovieDb.size() > 0 && filmesNewYorkTimes != null && filmesNewYorkTimes.size() > 0) {
				filmes = new ArrayList<>(Stream.of(filmesTheMovieDb, filmesNewYorkTimes)
													   .flatMap(List::stream)
													   	.collect(Collectors.toMap(Movie::getNomeFilme, d -> d, (Movie x, Movie y) -> x == null ? y : x)).values());
			}else if(filmesTheMovieDb != null && filmesTheMovieDb.size() > 0) {
				filmes = filmesTheMovieDb;
			}else {
				filmes = filmesNewYorkTimes;
			}
			
			if(filmes != null && filmes.size() > 0) {
				logger.info("############# FILMES ####################");
				filmes.forEach(System.out::println);
				logger.info("############# FILMES ####################");
			}	
		}catch(BussinessException e) {
			throw new BussinessException(Constants.BUSSINESS_EXCEPTION);
		}catch(IntegrationException e) {
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		}
		return filmes;
	}
	
	public List<Movie> searchByActor(String nome) throws IntegrationException {
		List<Movie> filmes = null;
		try{
			List<Movie> filmesTheMovieDb = theMovieDbApi.getMoviesByActor(nome);
			
			if(filmesTheMovieDb != null) {
				logger.info("############# FILMES TheMovieDb ####################");
				filmesTheMovieDb.forEach(System.out::println);
				logger.info("############# FILMES TheMovieDb ####################");
			}
			
			List<Movie> filmesNewYorkTimes = newYorkTimesApi.getMoviesByActor(nome);
			if(filmesNewYorkTimes != null) {
				logger.info("############# FILMES NEW YORK TIMES ####################");
				filmesNewYorkTimes.forEach(System.out::println);
				logger.info("############# FILMES NEW YORK TIMES ####################");				
			}
			
			filmes = new ArrayList<>(Stream.of(filmesTheMovieDb, filmesNewYorkTimes)
													   .flatMap(List::stream)
													   	.collect(Collectors.toMap(Movie::getNomeFilme, d -> d, (Movie x, Movie y) -> x == null ? y : x)).values());
			if(filmes != null) {
				logger.info("############# FILMES ####################");
				filmes.forEach(System.out::println);
				logger.info("############# FILMES ####################");
			}	
		}catch(BussinessException e) {
			throw new BussinessException(Constants.BUSSINESS_EXCEPTION);
		}catch(IntegrationException e) {
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		}
		return filmes;
	}
}
