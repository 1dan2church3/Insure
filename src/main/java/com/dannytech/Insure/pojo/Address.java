package com.dannytech.Insure.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

	private String streetAddress;
	private Zip zip;
	private String zipLink;
}
