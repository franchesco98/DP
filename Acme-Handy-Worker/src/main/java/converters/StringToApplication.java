
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.ApplicationRepository;
import domain.Application;

@Component
@Transactional
public class StringToApplication implements Converter<String, Application> {

	@Autowired
	ApplicationRepository	applicationRepository;


	@Override
	public Application convert(final String text) {
		Application result;
		int id;

		try {
			if (org.springframework.util.StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.applicationRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
