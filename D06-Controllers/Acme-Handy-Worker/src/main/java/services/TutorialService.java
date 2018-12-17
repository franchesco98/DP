/*
 * CustomerService.java
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
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.HandyWorker;
import domain.Section;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private TutorialRepository	tutorialRepository;


	// Constructors -----------------------------------------------------------

	public TutorialService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Tutorial create() {
		Tutorial result;

		result = new Tutorial();

		final Collection<Section> sections = new HashSet<>();
		result.setSections(sections);
		return result;
	}

	public Collection<Tutorial> findAll() {
		Collection<Tutorial> result;

		result = this.tutorialRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Tutorial findOne(final int tutorialId) {
		Assert.notNull(tutorialId);
		Tutorial result;

		result = this.tutorialRepository.findOne(tutorialId);
		Assert.notNull(result);

		return result;
	}

	public Tutorial save(final Tutorial tutorial, final HandyWorker handy) {
		Assert.notNull(tutorial);
		Assert.notNull(handy);

		Tutorial result;

		//solo lo usan handy
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.HANDYWORKER));

		if (tutorial.getId() == 0) {
			tutorial.setMoment(new Date());
		} else {
			//comprobar que ese tutorial es del handy	
			Assert.isTrue(tutorial.getHandyWorker().getId() == handy.getId());
		}

		result = this.tutorialRepository.save(tutorial);
		return result;

	}
	public void delete(final Tutorial tutorial, final HandyWorker handy) {
		Assert.notNull(tutorial);
		Assert.notNull(handy);
		//comprobar que ese tutorial es del handy	
		Assert.isTrue(tutorial.getHandyWorker().getId() == handy.getId());

		//un tutorial no se puede borrar si tiene un sponsorShip
		Assert.isTrue(this.tutorialRepository.getSponsorShip(tutorial.getId()).size() == 0);

		this.tutorialRepository.delete(tutorial);
	}
	// Other business methods -------------------------------------------------

	public Collection<Section> findSectionByTutorial(final int tutorialId) {

		return this.tutorialRepository.getSectionByTutorial(tutorialId);
	}

}
