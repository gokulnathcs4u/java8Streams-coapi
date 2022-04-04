package com.java8streams.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CoApiResponse {
	
	private HttpStatus status;
	private Object values;
	private int count;
	private LocalDateTime timestamp = LocalDateTime.now();

}
