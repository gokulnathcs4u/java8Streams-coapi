package com.java8streams.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.java8streams.exception.ApiException;
import com.java8streams.exception.ErrorBo;
import com.java8streams.model.CoApi;
import com.java8streams.response.CoApiResponse;

/**
 * 
 * @author GoCool
 *
 */
@Service
public class CoApiService {

	@Autowired
	private CoApiUrl coApiUrl;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 
	 * @param yesterday
	 * @param twoDaysAgo
	 * @param allowNull
	 * @return
	 * @throws ApiException
	 */
	public CoApiResponse getAllByByCountry() throws ApiException {
		ResponseEntity<List<CoApi>> responseEntity = restTemplate.exchange(
				coApiUrl.getHostName() + coApiUrl.getConfirmed(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CoApi>>() {
				});
		
		if (Objects.nonNull(responseEntity)) {
			CoApiResponse resp = new CoApiResponse();
			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				resp.setStatus(responseEntity.getStatusCode());
				resp.setValues(responseEntity.getBody());
				resp.setCount(1);
			} else {
				throw new ApiException(
						new ErrorBo(responseEntity.getStatusCode(), responseEntity.getStatusCode().getReasonPhrase()));
			}
			return resp;
		} else {
			throw new ApiException(
					new ErrorBo(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase()));
		}
	}

	

}
