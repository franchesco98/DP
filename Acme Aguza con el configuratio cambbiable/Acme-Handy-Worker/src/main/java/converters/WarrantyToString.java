
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Warranty;

@Component
@Transactional
public class WarrantyToString implements Converter<Warranty, String> {

	@Override
	public String convert(final Warranty warranty) {
		String result;

		if (warranty == null)
			result = null;
		else
			result = String.valueOf(warranty.getTitle());

		return result;
	}

}
