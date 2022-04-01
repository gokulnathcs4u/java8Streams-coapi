package com.java8streams.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2279031340526955647L;
	private ErrorBo errorBo;
	private String debugMessage;

	public ApiException() {
	}
	
	public ApiException(ErrorBo errorBo) {
		this();
		this.errorBo = errorBo;
	}

	public ApiException(ErrorBo errorBo, Throwable ex) {
		this();
		this.errorBo = errorBo;
		this.debugMessage = ex.getLocalizedMessage();
	}

}
