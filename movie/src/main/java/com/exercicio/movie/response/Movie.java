package com.exercicio.movie.response;

import java.util.List;

import com.exercicio.movie.utils.Validator;

public class Movie {
	private String nomeFilme;
	private String realizador;
	private List<Ator> protagonistas;
	private Integer anoLancamento;
	
	public String getNomeFilme() {
		return nomeFilme;
	}
	public void setNomeFilme(String nomeFilme) {
		this.nomeFilme = nomeFilme;
	}
	public Integer getAnoLancamento() {
		return anoLancamento;
	}
	public void setAnoLancamento(Integer anoLancamento) {
		this.anoLancamento = anoLancamento;
	}
	public List<Ator> getProtagonistas() {
		return protagonistas;
	}
	public void setProtagonistas(List<Ator> protagonistas) {
		this.protagonistas = protagonistas;
	}
	public String getRealizador() {
		return realizador;
	}
	public void setRealizador(String realizador) {
		this.realizador = realizador;
	}
	@Override
	public String toString() {
		return "Movie [nomeFilme=" + nomeFilme + ", realizador=" + realizador + ", protagonistas=" + imprimeProtagonistas()
				+ ", anoLancamento=" + anoLancamento + "]";
	}
	public String imprimeProtagonistas() {
		StringBuffer atores = new StringBuffer();
		if(protagonistas != null && protagonistas.size() > 0) {
			protagonistas.forEach(p -> atores.append(p.getNome() + ", "));
		}
		if(!Validator.validaStringVazioOrNull(atores.toString()) && atores.toString().length() > 2) {
			return atores.toString().substring(0,(atores.toString().length()-2));	
		}else {
			return "";
		}
		
	}
	
	
}
