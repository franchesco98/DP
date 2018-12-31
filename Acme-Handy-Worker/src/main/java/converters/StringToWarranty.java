/*
 * StringToActorConverter.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.WarrantyRepository;
import domain.Warranty;

@Component
@Transactional
public class StringToWarranty implements Converter<String, Warranty> {

	@Autowired
	WarrantyRepository	WarrantyRepository;


	@Override
	public Warranty convert(final String text) {
		Warranty result;

		try {

			result = this.WarrantyRepository.findWarrantyByTitle(text);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
