package com.exercicio.movie.api.themoviedb;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.exercicio.movie.api.themoviedb.vo.TheMovieDbCastVo;
import com.exercicio.movie.api.themoviedb.vo.TheMovieDbCreditsVo;
import com.exercicio.movie.api.themoviedb.vo.TheMovieDbVo;
import com.exercicio.movie.connection.Connection;
import com.exercicio.movie.exception.IntegrationException;
import com.exercicio.movie.response.Ator;
import com.exercicio.movie.response.Movie;
import com.exercicio.movie.utils.Constants;
import com.exercicio.movie.utils.JsonConverter;
import com.exercicio.movie.utils.Utils;

@Service
public class TheMovieDbApiImpl extends Connection implements TheMovieDbApi{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String API = "https://api.themoviedb.org/3/";
	private final String API_KEY = "b6b1a85a7ba5cde2553684e58a3fecda";
	
	@Override
	public List<Movie> getMoviesByActor(String name) throws IntegrationException {
		List<Movie> filmes = null;
		try{
			InputStream inputStream = getMoveByActorName(name);
			filmes = getListMovie(inputStream);
		}catch(IntegrationException e) {
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		}finally{
			close();
		}
		return filmes;
	}

	@Override
	public List<Movie> getMoviesByRealizador(String name) throws IntegrationException {
		List<Movie> filmes = null;
		try{
			InputStream inputStream = getMoveByDirectorName(name);
			List<Movie> filmesTemp = getListMovie(inputStream);
			filmes = filmesTemp.stream()
					.filter(p -> name.equals(p.getRealizador()))
					.collect(Collectors.toList());
		}catch(IntegrationException e) {
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		}finally{
			close();
		}
		return filmes;
	}

	private List<Movie> getListMovie(InputStream inputStream){ 
		List<Movie> filmes = null;

		TheMovieDbVo theMovieDbVo = JsonConverter.convertJsonObjectToTheMovieDbVo(inputStream);
		if(theMovieDbVo.getResults() != null && theMovieDbVo.getResults().size() >= 1) {
			filmes = new ArrayList<Movie>();
			//buscar todos os filmes pelo id da pessoa
			long id = theMovieDbVo.getResults().get(0).getId();
			inputStream = getActorMovies(id);
			TheMovieDbCreditsVo theMovieDbCreditsVo = JsonConverter.convertJsonObjectToTheMovieDbCreditsVo(inputStream);
			if(theMovieDbCreditsVo != null && theMovieDbCreditsVo.getCast() != null && theMovieDbCreditsVo.getCast().size() >= 1) {
				for(TheMovieDbCastVo theMovieDbCastVo : theMovieDbCreditsVo.getCast()) {
					Movie movie = new Movie();
					movie.setNomeFilme(theMovieDbCastVo.getTitle());
					if(theMovieDbCastVo.getRelease_date() != null) {
						movie.setAnoLancamento(Utils.getYearDate(theMovieDbCastVo.getRelease_date()));
					}
					
					//busca a equipa do filme
					inputStream = getMovieTeam(theMovieDbCastVo.getId());
					TheMovieDbCreditsVo theMovieDbCreditsVoTeam = JsonConverter.convertJsonObjectToTheMovieDbCreditsVo(inputStream);
					if(theMovieDbCreditsVoTeam != null && theMovieDbCreditsVoTeam.getCast() != null && theMovieDbCreditsVoTeam.getCast().size() >= 1) {
						if(movie.getAnoLancamento() == null && theMovieDbCreditsVoTeam.getCast().get(0).getRelease_date() != null) {
							movie.setAnoLancamento(Utils.getYearDate(theMovieDbCreditsVoTeam.getCast().get(0).getRelease_date()));
						}
						List<TheMovieDbCastVo> atores = theMovieDbCreditsVoTeam.getCast()
																			   .stream()
																			   .filter(p -> Constants.ACTING.equals(p.getKnown_for_department())) //somente atores do filme
																			   .sorted(Comparator.comparing(TheMovieDbCastVo::getOrder))
																			   .collect(Collectors.toList());
						
						if(atores != null && atores.size() >= 1) {
							List<Ator> atoresFilme = new ArrayList<Ator>();
							for(TheMovieDbCastVo ator : atores) {
								Ator atorFilme = new Ator(ator.getName());
								atoresFilme.add(atorFilme);
							}
							movie.setProtagonistas(atoresFilme);
						}
						List<TheMovieDbCastVo> realizadores = theMovieDbCreditsVoTeam.getCrew()
																					 .stream()
																					 .filter(p -> Constants.DIRECTTING.equals(p.getKnown_for_department())) //somente diretores do filme
																					 .sorted(Comparator.comparing(TheMovieDbCastVo::getOrder))
																					 .collect(Collectors.toList());
						if(realizadores == null) {
							realizadores = theMovieDbCreditsVoTeam.getCast()
																  .stream()
																  .filter(p -> Constants.DIRECTTING.equals(p.getKnown_for_department())) //somente diretores do filme
																  .sorted(Comparator.comparing(TheMovieDbCastVo::getOrder))
																  .collect(Collectors.toList());
						}
						if(realizadores != null && realizadores.size() >= 1) {
							movie.setRealizador(realizadores.get(0).getName());
						}
					}
					filmes.add(movie);
				}
			}
		}
		return filmes;	
	}

	private InputStream getMovieTeam(long id) {
		logger.info("search team -> move id {}", id);
		String urlString = String.format("%smovie/%s/credits?api_key=%s", API,id,API_KEY);
		return executeConnection(urlString);
	}

	private InputStream getActorMovies(long id) throws IntegrationException{
		logger.info("search movies -> move id {}", id);
		String urlString = String.format("%sperson/%s/credits?api_key=%s&credit_department=%s", API,id,API_KEY,Constants.ACTING);
		return executeConnection(urlString);
	}

	private InputStream getMoveByActorName(String name) {
		logger.info("search move by actor -> name {}", name);
		String urlString = String.format("%ssearch/person?api_key=%s&query=%s", API,API_KEY,name.trim().replaceAll(" ", "-"));
		return executeConnection(urlString);
	}

	private InputStream getMoveByDirectorName(String name) {
		logger.info("search move by director -> name {}", name);
		String urlString = String.format("%ssearch/person?api_key=%s&query=%s", API,API_KEY,name.trim().replaceAll(" ", "-"));
		return executeConnection(urlString);
	}

}
