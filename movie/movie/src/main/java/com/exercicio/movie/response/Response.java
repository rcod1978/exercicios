package com.exercicio.movie.response;

import java.util.List;

public class Response {
	private List<Movie> filmes;
	private String apikey;
	private String tipoPesquisa; 
	private String nomePesquisado;
	
	public void setFilmes(List<Movie> filmes) {
		this.filmes = filmes;
	}
	public List<Movie> getFilmes() {
		return filmes;
	}
	public String getTipoPesquisa() {
		return tipoPesquisa;
	}
	public void setTipoPesquisa(String tipoPesquisa) {
		this.tipoPesquisa = tipoPesquisa;
	}
	public String getNomePesquisado() {
		return nomePesquisado;
	}
	public void setNomePesquisado(String nomePesquisado) {
		this.nomePesquisado = nomePesquisado;
	}
	public String getApikey() {
		return apikey;
	}
	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
	
	
}
