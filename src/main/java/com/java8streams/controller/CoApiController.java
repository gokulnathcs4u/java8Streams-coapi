package com.java8streams.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java8streams.exception.ApiException;
import com.java8streams.exception.CoApiException;
import com.java8streams.response.CoApiResponse;
import com.java8streams.service.CoApiService;

/**
 * 
 * @author GoCool
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/coapi")
public class CoApiController {

	@Autowired
	private CoApiService coApiService;

	/**
	 * 
	 * @param yesterday
	 * @param twoDaysAgo
	 * @param allowNull
	 * @return
	 * @throws CoApiException
	 */
	@GetMapping("/all/byCountry")
	public ResponseEntity<CoApiResponse> getAllByByCountry()
			throws CoApiException {
		CoApiResponse resp = new CoApiResponse();
		try {
			resp = coApiService.getAllByByCountry();
		} catch (ApiException exception) {
			throw new CoApiException(exception.getErrorBo().getErrorCode(), exception.getErrorBo().getDescription(),
					exception);
		} catch (Exception exception) {
			throw new CoApiException(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),
					exception);
		}
		return new ResponseEntity<CoApiResponse>(resp, resp.getStatus());
	}

	
}
