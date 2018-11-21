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

import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CustomerRepository	customerRepository;


	// Constructors -----------------------------------------------------------

	public CustomerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Customer create() {
		Customer result;

		result = new Customer();

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

		result = this.customerRepository.save(customer);

		return result;
	}

	public void delete(final Customer customer) {
		Assert.notNull(customer);
		Assert.isTrue(customer.getId() != 0);

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

}
