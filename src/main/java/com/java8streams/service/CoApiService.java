package com.java8streams.service;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;

import java.util.List;
import java.util.Map;
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
import com.java8streams.model.CoApiStatus;
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

	/**
	 * 
	 * @return
	 * @throws ApiException
	 */
	public CoApiResponse getAllByByCountryAndProvince() throws ApiException {
		ResponseEntity<List<CoApi>> responseEntity = restTemplate.exchange(
				coApiUrl.getHostName() + coApiUrl.getConfirmed(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CoApi>>() {
				});
		List<CoApi> coList = null;
		if (Objects.nonNull(responseEntity)) {
			CoApiResponse resp = new CoApiResponse();
			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				resp.setStatus(responseEntity.getStatusCode());
				coList = responseEntity.getBody();
				resp.setValues(coList);
				resp.setCount(coList.size());
			} else {
				throw new ApiException(
						new ErrorBo(responseEntity.getStatusCode(), responseEntity.getStatusCode().getReasonPhrase()));
			}
			try {
				Map<String, Map<String, CoApiStatus>> coapiGropuByRegionProvinceMap = coList.stream()
						.collect(groupingBy(CoApi::getCountryRegion, groupingBy((CoApi api) -> {
							return api.getProvinceState() != null ? api.getProvinceState() : api.getCombinedKey();
						}, mapping((CoApi api) -> {
							return new CoApiStatus(api.getConfirmed() != null ? api.getConfirmed() : 0, api.getDeaths() != null ? api.getDeaths() : 0, 
									api.getRecovered() != null ? api.getRecovered() : 0, api.getActive() != null ? api.getActive() : 0);
						}, reducing(new CoApiStatus(0l, 0l, 0l, 0l), (api1, api2) -> {
							return new CoApiStatus(api1.getConfirmed()+api2.getConfirmed(), api1.getDeaths()+api2.getDeaths(), 
									api1.getRecovered()+api2.getRecovered(), api1.getActive()+api2.getActive());
						})))));
				resp.setValues(coapiGropuByRegionProvinceMap);
				resp.setCount(coapiGropuByRegionProvinceMap.size());
			} catch (Exception exception) {
				throw new ApiException(new ErrorBo(HttpStatus.SERVICE_UNAVAILABLE, exception.getLocalizedMessage()));
			}
			return resp;
		} else {
			throw new ApiException(
					new ErrorBo(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase()));
		}
	}

}
