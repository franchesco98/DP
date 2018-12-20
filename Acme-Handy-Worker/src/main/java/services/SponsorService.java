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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.CreditCard;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SponsorRepository	sponsorRepository;
	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserAccountService	userAccountService;
	@Autowired
	private BoxService			boxService;


	// Constructors -----------------------------------------------------------

	public SponsorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Sponsor create() {
		Sponsor result;

		result = new Sponsor();
		//meterle el authority
		final UserAccount userAccount = new UserAccount();
		final Authority authotity = new Authority();
		authotity.setAuthority(Authority.SPONSOR);
		final Collection<Authority> authorities = new ArrayList<>();
		authorities.add(authotity);
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);
		return result;
	}

	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result;

		result = this.sponsorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Sponsor findOne(final int sponsorId) {
		Sponsor result;

		result = this.sponsorRepository.findOne(sponsorId);
		Assert.notNull(result);

		return result;
	}

	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		Assert.notNull(sponsor.getUserAccount().getUsername());
		Assert.notNull(sponsor.getUserAccount().getPassword());

		//comprobamos que no nos han dado cadenas vacias en los at opcionales
		if (sponsor.getMiddleName() != null)
			Assert.isTrue(!(sponsor.getMiddleName().trim().equals("")));

		if (sponsor.getAddress() != null)
			Assert.isTrue(!(sponsor.getAddress().trim().equals("")));

		final Sponsor result;
		if (sponsor.getId() != 0) {

			//comprobar que es el
			final UserAccount userAccount;
			userAccount = LoginService.getPrincipal();
			Assert.isTrue(sponsor.getUserAccount().equals(userAccount));

		} else {

			sponsor.setBoxes(this.boxService.originalBoxes());
			sponsor.setIsBanned(false);
			sponsor.setIsSuspicious(false);

			//solo lo usan admin
			UserAccount userAcount;
			userAcount = LoginService.getPrincipal();
			Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));
		}

		result = this.sponsorRepository.save(sponsor);
		return result;
	}

	// Other business methods -------------------------------------------------

	public Sponsor findSponsorByPrincipal() {
		Sponsor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public Sponsor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Sponsor result;

		result = this.sponsorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Collection<CreditCard> getCreditCardsBySponsorId(final int sponsorId) {

		return this.sponsorRepository.findCreditCardsBySponsorId(sponsorId);
	}

}
