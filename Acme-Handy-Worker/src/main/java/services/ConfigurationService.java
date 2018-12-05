
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import utilities.GeneratorTickers;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ConfigurationRepository	configurationRepository;


	// Constructors -----------------------------------------------------------

	public ConfigurationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Collection<Configuration> findAll() {
		Collection<Configuration> result;

		result = this.configurationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Configuration findOne(final int configurationId) {
		Configuration result;

		result = this.configurationRepository.findOne(configurationId);
		Assert.notNull(result);

		return result;
	}

	public Configuration save(final Configuration configuration) {
		Assert.notNull(configuration);

		Configuration result;

		result = this.configurationRepository.save(configuration);

		return result;
	}

	public void delete(final Configuration configuration) {
		Assert.notNull(configuration);
		Assert.isTrue(configuration.getId() != 0);

		this.configurationRepository.delete(configuration);
	}

	//business method 

	public String getValidTicker() {

		final String ticker = GeneratorTickers.ticker();

		for (final String tickerBD : this.configurationRepository.getAllTickers()) {

			if (tickerBD.equals(ticker)) {
				this.getValidTicker();
			}
		}

		return ticker;
	}

}
