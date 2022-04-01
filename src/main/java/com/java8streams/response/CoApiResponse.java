package com.java8streams.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class CoApiResponse {
	
	private HttpStatus status;
	private Object values;
	private int count;
	private LocalDateTime timestamp = LocalDateTime.now();

}
