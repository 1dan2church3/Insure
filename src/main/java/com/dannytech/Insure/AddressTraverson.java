package com.dannytech.Insure;

import org.springframework.hateoas.client.Hop;
import org.springframework.stereotype.Service;

import com.dannytech.Insure.pojo.Address;

@Service
public class AddressTraverson extends AbstractTraverson {

	public String getAddressLink(String code) {

		Hop searchHop = Hop.rel("findByCode").withParameter("code", code);

		return getTraverson().follow("zips").follow("search").follow(searchHop).asLink().toString();
	}

	public void addAddress(Address address) {

		System.out.println("Traverson " + address.getZip());
		restTemplate.postForObject(getBASEURL() + "addresses", address, String.class);
	}
}
