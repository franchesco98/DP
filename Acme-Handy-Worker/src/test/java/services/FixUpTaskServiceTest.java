
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.ConfigurationRepository;
import utilities.AbstractTest;
import domain.Application;
import domain.FixUpTask;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FixUpTaskServiceTest extends AbstractTest {

	@Autowired
	private FixUpTaskService		fixUpTaskService;
	@Autowired
	private CategoryService			categoryService;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private WarrantyService			warrantyService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private ConfigurationRepository	configurationRepository;


	@SuppressWarnings("deprecation")
	@Test
	public void testSaveFixUpTask() {
		super.authenticate("customer2");

		//		final Date d = new Date(11, 1, 2018 - 1900);
		//
		//		final Date d2 = new Date(12, 1, 2018 - 1900);
		//		final FixUpTask fixUpTask;
		//		FixUpTask saved;
		//		Collection<FixUpTask> fixUpTasks;
		//
		//		final Category category = this.categoryService.findOne(17537);
		//		final Warranty warranty = this.warrantyService.findOne(17546);
		//
		//		fixUpTask = this.fixUpTaskService.create();
		//		fixUpTask.setAddress("Calle Zarzavieja n2");
		//		fixUpTask.setStartTime(d);
		//		fixUpTask.setEndTime(d2);
		//		fixUpTask.setDescription("Tpm");
		//		fixUpTask.setCategory(category);
		//		fixUpTask.setMaxPrice(23.0);
		//		fixUpTask.setWarranty(warranty);
		//
		//		saved = this.customerService.saveFixUpTaskCustomer(fixUpTask);
		//		fixUpTasks = this.fixUpTaskService.findAll();
		//		Assert.isTrue(fixUpTasks.contains(saved));

		final FixUpTask fixUpTask1 = this.fixUpTaskService.findOne(622592);
		final Collection<Application> aplications = this.customerService.findApplicationsByFixUpTaskId(fixUpTask1);
		System.out.println(aplications);

	}
}
