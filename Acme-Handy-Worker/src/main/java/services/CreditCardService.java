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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Configuration;
import domain.CreditCard;
import domain.Customer;
import domain.Sponsor;

@Service
@Transactional
public class CreditCardService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CreditCardRepository	creditCardRepository;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private SponsorService			sponsorService;
	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public CreditCardService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public CreditCard create() {
		CreditCard result;

		result = new CreditCard();

		final Actor actor = this.actorService.findActorByPrincipal();

		result.setActor(actor);
		return result;
	}

	public Collection<CreditCard> findAll() {
		Collection<CreditCard> result;

		result = this.creditCardRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public CreditCard findOne(final int creditCardId) {
		CreditCard result;

		result = this.creditCardRepository.findOne(creditCardId);
		Assert.notNull(result);

		return result;
	}

	public CreditCard save(final CreditCard creditCardId) {
		Assert.notNull(creditCardId);

		final CreditCard result;

		result = this.creditCardRepository.save(creditCardId);
		return result;
	}

	// Other business methods -------------------------------------------------

	public void validCreditCard(final CreditCard creditCard) {
		Assert.notNull(creditCard);

		Boolean res = true;
		final int currentlyYear = new Date().getYear() - 100;
		final int currentlyMonth = new Date().getMonth() + 1;//0 es Enero y 11 Diciembre

		if (creditCard.getExpirationYear() == currentlyYear) {
			if (creditCard.getExpirationMonth() > currentlyMonth)
				res = false;
		} else if (creditCard.getExpirationYear() > currentlyYear)
			res = false;

		//coger la configuration actual
		final List<Configuration> configuration = new ArrayList<>(this.configurationService.findAll());
		final String[] creditCardMakes = configuration.get(0).getCreditCardMakes().split(",");

		for (final String brand : creditCardMakes) {
			if (creditCard.getBrandName().equals(brand.trim())) {
				res = true;
				break;
			}
			res = false;
		}
		Assert.isTrue(res);
	}

	public Collection<CreditCard> getCreditCardsByPrincipal() {

		//solo los customer o sponsor tienen creditCard
		final UserAccount UserPrincipal = LoginService.getPrincipal();

		final Authority customerAuthority = new Authority();
		customerAuthority.setAuthority("CUSTOMER");
		final Authority sponsorAuthority = new Authority();
		sponsorAuthority.setAuthority("SPONSOR");

		Assert.isTrue(UserPrincipal.getAuthorities().contains(customerAuthority) || UserPrincipal.getAuthorities().contains(sponsorAuthority));

		Collection<CreditCard> res = null;

		if (UserPrincipal.getAuthorities().contains(customerAuthority)) {
			final Customer principal = this.customerService.findByUserAccount(UserPrincipal);
			res = this.creditCardRepository.findByPrincipalId(principal.getId());
		} else {
			final Sponsor principal = this.sponsorService.findByUserAccount(UserPrincipal);
			res = this.creditCardRepository.findByPrincipalId(principal.getId());
		}

		return res;

	}

}
