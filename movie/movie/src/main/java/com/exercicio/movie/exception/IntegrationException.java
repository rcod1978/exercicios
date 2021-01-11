package com.exercicio.movie.exception;

import org.springframework.http.HttpStatus;

public class IntegrationException extends GenericRuntimeException {

	private static final long serialVersionUID = -4033499425459180086L;
	
	String code;
	HttpStatus httpStatus;
	
	public IntegrationException(HttpStatus httpStatus, String message, String code) {
		super(message, code);
		this.code = code;
		this.httpStatus = httpStatus;
	}
	
	public IntegrationException(HttpStatus httpStatus, String message, String code, Throwable cause) {
		super(message, cause, code);
		this.code = code;
		this.httpStatus = httpStatus;
	}
	
	public IntegrationException(String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	
	public IntegrationException(String message, String code) {
		super(message, code);
		this.httpStatus = httpStatus;
	}
	
	public IntegrationException(String message) {
		super(message);
		this.httpStatus = httpStatus;
		this.code = code;
	}
	
	public IntegrationException(Throwable cause) {
		super(cause);
	}
	
	public String getCode() {
		return code;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
}
