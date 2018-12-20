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
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRepository;
import security.LoginService;
import domain.Actor;
import domain.Customer;
import domain.Endorsement;
import domain.Endorser;
import domain.HandyWorker;

@Service
@Transactional
public class EndorserService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private EndorserRepository	endorserRepository;
	@Autowired
	private EndorsementService	endorsementService;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private HandyWorkerService	handyWorkerService;
	@Autowired
	private BoxService			boxService;


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
		Assert.notNull(endorser.getUserAccount().getUsername());
		Assert.notNull(endorser.getUserAccount().getPassword());

		//comprobamos que no nos han dado cadenas vacias en los at opcionales
		if (endorser.getMiddleName() != null)
			Assert.isTrue(!(endorser.getMiddleName().trim().equals("")));

		if (endorser.getAddress() != null)
			Assert.isTrue(!(endorser.getAddress().trim().equals("")));

		Endorser result;

		if (endorser.getId() == 0) {

			endorser.setBoxes(this.boxService.originalBoxes());
			endorser.getUserAccount().setAccountNonLocked(true);
			endorser.setIsSuspicious(false);

			endorser.setScore(0.0);
			result = this.endorserRepository.save(endorser);
		} else {
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

	public Endorsement createAndUpdateEndorsemet(final Endorser sender, final Endorser recipient, final Endorsement endorsement) {
		Assert.notNull(sender);
		Assert.notNull(recipient);
		Assert.notNull(endorsement);

		int idPrincipal;
		idPrincipal = LoginService.getPrincipal().getId();
		Assert.isTrue(idPrincipal == sender.getId());

		//en el caso de que tengamos comment no debe ser vacio
		if (endorsement.getComments() != null) {
			final String[] comments = endorsement.getComments().split(";;");
			for (final String comment : comments)
				Assert.isTrue(!comment.trim().equals(""));

		}

		if (sender.getUserAccount().getAuthorities().contains("CUSTOMER")) {
			final Collection<HandyWorker> relatedHandyWorkers = this.customerService.getRelatedHandyWorkers(sender.getId());
			//TODO
			Assert.isTrue(relatedHandyWorkers.contains(recipient));
		} else {
			final Collection<Customer> relatedCustomers = this.handyWorkerService.getRelatedCustomers(sender.getId());
			//TODO
			Assert.isTrue(relatedCustomers.contains(recipient));
		}
		if (endorsement.getId() == 0) {
			endorsement.setMoment(new Date());
			endorsement.setSender(sender);
			endorsement.setRecipient(recipient);
		}
		this.endorsementService.save(endorsement);
		return endorsement;

	}

	public Collection<Endorsement> listHisEndorsements(final Endorser endorser) {
		Assert.notNull(endorser);

		int idPrincipal;
		idPrincipal = LoginService.getPrincipal().getId();
		Assert.isTrue(idPrincipal == endorser.getId());

		return this.endorserRepository.findEndorsementsSentByEndorserId(endorser.getId());
	}

	public void deleteEndorsement(final Endorser endorser, final Endorsement endorsement) {
		Assert.notNull(endorser);
		Assert.notNull(endorsement);

		int idPrincipal;
		idPrincipal = LoginService.getPrincipal().getId();
		Assert.isTrue(idPrincipal == endorser.getId());
		//Comprobar que es un endorsement realizado por el mismo
		Assert.isTrue(endorsement.getSender().getId() == endorser.getId());

		this.endorsementService.delete(endorsement);

	}

	public Collection<Endorsement> findEndorsementsRecipientByEndorserId(final Endorser endorser) {
		Assert.notNull(endorser);

		int idPrincipal;
		idPrincipal = LoginService.getPrincipal().getId();
		Assert.isTrue(idPrincipal == endorser.getUserAccount().getId());

		return this.endorserRepository.findEndorsementsRecipientByEndorserId(endorser.getId());
	}

}
