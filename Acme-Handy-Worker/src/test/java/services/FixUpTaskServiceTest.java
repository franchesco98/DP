
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.FixUpTask;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FixUpTaskServiceTest extends AbstractTest {

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	@SuppressWarnings("deprecation")
	@Test
	public void testSaveFixUpTask() {
		final Date d = new Date(2018, 11, 01);
		final Date moment = new Date(2018, 11, 02);
		final Date d2 = new Date(2018, 12, 02);
		final FixUpTask fixUpTask;
		FixUpTask saved;
		Collection<FixUpTask> fixUpTasks;

		fixUpTask = this.fixUpTaskService.create();
		fixUpTask.setAddress("Calle Zarzavieja n2");
		fixUpTask.setTicker("23273399-9dsa");
		fixUpTask.setStartTime(d);
		fixUpTask.setMoment(moment);
		fixUpTask.setEndTime(d2);
		fixUpTask.setDescription("Tpm");

		saved = this.fixUpTaskService.save(fixUpTask);
		fixUpTasks = this.fixUpTaskService.findAll();
		Assert.isTrue(fixUpTasks.contains(saved));
	}
}
