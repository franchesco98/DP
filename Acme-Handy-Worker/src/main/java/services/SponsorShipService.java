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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorShipRepository;
import domain.SponsorShip;

@Service
@Transactional
public class SponsorShipService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SponsorShipRepository	sponsorShipRepository;


	// Constructors -----------------------------------------------------------

	public SponsorShipService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public SponsorShip create() {
		SponsorShip result;

		result = new SponsorShip();

		return result;
	}

	public Collection<SponsorShip> findAll() {
		Collection<SponsorShip> result;

		result = this.sponsorShipRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public SponsorShip findOne(final int sponsorShipId) {
		SponsorShip result;

		result = this.sponsorShipRepository.findOne(sponsorShipId);
		Assert.notNull(result);

		return result;
	}

	public SponsorShip save(final SponsorShip sponsorShipId) {
		Assert.notNull(sponsorShipId);

		final SponsorShip result;

		result = this.sponsorShipRepository.save(sponsorShipId);
		return result;
	}

	// Other business methods -------------------------------------------------

}
