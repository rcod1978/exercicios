package com.exercicio.movie.exception;

public class BussinessException extends IntegrationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4833472765229056928L;

	public BussinessException(String message) {
		super(message);
	}
	
	public BussinessException(String message, String code) {
		super(message, code);
	}
	
	public BussinessException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BussinessException(Throwable cause) {
		super(cause);
	}
}
