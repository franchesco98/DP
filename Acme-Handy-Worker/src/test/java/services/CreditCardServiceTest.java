
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Application;
import domain.CreditCard;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CreditCardServiceTest extends AbstractTest {

	@Autowired
	private CreditCardService	creditCardService;
	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	@SuppressWarnings("deprecation")
	@Test
	public void testSaveFixUpTask() {
		super.authenticate("customer4");

		final Application application = this.applicationService.findOne(17606);
		System.out.println("previus " + application.getStatus());
		this.customerService.rejecteCustomerApplication(application);
		System.out.println("beforeR " + application.getStatus());
		application.setStatus("PENDING");
		application.setCreditCard((CreditCard) this.creditCardService.getCreditCardsByPrincipal().toArray()[0]);
		this.customerService.acceptCustomerApplication(application);
		System.out.println("beforeA " + application.getStatus());
	}
}
