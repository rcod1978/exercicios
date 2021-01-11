package com.exercicio.movie.controllers;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.exercicio.movie.response.Movie;
import com.exercicio.movie.response.Response;
import com.exercicio.movie.services.MovieService;
import com.exercicio.movie.sessao.Sessao;
import com.exercicio.movie.utils.Constants;
import com.exercicio.movie.utils.Validator;

@RestController
@RequestMapping(Constants.API)
public class MovieController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public MovieService movieService;
	
	@GetMapping("search/realizador/{nome}")
	public Response searchToRealizador(HttpServletRequest request, @PathVariable("nome") String nome,  @RequestParam String apikey) {
		if (Validator.validaStringVazioOrNull(nome)) {
		    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nome do realizador não pode ser null/branco.");
		}
		if (Validator.validaTamanhoMinimoString(nome, Constants.TAMANHO_MINIMO_NOME)) {
		    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nome do realizador precisa ter no mínimo 3 caracteres.");
		}
		logger.info("search by realizador name {}", nome);
		try {
			return movieService.searchByRealizador(nome, request, apikey);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INTEGRATION_EXCEPTION);
		}
	}
	
	@GetMapping("search/ator/{nome}")
	public Response searchByActor(HttpServletRequest request, @PathVariable("nome") String nome,  @RequestParam String apikey) {
		if (Validator.validaStringVazioOrNull(nome)) {
		    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nome ator não pode ser null/branco.");
		}
		if (Validator.validaTamanhoMinimoString(nome, Constants.TAMANHO_MINIMO_NOME)) {
		    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nome do ator precisa ter no mínimo 3 caracteres.");
		}
		logger.info("search by actor name {}", nome);
		try {
			return movieService.searchByActor(nome, request, apikey);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INTEGRATION_EXCEPTION);
		}
	}
	
	@GetMapping(path="export/filmes", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void export(HttpServletRequest request, HttpServletResponse response, @RequestParam String apikey) throws Exception {
		Response responseSessao = Sessao.buscarDadosSessao(request, apikey);
		if (responseSessao != null && Validator.validaListVazioOrNull(responseSessao.getFilmes())) {
		    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lista de filmes em branco não pode ser exportada.");
		}
		
		OutputStream output = null;
		try {
			output = movieService.export(responseSessao.getFilmes());
		}catch(Exception e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao converter a lista de filmes para Power Point.");
		}
		response.setContentType("application/vnd.ms-powerpoint");
		response.setHeader("access-control-expose-headers","Content-Disposition");
		response.setHeader("Content-Disposition","attachment; filename=\"" + Constants.NOME_ARQUIVO);

		InputStream is = new FileInputStream("Filmes.pptx");
		IOUtils.copy(is, response.getOutputStream());

		response.flushBuffer();
	}	    
}
