package com.exercicio.movie;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exercicio.movie.api.nyt.NewYorkTimesApi;
import com.exercicio.movie.api.themoviedb.TheMovieDbApi;

@SpringBootTest
class MovieApplicationTests {

	@InjectMocks
	public TheMovieDbApi theMovieDbApi;
	
	@Autowired
	public NewYorkTimesApi newYorkTimesApi;
	
	
	@Test
	void contextLoads() {
	}

	@Test
	void getMoviesByActor() {
		theMovieDbApi.getMoviesByActor("Jeff Goldblum");
	}
}
