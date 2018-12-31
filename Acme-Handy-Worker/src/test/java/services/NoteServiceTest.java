
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class NoteServiceTest extends AbstractTest {

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
	@Autowired
	private NoteService			noteService;


	@SuppressWarnings("deprecation")
	@Test
	public void testSaveFixUpTask() {
		super.authenticate("customer2");

		System.out.println(this.noteService.getNotesByReportId(17609));
	}
}
