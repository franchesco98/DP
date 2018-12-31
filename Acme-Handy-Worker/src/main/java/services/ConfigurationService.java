
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
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

		//sacamos la userAcount del principal
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();

		//solo lo usan admin
		final Authority adminAuthority = new Authority();
		adminAuthority.setAuthority("ADMIN");
		Assert.isTrue(userAcount.getAuthorities().contains(adminAuthority));

		this.configurationRepository.delete(configuration);
	}

	//business method

	public String getValidTicker() {

		final String ticker = GeneratorTickers.ticker();
		//TODO
		//final Collection<String> tickersBD = this.configurationRepository.getAllTickers();
		//		for (final String tickerBD : tickersBD)
		//			if (tickerBD.equals(ticker))
		//				System.out.println("Gol");

		return ticker;
	}

	//metodo para splitear string

	public Collection<String> splitComa(final String toSplit) {

		final Collection<String> res = new ArrayList<>();

		for (final String item : toSplit.split(","))
			res.add(item.trim());

		return res;

	}

}
