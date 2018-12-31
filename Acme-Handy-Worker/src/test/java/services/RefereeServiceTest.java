
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Note;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RefereeServiceTest extends AbstractTest {

	@Autowired
	private RefereeService	refereeService;


	@SuppressWarnings("deprecation")
	@Test
	public void testSaveFixUpTask() {
		super.authenticate("referee1");

		//		Collection<Complaint> complaints = null;
		//		complaints = this.refereeService.listComplaintByReferee();
		//		System.out.println(complaints);

		final Note note = this.refereeService.writeNoteOfReportByRefereeCustomerHandy(17609);
		note.setRefereeComment("hola que pasa");
		System.out.println(this.refereeService.saveNoteOfReportByRefereeCustomerHandy(note));

	}
}
