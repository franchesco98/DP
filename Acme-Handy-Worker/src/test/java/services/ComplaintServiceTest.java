
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Complaint;
import domain.FixUpTask;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ComplaintServiceTest extends AbstractTest {

	@Autowired
	private CreditCardService	creditCardService;
	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private ComplaintService	complaintService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	@SuppressWarnings("deprecation")
	@Test
	public void testSaveFixUpTask() {
		super.authenticate("customer2");

		final Complaint complaint = this.complaintService.create();
		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(17577);

		System.out.println(this.complaintService.getReportByComplaint(458752));
	}
}
