package com.exercicio.movie.sessao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.exercicio.movie.response.Response;
import com.exercicio.movie.utils.Validator;

public class Sessao {

	public static Response buscarDadosSessao(HttpServletRequest request, String apikey) throws ResponseStatusException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Necessária a pesquisa de filmes antes de exportar.");
		} 
		return (Response) session.getAttribute(apikey);
	}
	
	public static void gerarDadosSessao(HttpServletRequest request, Response response) {
		if(response != null && !Validator.validaListVazioOrNull(response.getFilmes()) && !Validator.validaStringVazioOrNull(response.getApikey())) {
			HttpSession session = request.getSession();
			session.setAttribute(response.getApikey(), response);
		}
	}
}
