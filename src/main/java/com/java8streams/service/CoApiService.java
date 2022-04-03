package com.java8streams.service;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summingLong;
import static java.util.stream.Collectors.toMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
import com.java8streams.model.CoApiDaily;
import com.java8streams.model.CoApiStatus;
import com.java8streams.model.Country;
import com.java8streams.model.CountryList;
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
	public CoApiResponse getAllDetails() throws ApiException {
		ResponseEntity<List<CoApi>> responseEntity = restTemplate.exchange(
				coApiUrl.getHostName() + coApiUrl.getConfirmed(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CoApi>>() {
				});

		if (Objects.nonNull(responseEntity)) {
			CoApiResponse resp = new CoApiResponse();
			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				resp.setStatus(responseEntity.getStatusCode());
				resp.setValues(responseEntity.getBody());
				resp.setCount(responseEntity.getBody().size());
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
			} else {
				throw new ApiException(
						new ErrorBo(responseEntity.getStatusCode(), responseEntity.getStatusCode().getReasonPhrase()));
			}
			try {
				Map<String, Object> coapiGropuByRegionProvinceMap = coList.stream()
						.collect(groupingBy(CoApi::getCountryRegion, collectingAndThen(groupingBy((CoApi api) -> {
							return api.getProvinceState() != null ? api.getProvinceState() : api.getCombinedKey();
						}, mapping((CoApi api) -> {
							return new CoApiStatus(api.getConfirmed() != null ? api.getConfirmed() : 0,
									api.getDeaths() != null ? api.getDeaths() : 0,
									api.getRecovered() != null ? api.getRecovered() : 0,
									api.getActive() != null ? api.getActive() : 0);
						}, reducing(new CoApiStatus(0l, 0l, 0l, 0l), (oldval, newval) -> newval))), 
								(Map<String, CoApiStatus> unsortedMap) -> 
						unsortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.comparing(CoApiStatus::getConfirmed).reversed()))
						.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (oldval, newval) -> newval, LinkedHashMap::new))) ));
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

	/**
	 * 
	 * @return
	 * @throws ApiException
	 */
	public CoApiResponse getAllCountriesNames() throws ApiException {
		ResponseEntity<CountryList> responseEntity = restTemplate
				.exchange(coApiUrl.getHostName() + coApiUrl.getCountries(), HttpMethod.GET, null, CountryList.class);

		if (Objects.nonNull(responseEntity)) {
			CoApiResponse resp = new CoApiResponse();
			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				resp.setStatus(responseEntity.getStatusCode());
				CountryList respVal = responseEntity.getBody();
				resp.setValues(respVal);
				resp.setCount(respVal.getCountries().size());
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
	 * @param name
	 * @return
	 * @throws ApiException
	 */
	public CoApiResponse getCountryByName(String name) throws ApiException {
		ResponseEntity<CountryList> responseEntity = restTemplate
				.exchange(coApiUrl.getHostName() + coApiUrl.getCountries(), HttpMethod.GET, null, CountryList.class);

		if (Objects.nonNull(responseEntity)) {
			CoApiResponse resp = new CoApiResponse();
			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				resp.setStatus(responseEntity.getStatusCode());
				CountryList respVal = responseEntity.getBody();

				List<Country> countryList = respVal.getCountries().stream()
						.collect(filtering((country) -> country.getName().contains(name), Collectors.toList()));

				resp.setValues(countryList);
				resp.setCount(countryList.size());
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
	 * @param name
	 * @param filterVal
	 * @return
	 * @throws ApiException
	 */
	public CoApiResponse getCountryByName(String name, String filterVal) throws ApiException {
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
			} else {
				throw new ApiException(
						new ErrorBo(responseEntity.getStatusCode(), responseEntity.getStatusCode().getReasonPhrase()));
			}
			try {
				Map<String, Long> respList = coList.stream()
					.collect(filtering(coapi -> coapi.getIso3() != null && coapi.getIso3().equalsIgnoreCase(name),
								collectingAndThen(groupingBy((CoApi api) -> api.getProvinceState() != null ? api.getProvinceState()	: api.getCombinedKey(),
								 mapping(filterVal.contentEquals("deaths")? CoApi::getDeaths : filterVal.contentEquals("active") ? CoApi::getActive : CoApi::getConfirmed, 
									summingLong(sumVal -> sumVal))), (Map<String, Long> unsortedMap) -> 
											unsortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
													.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (oldval, newval) -> oldval, LinkedHashMap::new)))));

				resp.setValues(respList);
				resp.setCount(respList.size());

			} catch (Exception exception) {
				throw new ApiException(new ErrorBo(HttpStatus.SERVICE_UNAVAILABLE, exception.getLocalizedMessage()));
			}
			return resp;
		} else {
			throw new ApiException(
					new ErrorBo(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase()));
		}
	}

	/**
	 * 
	 * @param getDailyDetailsByDate
	 * @return
	 * @throws ApiException 
	 */
	public CoApiResponse getDailyDetailsByDate(String getDailyDetailsByDate) throws ApiException {
		LocalDate date = LocalDate.parse(getDailyDetailsByDate, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
		ResponseEntity<List<CoApiDaily>> responseEntity = restTemplate
				.exchange(coApiUrl.getHostName() + coApiUrl.getDaily(), HttpMethod.GET, null, new ParameterizedTypeReference<List<CoApiDaily>>() {
				}, date);

		if (Objects.nonNull(responseEntity)) {
			CoApiResponse resp = new CoApiResponse();
			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				resp.setStatus(responseEntity.getStatusCode());
				resp.setValues(responseEntity.getBody());
				resp.setCount(responseEntity.getBody().size());
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
