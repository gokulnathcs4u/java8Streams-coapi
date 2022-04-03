package com.java8streams.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	 * Get details of all countires 
	 * @return
	 * @throws CoApiException
	 */
	@GetMapping("/all/details")
	public ResponseEntity<CoApiResponse> getAllDetails() throws CoApiException {
		CoApiResponse resp = new CoApiResponse();
		try {
			resp = coApiService.getAllDetails();
		} catch (ApiException exception) {
			throw new CoApiException(exception.getErrorBo().getErrorCode(), exception.getErrorBo().getDescription(),
					exception);
		} catch (Exception exception) {
			throw new CoApiException(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),
					exception);
		}
		return new ResponseEntity<CoApiResponse>(resp, resp.getStatus());
	}

	/**
	 * 
	 * @return
	 * @throws CoApiException
	 */
	@GetMapping("/all/byCountryAndProvince")
	public ResponseEntity<CoApiResponse> getAllByByCountryAndProvince() throws CoApiException {
		CoApiResponse resp = new CoApiResponse();
		try {
			resp = coApiService.getAllByByCountryAndProvince();
		} catch (ApiException exception) {
			throw new CoApiException(exception.getErrorBo().getErrorCode(), exception.getErrorBo().getDescription(),
					exception);
		} catch (Exception exception) {
			throw new CoApiException(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),
					exception);
		}
		return new ResponseEntity<CoApiResponse>(resp, resp.getStatus());
	}
	
	/**
	 * 
	 * @return
	 * @throws CoApiException
	 */
	@GetMapping("/all/countries")
	public ResponseEntity<CoApiResponse> getAllCountriesNames() throws CoApiException {
		CoApiResponse resp = new CoApiResponse();
		try {
			resp = coApiService.getAllCountriesNames();
		} catch (ApiException exception) {
			throw new CoApiException(exception.getErrorBo().getErrorCode(), exception.getErrorBo().getDescription(),
					exception);
		} catch (Exception exception) {
			throw new CoApiException(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),
					exception);
		}
		return new ResponseEntity<CoApiResponse>(resp, resp.getStatus());
	}
	
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws CoApiException
	 */
	@GetMapping("/all/countries/{name}")
	public ResponseEntity<CoApiResponse> getCountryByName(@PathVariable String name) throws CoApiException {
		CoApiResponse resp = new CoApiResponse();
		try {
			resp = coApiService.getCountryByName(name);
		} catch (ApiException exception) {
			throw new CoApiException(exception.getErrorBo().getErrorCode(), exception.getErrorBo().getDescription(),
					exception);
		} catch (Exception exception) {
			throw new CoApiException(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),
					exception);
		}
		return new ResponseEntity<CoApiResponse>(resp, resp.getStatus());
	}
	
	/**
	 * 
	 * @param name
	 * @param filterVal
	 * @return
	 * @throws CoApiException
	 */
	@GetMapping("/countries/details/{name}")
	public ResponseEntity<CoApiResponse> getCountryByName(@PathVariable(required = true) String name, 
			@RequestParam(required = true) String filterVal) throws CoApiException {
		CoApiResponse resp = new CoApiResponse();
		try {
			resp = coApiService.getCountryByName(name, filterVal);
		} catch (ApiException exception) {
			throw new CoApiException(exception.getErrorBo().getErrorCode(), exception.getErrorBo().getDescription(),
					exception);
		} catch (Exception exception) {
			throw new CoApiException(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),
					exception);
		}
		return new ResponseEntity<CoApiResponse>(resp, resp.getStatus());
	}
	
	@GetMapping("/daily/{date}")
	public ResponseEntity<CoApiResponse> getDailyDetailsByDate(@PathVariable(required = true) String date) 
			throws CoApiException {
		CoApiResponse resp = new CoApiResponse();
		try {
			resp = coApiService.getDailyDetailsByDate(date);
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
