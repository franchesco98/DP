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

import repositories.ActorRepository;
import repositories.BoxRepository;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository		actorRepository;
	@Autowired
	private BoxRepository		BoxRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private BoxService			BoxService;


	// Constructors -----------------------------------------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result;

		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		Actor result;

		result = this.actorRepository.save(actor);

		result.setBoxes(this.BoxService.originalBoxes());

		return result;
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(this.actorRepository.exists(actor.getId()));

		this.actorRepository.delete(actor);
	}

	// Other business methods -------------------------------------------------

	public UserAccount findUserAccount(final Actor actor) {
		Assert.notNull(actor);

		UserAccount result;

		result = this.userAccountService.findByActor(actor);

		return result;
	}
}
