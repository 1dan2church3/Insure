package com.dannytech.Insure.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Zip {

	private String code;
	private String state;
	private String city;
}
