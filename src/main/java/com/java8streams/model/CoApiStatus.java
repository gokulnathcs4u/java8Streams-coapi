package com.java8streams.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoApiStatus {
    private Long confirmed;
    private Long deaths;
    private Long recovered;
    private Long active;
}
