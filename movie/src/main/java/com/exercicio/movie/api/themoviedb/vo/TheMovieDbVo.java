package com.exercicio.movie.api.themoviedb.vo;

import java.util.List;

public class TheMovieDbVo {
	private int page;
	private List<TheMovieDbResultadoVo> results;
	private int total_pages;
	private int total_results;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotal_pages() {
		return total_pages;
	}
	public void setTotal_pages(int total_pages) {
		this.total_pages = total_pages;
	}
	public int getTotal_results() {
		return total_results;
	}
	public void setTotal_results(int total_results) {
		this.total_results = total_results;
	}
	public List<TheMovieDbResultadoVo> getResults() {
		return results;
	}
	public void setResults(List<TheMovieDbResultadoVo> results) {
		this.results = results;
	}
	
	
}
