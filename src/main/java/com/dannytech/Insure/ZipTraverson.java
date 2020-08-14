package com.dannytech.Insure;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.hateoas.client.Hop;
import org.springframework.hateoas.server.core.TypeReferences.CollectionModelType;
import org.springframework.stereotype.Service;

import com.dannytech.Insure.pojo.Zip;

@Service
public class ZipTraverson extends AbstractTraverson {

	public ArrayList<Zip> getAll() {

		CollectionModelType<Zip> collectionModelType = new CollectionModelType<Zip>() {
		};

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("page", "0");
		params.put("size", "50000");

		return new ArrayList<Zip>(getTraverson().follow("zips").withTemplateParameters(params)
				.toObject(collectionModelType).getContent());
	}

	public Zip findByCode(String code) {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("code", code);

		return getTraverson().follow("zips").follow("search").follow("findByCode").withTemplateParameters(params)
				.toObject(Zip.class);
	}

	public String getZipLink(String code) {

		Hop searchHop = Hop.rel("findByCode").withParameter("code", code);

		return getTraverson().follow("zips").follow("search").follow(searchHop).asLink().toString();
	}
}
