package com.exercicio.movie.exception;

public abstract class GenericRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	
	public void setCode(String code) {
		this.code = code;
	}

	public GenericRuntimeException(String message) {
		super(message);
	}
	
	public GenericRuntimeException(String message, String code) {
		super(message);
	}

	public GenericRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public GenericRuntimeException(String message, Throwable cause, String code) {
		super(message, cause);
		this.code = code;
	}

	public GenericRuntimeException(Throwable cause, String code) {
		super(cause);
		this.code = code;
	}	
	
	public GenericRuntimeException(Throwable cause) {
		super(cause);
	}	
	
	
}
