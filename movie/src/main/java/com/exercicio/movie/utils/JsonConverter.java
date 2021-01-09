package com.exercicio.movie.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exercicio.movie.api.nyt.vo.NewYorkTimesVo;
import com.exercicio.movie.api.themoviedb.vo.TheMovieDbCreditsVo;
import com.exercicio.movie.api.themoviedb.vo.TheMovieDbVo;
import com.exercicio.movie.exception.IntegrationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
	private static Logger logger = LoggerFactory.getLogger(Utils.class);

	public static TheMovieDbVo convertJsonObjectToTheMovieDbVo(InputStream inputStream) throws IntegrationException{
		TheMovieDbVo theMovieDbVo = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		try {
			theMovieDbVo = mapper.readValue(inputStream, TheMovieDbVo.class);
		} catch (JsonParseException e) {
			logger.error("JsonParseException Erro = "+ e.getMessage());
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException Erro = "+ e.getMessage());
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		} catch (IOException e) {
			logger.error("IOException Erro = "+ e.getMessage());
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		}
		return theMovieDbVo;
	}
	
	public static NewYorkTimesVo convertJsonObjectToNewYorkTimesVo(InputStream inputStream) throws IntegrationException{
		NewYorkTimesVo newYorkTimesVo = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		try {
			newYorkTimesVo = mapper.readValue(inputStream, NewYorkTimesVo.class);
		} catch (JsonParseException e) {
			logger.error("JsonParseException Erro = "+ e.getMessage());
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException Erro = "+ e.getMessage());
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		} catch (IOException e) {
			logger.error("IOException Erro = "+ e.getMessage());
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		}
		return newYorkTimesVo;
	}
	
	public static TheMovieDbCreditsVo convertJsonObjectToTheMovieDbCreditsVo(InputStream inputStream) throws IntegrationException {
		TheMovieDbCreditsVo theMovieDbCreditsVo = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		try {
			theMovieDbCreditsVo = mapper.readValue(inputStream, TheMovieDbCreditsVo.class);
		} catch (JsonParseException e) {
			logger.error("JsonParseException Erro = "+ e.getMessage());
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException Erro = "+ e.getMessage());
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		} catch (IOException e) {
			logger.error("IOException Erro = "+ e.getMessage());
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		}
		return theMovieDbCreditsVo;
	}
	
	public static JSONObject convertInputStreamReaderToJsonObject(HttpURLConnection con) throws IntegrationException{ 
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			JSONTokener tokener = new JSONTokener(bufferedReader);
			return new org.json.JSONObject(tokener);
		}catch(UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException Erro = "+ e.getMessage());
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		}catch(IOException e) {
			logger.error("IOException Erro = "+ e.getMessage());
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		}
	}
	

}
