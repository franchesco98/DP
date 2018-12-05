
package usecases;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ApplicationService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import utilities.AbstractTest;
import domain.Application;
import domain.FixUpTask;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationTest extends AbstractTest {

	@Autowired
	private FixUpTaskService	fixUpTaskService;
	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private HandyWorkerService	handyWorkerService;


	@Test
	public void testFixUpTaskInTwoApplications() {
		super.authenticate("handy1");
		final FixUpTask f = (FixUpTask) this.fixUpTaskService.findAll().toArray()[0];
		final Collection<HandyWorker> handyWorkers = this.handyWorkerService.findAll();
		final HandyWorker h1 = this.handyWorkerService.findOne(17562);
		final HandyWorker h2 = (HandyWorker) handyWorkers.toArray()[1];
		Application saved1, saved2;

		final Application a1 = this.applicationService.create();
		final Application a2 = this.applicationService.create();

		a1.setComment("Comentario de ejemplo");
		a1.setOfferedPrice(23.);

		a2.setComment("Comentario de ejemplo 2");
		a2.setOfferedPrice(25.);

		saved1 = this.handyWorkerService.createApplication(h1, a1, f);
		super.authenticate("handy2");
		saved2 = this.handyWorkerService.createApplication(h2, a2, f);

		Assert.notNull(saved1);
		Assert.notNull(saved2);

		Assert.isTrue(saved1.getHandyWorker().getId() == h1.getId());
		Assert.isTrue(saved2.getHandyWorker().getId() == h2.getId());
		Assert.isTrue(saved1.getFixUpTask().getTicker().equals(saved2.getFixUpTask().getTicker()));
	}
}
