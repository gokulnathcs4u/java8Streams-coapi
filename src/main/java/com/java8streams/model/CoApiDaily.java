package com.java8streams.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoApiDaily {
	private String provinceState;
	private String countryRegion;
	private String lastUpdate;
	private Long confirmed;
	private Long deaths;
	private Long recovered;
}
