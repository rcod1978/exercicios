package com.exercicio.movie.connection;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exercicio.movie.exception.IntegrationException;
import com.exercicio.movie.utils.Constants;

public class Connection {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	protected URL url = null;
	protected HttpURLConnection con = null;
	
	private void getConnection(String urlString) throws IntegrationException {
		try {
			if(url != null) {
				close();
			}
			url = new URL(urlString);
			con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");
		}catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error("MalformedURLException Erro = "+ e.getMessage());
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			logger.error("ProtocolException Erro = "+ e.getMessage());
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IOException Erro = "+ e.getMessage());
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		}
	}
	
	
	protected InputStream executeConnection(String urlString) throws IntegrationException{
		getConnection(urlString);
		try {
			return con.getInputStream();
		} catch (IOException e) {
			logger.error("IOException Erro = "+ e.getMessage());
			throw new IntegrationException(Constants.INTEGRATION_EXCEPTION);
		}
	}	
	
	protected void close() {
		if(con != null) {
			con.disconnect();
		}
	}
}
