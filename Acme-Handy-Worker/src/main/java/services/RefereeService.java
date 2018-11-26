
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.UserAccount;
import domain.Referee;

@Service
@Transactional
public class RefereeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RefereeRepository	refereeRepository;


	// Constructors -----------------------------------------------------------

	public RefereeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Referee create() {
		Referee result;

		result = new Referee();
		//meterle el authority
		final UserAccount userAccount = new UserAccount();
		final Authority authotity = new Authority();
		authotity.setAuthority(Authority.REFEREE);
		final Collection<Authority> authorities = new ArrayList<>();
		authorities.add(authotity);
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);
		return result;
	}

	public Collection<Referee> findAll() {
		Collection<Referee> result;

		result = this.refereeRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Referee findOne(final int refereeId) {
		Referee result;

		result = this.refereeRepository.findOne(refereeId);
		Assert.notNull(result);

		return result;
	}

	public Referee save(final Referee referee) {
		Assert.notNull(referee);

		Referee result;

		result = this.refereeRepository.save(referee);

		return result;
	}

	public void delete(final Referee referee) {
		Assert.notNull(referee);
		Assert.isTrue(referee.getId() != 0);

		this.refereeRepository.delete(referee);
	}

	// Other business methods -------------------------------------------------

}
