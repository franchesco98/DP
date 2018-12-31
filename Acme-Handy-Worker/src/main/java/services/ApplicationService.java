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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import repositories.HandyWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.HandyWorker;

@Service
@Transactional
public class ApplicationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ApplicationRepository	applicationRepository;
	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;


	// Constructors -----------------------------------------------------------

	public ApplicationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Application create() {
		Application result;

		final UserAccount user = LoginService.getPrincipal();
		final Integer userID = user.getId();
		final Authority handyWorkerAuthority = new Authority();
		handyWorkerAuthority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(handyWorkerAuthority));

		final HandyWorker hw = this.handyWorkerRepository.findByUserAccountId(userID);

		result = new Application();
		result.setHandyWorker(hw);
		result.setStatus("PENDING");
		result.setMoment(new Date());

		return result;
	}
	public Collection<Application> findAll() {
		Collection<Application> result;

		result = this.applicationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Application findOne(final int ApplicationId) {
		Application result;

		result = this.applicationRepository.findOne(ApplicationId);
		Assert.notNull(result);

		return result;
	}

	public Application save(final Application application) {
		Assert.notNull(application);

		Application result;

		result = this.applicationRepository.save(application);

		return result;
	}

	public void delete(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getId() != 0);

		this.applicationRepository.delete(application);
	}
	// Other business methods -------------------------------------------------

	public Collection<Application> findApplicationsOfHW() {

		Collection<Application> result;
		final UserAccount user = LoginService.getPrincipal();
		final Integer userId = user.getId();
		final Authority handyWorkerAuthority = new Authority();
		handyWorkerAuthority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(handyWorkerAuthority));

		result = this.applicationRepository.findApplicationsOfHW(userId);

		return result;
	}
}
