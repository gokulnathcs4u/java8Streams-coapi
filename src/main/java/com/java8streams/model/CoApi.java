package com.java8streams.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoApi {
	
	private String provinceState;
    private String countryRegion;
    private Double lastUpdate;
    @JsonProperty("lat")
    private Double lattitude;
    @JsonProperty("long")
    private Double  longitude;
    private Long confirmed;
    private Long deaths;
    private Long recovered;
    private Long active;
    private String admin2;
    private String fips;
    private String  combinedKey;
    private Double incidentRate;
    private Long peopleTested;
    private Long peopleHospitalized;
    private Long uid;
    private String iso3;
    private Long cases28Days;
    private Long deaths28Days;
    private String iso2;


}
