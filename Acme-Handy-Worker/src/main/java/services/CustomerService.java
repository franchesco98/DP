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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Category;
import domain.Customer;
import domain.FixUpTask;
import domain.Warranty;

@Service
@Transactional
public class CustomerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CustomerRepository	customerRepository;
	@Autowired
	private FixUpTaskService	fixUpTaskService;
	@Autowired
	private ComplaintService	complaintService;
	@Autowired
	private ApplicationService	applicationService;


	// Constructors -----------------------------------------------------------

	public CustomerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Customer create() {
		Customer result;

		result = new Customer();
		//meterle el authority
		final UserAccount userAccount = new UserAccount();
		final Authority authotity = new Authority();
		authotity.setAuthority(Authority.CUSTOMER);
		final Collection<Authority> authorities = new ArrayList<>();
		authorities.add(authotity);
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);

		return result;
	}
	public Collection<Customer> findAll() {
		Collection<Customer> result;

		result = this.customerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Customer findOne(final int customerId) {
		Customer result;

		result = this.customerRepository.findOne(customerId);
		Assert.notNull(result);

		return result;
	}

	public Customer save(final Customer customer) {
		Assert.notNull(customer);

		Customer result;

		if (customer.getId() == 0) {
			int idPrincipal;
			idPrincipal = LoginService.getPrincipal().getId();
			Assert.isTrue(idPrincipal == customer.getId());

		} else {
			//comprobar si el que borraba su cuenta era el de la cuenta
			final UserAccount userAccount;
			userAccount = LoginService.getPrincipal();
			Assert.isTrue(customer.getUserAccount().equals(userAccount));
		}

		result = this.customerRepository.save(customer);

		return result;
	}

	public void delete(final Customer customer) {
		Assert.notNull(customer);
		Assert.isTrue(customer.getId() != 0);

		//lo que dijo de que teniamos que comprobar si el que borraba su cuenta era el de la cuenta
		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(customer.getUserAccount().equals(userAccount));
		//no puede tener fixUpTask 
		Assert.isTrue(this.fixUpTaskService.numFixUpTaskByCustomer(customer).equals(0));
		//no puede tener complaint
		Assert.isTrue(this.complaintService.numComplaintByCustomer(customer).equals(0));

		this.customerRepository.delete(customer);
	}

	// Other business methods -------------------------------------------------

	public Customer findByPrincipal() {
		Customer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Customer findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Customer result;

		result = this.customerRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	//R11.1
	public Collection<FixUpTask> fixUpTasksCreatedByCustomer(final Customer customer) {
		return this.customerRepository.findByCustomerId(customer.getId());
	}

	public FixUpTask showFixUpTask(final int fixUpTaskId, final Customer customer) {
		final int customerId = LoginService.getPrincipal().getId();
		Assert.isTrue(customerId == customer.getId());

		final FixUpTask res = this.fixUpTaskService.findOne(fixUpTaskId);
		return res;
	}

	public FixUpTask saveFixUpTaskCustomer(final Customer customer, final FixUpTask fixUpTask, final Category category, final Warranty warranty) {
		final int customerId = LoginService.getPrincipal().getId();
		Assert.isTrue(customerId == customer.getId());
		Assert.isTrue(fixUpTask.getCustomer().getId() == customerId);
		Assert.isTrue(fixUpTask.getStartTime().before(fixUpTask.getEndTime()));

		//TODO HACER UN METODO QUE TE CREE TIKECTS

		final FixUpTask res;

		fixUpTask.setCustomer(customer);
		fixUpTask.setMoment(new Date());
		//		fixUpTask.setTicker(ticker);
		fixUpTask.setCategory(category);
		fixUpTask.setWarranty(warranty);
		res = this.fixUpTaskService.save(fixUpTask);

		return res;

	}

	//TODO revisar

	public void deleteFixUpTaskCustomer(final Customer customer, final FixUpTask f) {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains("CUSTOMER"));

		final int customerId = LoginService.getPrincipal().getId();
		Assert.isTrue(customerId == customer.getId());
		Assert.isTrue(f.getCustomer().getId() == customerId);

		this.fixUpTaskService.delete(f);
	}

	public Collection<Application> findApplicationsByCustomerId(final int customerId) {
		final Collection<Application> res = this.customerRepository.findApplicationsByCustomerId(customerId);
		return res;
	}

	public Application updateCustomerApplication(final Customer c, final int applicationId, final String status, final String comment) {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains("CUSTOMER"));

		final int customerId = LoginService.getPrincipal().getId();
		Assert.isTrue(customerId == c.getId());

		final Application a = this.applicationService.findOne(applicationId);
		final Application res;

		if (a.getStatus().equals("PENDING") && !status.equals("PENDING"))
			a.setStatus(status);
		//cambiar tarjeta de crédito

		a.setComment(comment);

		res = this.applicationService.save(a);

		return res;
	}

}
