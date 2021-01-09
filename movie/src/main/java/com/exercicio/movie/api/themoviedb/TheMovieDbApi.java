package com.exercicio.movie.api.themoviedb;

import java.util.List;

import com.exercicio.movie.exception.IntegrationException;
import com.exercicio.movie.response.Movie;

public interface TheMovieDbApi {

	public List<Movie> getMoviesByActor(String nome) throws IntegrationException ;
	
	public List<Movie> getMoviesByRealizador(String nome) throws IntegrationException ;
	
}
