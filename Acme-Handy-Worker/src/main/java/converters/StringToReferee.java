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

import repositories.RefereeRepository;
import domain.Referee;

@Component
@Transactional
public class StringToReferee implements Converter<String, Referee> {

	@Autowired
	RefereeRepository	RefereeRepository;


	@Override
	public Referee convert(final String text) {
		Referee result;

		try {

			result = this.RefereeRepository.findRefereeByUserName(text);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
