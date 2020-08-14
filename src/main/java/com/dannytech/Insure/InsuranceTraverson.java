package com.dannytech.Insure;

import java.util.ArrayList;

import org.springframework.hateoas.server.core.TypeReferences.CollectionModelType;
import org.springframework.stereotype.Service;

import com.dannytech.Insure.pojo.Insurance;

@Service
public class InsuranceTraverson extends AbstractTraverson {

	ZipTraverson zipTraverson = new ZipTraverson();
	AddressTraverson addressTraverson = new AddressTraverson();

	public ArrayList<Insurance> getAll() {

		CollectionModelType<Insurance> collectionModelType = new CollectionModelType<Insurance>() {
		};

		return new ArrayList<Insurance>(getTraverson().follow("insurances").toObject(collectionModelType).getContent());
	}

	public void addInsurance(Insurance insurance) {

		insurance.setAddressLink(zipTraverson.getZipLink(insurance.getAddress().getZip().getCode()));
		System.out.println(insurance.toString());
		restTemplate.postForEntity(getBASEURL() + "insurances", insurance, Insurance.class);
	}

}
