package com.exercicio.movie.utils;

import java.util.List;

import org.springframework.util.StringUtils;

import com.exercicio.movie.response.Movie;

public class Validator {
	public static boolean validaStringVazioOrNull(String string){ 
		if (!StringUtils.hasText(string)) {
		    return true;
		}
		return false;
	}
	
	public static boolean validaListVazioOrNull(List<Movie> filmes){ 
		if (filmes == null || filmes.size() < 1) {
		    return true;
		}
		return false;
	}
	
	public static boolean validaTamanhoMinimoString(String string, int minimo){
		if (string.trim().length() < minimo) {
		    return true;
		}
		return false;
	}
}
