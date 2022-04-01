package com.java8streams.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoApiException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4874002750944023055L;
	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp = LocalDateTime.now();;
	private String message;
	private String debugMessage;

	public CoApiException() {
	}

	public CoApiException(HttpStatus status) {
		this();
		this.status = status;
	}

	public CoApiException(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = "Unexpected error";
		this.debugMessage = ex.getLocalizedMessage();
	}

	public CoApiException(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}

}
