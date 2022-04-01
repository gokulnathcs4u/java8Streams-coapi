package com.java8streams.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@PropertySource(value = { "classpath:/coapi.properties" })
@ConfigurationProperties(prefix = "coapi.api")
public class CoApiUrl {
	private String hostName;
	private String confirmed;
	private String recovered;
	private String deaths;
	private String countries;

}
