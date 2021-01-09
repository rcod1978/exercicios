package com.exercicio.movie.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Response;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.exercicio.movie.response.Movie;
import com.exercicio.movie.services.MovieService;
import com.exercicio.movie.utils.Constants;
import com.exercicio.movie.utils.Validator;

@RestController
@RequestMapping(Constants.API)
public class MovieController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public MovieService movieService;
	
	@GetMapping("search/realizador/{nome}")
	public List<Movie> searchToRealizador(HttpServletRequest request, @PathVariable("nome") String nome) {
		if (Validator.validaStringVazioOrNull(nome)) {
		    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nome do realizador não pode ser null/branco.");
		}
		if (Validator.validaTamanhoMinimoString(nome, Constants.TAMANHO_MINIMO_NOME)) {
		    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nome do realizador precisa ter no mínimo 3 caracteres.");
		}
		logger.info("search by realizador name {}", nome);
		try {
			List<Movie> filmes = movieService.searchByRealizador(nome);
			if(filmes != null && filmes.size() > 0) {
				HttpSession session = request.getSession();
				session.setAttribute("filmes", filmes);
			}
			return filmes;
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INTEGRATION_EXCEPTION);
		}
	}
	
	@GetMapping("search/ator/{nome}")
	public List<Movie> searchByActor(HttpServletRequest request, @PathVariable("nome") String nome) {
		if (Validator.validaStringVazioOrNull(nome)) {
		    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nome ator não pode ser null/branco.");
		}
		if (Validator.validaTamanhoMinimoString(nome, Constants.TAMANHO_MINIMO_NOME)) {
		    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nome do ator precisa ter no mínimo 3 caracteres.");
		}
		logger.info("search by actor name {}", nome);
		try {
			List<Movie> filmes = movieService.searchByActor(nome);
			if(filmes != null && filmes.size() > 0) {
				HttpSession session = request.getSession();
				session.setAttribute("filmes", filmes);
			}
			return filmes;
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INTEGRATION_EXCEPTION);
		}
	}
	
	@GetMapping(path="export/filmes", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		if (session == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Necessária a pesquisa de filmes antes de exportar.");
		} 
		List<Movie> movies = (List<Movie>) session.getAttribute("filmes");
		if (Validator.validaListVazioOrNull(movies)) {
		    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lista de filmes em branco não pode ser exportada.");
		}
		
		OutputStream output = null;
		try {
			output = movieService.export(movies);
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
