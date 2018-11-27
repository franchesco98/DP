
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Box;
import domain.Customer;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CustomerServiceTest extends AbstractTest {

	@Autowired
	private CustomerService	customerService;
	@Autowired
	private BoxService		boxService;


	@Test
	public void testSaveCustomer() {
		Customer customer;
		Actor saved;
		Collection<Customer> customers;

		final Collection<Box> customerBoxes = new ArrayList<>();
		final Collection<Box> savedCustomerBoxes = new ArrayList<>();

		//in box, out box, trash box, and spam box
		Box inBox;
		Box outBox;
		Box trashBox;
		Box spamBox;

		Box savedInBox;
		Box savedOutBox;
		Box savedTrashBox;
		Box savedSpamBox;

		inBox = this.boxService.create();
		inBox.setName("In Box");
		inBox.setSystemBox(true);
		customerBoxes.add(inBox);

		outBox = this.boxService.create();
		outBox.setName("Out Box");
		outBox.setSystemBox(true);
		customerBoxes.add(outBox);

		trashBox = this.boxService.create();
		trashBox.setName("Trash Box");
		trashBox.setSystemBox(true);
		customerBoxes.add(trashBox);

		spamBox = this.boxService.create();
		spamBox.setName("Spam Box");
		spamBox.setSystemBox(true);
		customerBoxes.add(spamBox);

		savedInBox = this.boxService.save(inBox);
		savedOutBox = this.boxService.save(outBox);
		savedTrashBox = this.boxService.save(trashBox);
		savedSpamBox = this.boxService.save(spamBox);

		savedCustomerBoxes.add(savedInBox);
		savedCustomerBoxes.add(savedOutBox);
		savedCustomerBoxes.add(savedTrashBox);
		savedCustomerBoxes.add(savedSpamBox);

		customer = this.customerService.create();
		customer.setName("Manuel");
		customer.setSurname("Pérez Álvarez");
		customer.setPhoto("http://tumblr.com");
		customer.setEmail("manperalv@gmail.dp");
		customer.setPhoneNumber("+34 (689) 326892");
		customer.setAddress("Calle Runge-Kutta N2");
		customer.setNumSocialProfile(1);
		customer.setBoxes(savedCustomerBoxes);

		saved = this.customerService.save(customer);
		customers = this.customerService.findAll();
		Assert.isTrue(customers.contains(saved));
	}
}
