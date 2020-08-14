package com.dannytech.Insure;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

@Data
public abstract class AbstractTraverson {

	private final String BASEURL = "http://localhost:8080/";
	private Traverson traverson = new Traverson(URI.create(BASEURL), MediaTypes.HAL_JSON);
	@Autowired
	protected RestTemplate restTemplate;
}
