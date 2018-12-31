/*
 * ReportToStringConverter.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Report;

@Component
@Transactional
public class ReportToString implements Converter<Report, String> {

	@Override
	public String convert(final Report report) {
		String result;

		if (report == null)
			result = null;
		else
			result = String.valueOf(report.getId());

		return result;
	}

}
