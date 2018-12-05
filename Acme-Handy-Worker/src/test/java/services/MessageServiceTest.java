
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import domain.Message;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private MessageService			messageService;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private BoxService				boxService;
	@Autowired
	private AdministratorService	administratorService;


	@Test
	public void testDeleteMessage() {
		super.authenticate("admin1");

		final Actor principal = this.administratorService.findByPrincipal();
		List<Message> customerMessages = new ArrayList<>();

		for (final Box b : principal.getBoxes())
			customerMessages.addAll(b.getMessages());

		final Message d = customerMessages.get(0);
		final Collection<Box> messageBoxes = this.boxService.searchBoxesByMessage(principal, d);

		final Box b2 = (Box) messageBoxes.toArray()[0];
		this.actorService.deleteMessage(d, b2, principal);

		customerMessages = new ArrayList<>();

		Assert.isTrue(!(b2.getMessages().contains(d)));
	}
}
