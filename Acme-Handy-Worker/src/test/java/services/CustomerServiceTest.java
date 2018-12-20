
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import security.UserAccountService;
import utilities.AbstractTest;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CustomerServiceTest extends AbstractTest {

	@Autowired
	private CustomerService		customerService;
	@Autowired
	private BoxService			boxService;
	@Autowired
	private PhaseService		phaseService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;
	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private UserAccountService	userAccountService;
	@Autowired
	private ActorService		actorService;


	@Test
	public void testCreateCustomer() {
		final Customer customer = this.customerService.create();

		Assert.isTrue(customer != null);
	}

	@Test
	public void testSaveCustomer() {
		Customer customer;
		Customer saved;
		final Collection<Customer> customers;
		//		final UserAccount userAccount = new UserAccount();
		//		final Authority customerAuthority = new Authority();
		//		customerAuthority.setAuthority(Authority.CUSTOMER);
		//		final Collection<Authority> authorities = new ArrayList<>();
		//		authorities.add(customerAuthority);
		//		userAccount.setAuthorities(authorities);
		//		userAccount.setUsername("jdhsaukdasdasdaew");
		//		userAccount.setPassword("audskhasiudghasiuy");

		//		final Collection<Box> savedCustomerBoxes = this.boxService.originalBoxes();

		customer = this.customerService.create();
		customer.getUserAccount().setUsername("jdhsaukdasdasdaew");
		customer.getUserAccount().setPassword("audskhasiudghasiuy");
		customer.setName("Venancio");
		customer.setSurname("Pérez Álvarez");
		customer.setPhoto("http://tumblr.com");
		customer.setEmail("manperalv@hotmail.dp");
		customer.setPhoneNumber("+34 (689) 326892");
		customer.setAddress("Calle Runge-Kutta N2");
		//		customer.setBoxes(savedCustomerBoxes);
		customer.getUserAccount().setAccountNonLocked(true);
		customer.setIsSuspicious(false);
		customer.setScore(1.);
		//		customer.setUserAccount(userAccount);

		saved = this.customerService.save(customer);
		customers = this.customerService.findAll();
		Assert.isTrue(customers.contains(saved));
	}
	@Test
	public void testFindByPrincipal() {
		super.authenticate("customer1");
		final int idPrincipal = LoginService.getPrincipal().getId();
		Customer principal;

		principal = this.customerService.findCustomerByPrincipal();

		Assert.isTrue(principal.getUserAccount().getId() == idPrincipal);
	}

	@Test
	public void testFindByUserAccount() {
		super.authenticate("customer1");
		final Customer customer = this.customerService.findCustomerByPrincipal();
		final Customer customerByUserAccount = this.customerService.findByUserAccount(customer.getUserAccount());

		Assert.notNull(customerByUserAccount);
		Assert.isTrue(customer.getId() == customerByUserAccount.getId());
	}

	@Test
	public void testFindFixUpTasksCreatedByCustomer() {
		super.authenticate("customer1");
		final Collection<FixUpTask> fixUpTasks = this.fixUpTaskService.findAll();
		Collection<FixUpTask> customerFixUpTask;
		final Customer principal = this.customerService.findCustomerByPrincipal();
		customerFixUpTask = this.customerService.listingFixUpTasksCreatedByCustomer(principal.getId());

		Assert.isTrue(fixUpTasks.containsAll(customerFixUpTask));

	}

	@Test
	public void testFindApplicationsByCustomerId() {
		super.authenticate("customer1");
		final Customer principal = this.customerService.findCustomerByPrincipal();
		final Collection<Application> applications = this.applicationService.findAll();
		final Collection<Application> customerApplications = this.customerService.findApplicationsByCustomerId(principal);

		Assert.notNull(customerApplications);
		Assert.isTrue(applications.containsAll(customerApplications));
	}
}
