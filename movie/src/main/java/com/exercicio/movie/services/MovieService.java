package com.exercicio.movie.services;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercicio.movie.api.nyt.NewYorkTimesApi;
import com.exercicio.movie.api.themoviedb.TheMovieDbApi;
import com.exercicio.movie.enums.TipoPesquisa;
import com.exercicio.movie.exception.BussinessException;
import com.exercicio.movie.exception.IntegrationException;
import com.exercicio.movie.response.Movie;
import com.exercicio.movie.response.Response;
import com.exercicio.movie.sessao.Sessao;
import com.exercicio.movie.utils.Constants;
import com.exercicio.movie.utils.PowerPointConverter;
import com.exercicio.movie.utils.Validator;

@Service
public class MovieService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TheMovieDbApi theMovieDbApi;
	
	@Autowired
	private NewYorkTimesApi newYorkTimesApi;
	
	public OutputStream export(List<Movie> filmes) throws IOException {
		return PowerPointConverter.convertMovieToFileOutputStream(filmes);
	}
	
	public Response searchByRealizador(String nome, HttpServletRequest request, String apikey) throws IntegrationException {
		Response response = new Response();
		response.setTipoPesquisa(TipoPesquisa.REALIZADOR.toString());
		response.setNomePesquisado(nome);
		try{
			Response responseSessao = verificarDadosSessao(nome, request, apikey, response);
			if(responseSessao != null && TipoPesquisa.ATOR.toString().equals(responseSessao.getTipoPesquisa()) && !Validator.validaListVazioOrNull(responseSessao.getFilmes()) && nome.trim().equals(responseSessao.getNomePesquisado())) {
				response.setFilmes(responseSessao.getFilmes());
				response.setToken(responseSessao.getToken());
				return response;
			}else {
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
				List<Movie> filmes = null;
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
					if(responseSessao != null && !Validator.validaStringVazioOrNull(responseSessao.getToken())) {
						response.setToken(responseSessao.getToken());
					}else {
						response.setToken(getToken());	
					}
					response.setFilmes(filmes);
					Sessao.gerarDadosSessao(request, response);
				}
			}
		}catch(BussinessException e) {
			throw new BussinessException(Constants.BUSSINESS_EXCEPTION);
		}catch(IntegrationException e) {
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		}
		return response;
	}
	
	public Response searchByActor(String nome, HttpServletRequest request, String apikey) throws IntegrationException {
		Response response = new Response();
		response.setTipoPesquisa(TipoPesquisa.ATOR.toString());
		response.setNomePesquisado(nome);
		try{
			Response responseSessao = verificarDadosSessao(nome, request, apikey, response);
			if(responseSessao != null && TipoPesquisa.ATOR.toString().equals(responseSessao.getTipoPesquisa()) && !Validator.validaListVazioOrNull(responseSessao.getFilmes()) && nome.trim().equals(responseSessao.getNomePesquisado())) {
				response.setFilmes(responseSessao.getFilmes());
				response.setToken(responseSessao.getToken());
				return response;
			}else {
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
				
				List<Movie> filmes = new ArrayList<>(Stream.of(filmesTheMovieDb, filmesNewYorkTimes)
														   .flatMap(List::stream)
														   	.collect(Collectors.toMap(Movie::getNomeFilme, d -> d, (Movie x, Movie y) -> x == null ? y : x)).values());
				if(filmes != null) {
					logger.info("############# FILMES ####################");
					filmes.forEach(System.out::println);
					logger.info("############# FILMES ####################");
					if(responseSessao != null && !Validator.validaStringVazioOrNull(responseSessao.getToken())) {
						response.setToken(responseSessao.getToken());
					}else {
						response.setToken(getToken());	
					}
					response.setFilmes(filmes);
					Sessao.gerarDadosSessao(request, response);
				}
			}
		}catch(BussinessException e) {
			throw new BussinessException(Constants.BUSSINESS_EXCEPTION);
		}catch(IntegrationException e) {
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		}
		return response;
	}

	private Response verificarDadosSessao(String nome, HttpServletRequest request, String apikey, Response response) {
		Response responseSessao = null;
		try {
			if(!Validator.validaStringVazioOrNull(apikey)) {
				responseSessao = Sessao.buscarDadosSessao(request, apikey);
			}
		}catch(Exception e) {
			logger.info("Dados sessao inexistentes");
		}
		return responseSessao;
	}

	private String getToken() {
		return UUID.randomUUID().toString();
	}
}
