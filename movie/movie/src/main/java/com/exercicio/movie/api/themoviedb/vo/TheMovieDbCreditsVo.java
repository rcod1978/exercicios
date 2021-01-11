package com.exercicio.movie.api.themoviedb.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TheMovieDbCreditsVo {
	private long id;
	private List<TheMovieDbCastVo> cast;
	private List<TheMovieDbCastVo> crew;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<TheMovieDbCastVo> getCast() {
		return cast;
	}
	public void setCast(List<TheMovieDbCastVo> cast) {
		this.cast = cast;
	}
	public List<TheMovieDbCastVo> getCrew() {
		return crew;
	}
	public void setCrew(List<TheMovieDbCastVo> crew) {
		this.crew = crew;
	}
	
	
}
