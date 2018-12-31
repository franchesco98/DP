
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.HandyWorkerRepository;
import domain.HandyWorker;

@Component
@Transactional
public class StringToHandyWorker implements Converter<String, HandyWorker> {

	@Autowired
	HandyWorkerRepository	handyWorkerRepository;


	@Override
	public HandyWorker convert(final String text) {
		HandyWorker result;
		int id;

		try {
			if (org.springframework.util.StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.handyWorkerRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
