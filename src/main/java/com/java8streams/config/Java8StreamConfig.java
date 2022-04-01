package com.java8streams.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Java8StreamConfig {
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
