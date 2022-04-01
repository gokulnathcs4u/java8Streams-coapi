package com.java8streams.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.java8streams.response.ErrorResponse;

@ControllerAdvice
public class CoApiExceptionHandler extends ResponseEntityExceptionHandler {

	
	@ExceptionHandler(CoApiException.class)
	protected ResponseEntity<ErrorResponse> handleExceptionInternal(CoApiException ex) {
		ErrorResponse response = new ErrorResponse();
		response.setStatus(ex.getStatus());
		response.setMessage(ex.getMessage());
		response.setTimestamp(ex.getTimestamp());
		response.setDebugMessage(ex.getDebugMessage());
		return new ResponseEntity<ErrorResponse>(response, ex.getStatus());
	}

}
