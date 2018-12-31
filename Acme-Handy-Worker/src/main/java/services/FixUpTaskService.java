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

import repositories.FixUpTaskRepository;
import security.Authority;
import security.LoginService;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Phase;

@Service
@Transactional
public class FixUpTaskService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FixUpTaskRepository		fixUpTaskRepository;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public FixUpTaskService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public FixUpTask create() {
		FixUpTask result;

		//solo los customer crean fixUpTask
		final Customer customer = this.customerService.findByUserAccount(LoginService.getPrincipal());
		//comprobamos que el principal es un customer
		final Authority customerAuthority = new Authority();
		customerAuthority.setAuthority("CUSTOMER");
		Assert.isTrue(customer.getUserAccount().getAuthorities().contains(customerAuthority));

		result = new FixUpTask();

		//le metemos el principal
		result.setCustomer(customer);
		//le ponemos el moment
		result.setMoment(new Date());
		//ponemos su ticker
		result.setTicker(this.configurationService.getValidTicker());

		return result;
	}

	public Collection<FixUpTask> findAll() {
		Collection<FixUpTask> result;

		result = this.fixUpTaskRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public FixUpTask findOne(final int fixUpTaskId) {
		FixUpTask result;

		result = this.fixUpTaskRepository.findOne(fixUpTaskId);
		Assert.notNull(result);

		return result;
	}

	public FixUpTask save(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);

		FixUpTask result;

		result = this.fixUpTaskRepository.save(fixUpTask);

		return result;
	}

	public void delete(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);
		Assert.isTrue(fixUpTask.getId() != 0);

		this.fixUpTaskRepository.delete(fixUpTask);
	}

	// Other business methods -------------------------------------------------

	//R11.1
	public Customer obtainCustomer(final FixUpTask fixUpTask) {
		return this.fixUpTaskRepository.findCustomerByFixUpTaskId(fixUpTask.getId());
	}

	public Collection<Phase> getPhasesPerFixUpTask(final int fixUpTaskId) {

		return this.fixUpTaskRepository.findPhasesPerFixUpTask(fixUpTaskId);

	}

	public HandyWorker getAcceptedHandyWorker(final int fixUpTaskId) {

		return this.fixUpTaskRepository.findAcceptedHandyWorker(fixUpTaskId);

	}

	//R35.1

	public Collection<Complaint> getRelatedComplaint(final FixUpTask fixUpTask) {

		return this.fixUpTaskRepository.getRelatedComplaint(fixUpTask.getId());

	}

}
