package com.dannytech.Insure.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Insurance {

	private String name;
	private Address address;
	private String addressLink;
}
