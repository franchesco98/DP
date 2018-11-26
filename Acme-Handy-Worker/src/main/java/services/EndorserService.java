/*
 * ActorService.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRepository;
import security.LoginService;
import domain.Actor;
import domain.Endorser;

@Service
@Transactional
public class EndorserService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private EndorserRepository	endorserRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public EndorserService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Collection<Endorser> findAll() {
		Collection<Endorser> result;

		result = this.endorserRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Actor findOne(final int endorserId) {
		Assert.isTrue(endorserId != 0);

		Actor result;

		result = this.endorserRepository.findOne(endorserId);
		Assert.notNull(result);

		return result;
	}

	public Actor save(final Endorser endorser) {
		Assert.notNull(endorser);

		Endorser result;

		if (endorser.getId() == 0)
			result = this.endorserRepository.save(endorser);
		else {
			int idPrincipal;
			idPrincipal = LoginService.getPrincipal().getId();
			Assert.isTrue(idPrincipal == endorser.getId());
			result = this.endorserRepository.save(endorser);
		}

		return result;
	}

	public void delete(final Endorser endorser) {
		Assert.notNull(endorser);
		Assert.isTrue(endorser.getId() != 0);
		Assert.isTrue(this.endorserRepository.exists(endorser.getId()));
		//comprobar que el endorser no tenga endorsement
		Assert.isTrue(this.endorserRepository.findEndorsementsByEndorserId(endorser.getId()).size() == 0);

		this.endorserRepository.delete(endorser);
	}

	// Other business methods -------------------------------------------------

}
