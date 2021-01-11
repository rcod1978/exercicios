package com.exercicio.movie.api.nyt;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.exercicio.movie.api.nyt.vo.NewYorkTimesResultVo;
import com.exercicio.movie.api.nyt.vo.NewYorkTimesVo;
import com.exercicio.movie.connection.Connection;
import com.exercicio.movie.exception.IntegrationException;
import com.exercicio.movie.response.Movie;
import com.exercicio.movie.utils.Constants;
import com.exercicio.movie.utils.JsonConverter;
import com.exercicio.movie.utils.Utils;

@Service
public class NewYorkTimesImpl extends Connection implements NewYorkTimesApi{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String API = "https://api.nytimes.com/svc/movies/v2/reviews/search.json";
	private final String API_KEY = "3KhVtvxzyvVLzdSGGwooUzap49749jge";
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
			filmes = getListMovie(inputStream);
		}catch(IntegrationException e) {
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		}finally{
			close();
		}
		return filmes;
	}

	private InputStream getMoveByDirectorName(String name) {
		logger.info("search move by director -> name {}", name);
		String urlString = String.format("%s?query=%s&api-key=%s", API,name.trim().replaceAll(" ", "-"),API_KEY);
		return executeConnection(urlString);
	}
	
	private InputStream getMoveByActorName(String name) {
		logger.info("search move by actor -> name {}", name);
		String urlString = String.format("%s?query=%s&api-key=%s", API,name.trim().replaceAll(" ", "%20"),API_KEY);
		return executeConnection(urlString);
	}
	
	private List<Movie> getListMovie(InputStream inputStream){ 
		List<Movie> filmes = null;		
		NewYorkTimesVo newYorkTimesVo = JsonConverter.convertJsonObjectToNewYorkTimesVo(inputStream);
		if(newYorkTimesVo.getResults() != null && newYorkTimesVo.getResults().size() >= 1) {
			filmes = new ArrayList<Movie>();
			for(NewYorkTimesResultVo newYorkTimesResultVo : newYorkTimesVo.getResults()) {
				Movie movie = new Movie();
				movie.setNomeFilme(newYorkTimesResultVo.getDisplay_title());
				if(newYorkTimesResultVo.getPublication_date() != null) {
					movie.setAnoLancamento(Utils.getYearDate(newYorkTimesResultVo.getPublication_date()));
				}
				movie.setRealizador(newYorkTimesResultVo.getByline());
				filmes.add(movie);
			}
		}
		return filmes;	
	}
}
