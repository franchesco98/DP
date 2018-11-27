
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

import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Box;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActorServiceTest {

	@Autowired
	private ActorService		actorService;
	@Autowired
	private UserAccountService	userAccountService;
	@Autowired
	private BoxService			boxService;


	@Test
	public void testSaveActorCustomer() {
		Actor actor;
		Actor saved;
		Collection<Actor> actors;

		//		<final property name="username" value="customer1" />
		//		<property name="password" value="1b3231655cebb7a1f783eddf27d254ca" />
		//		<property name="authorities">
		//			<list>
		//				<bean class="security.Authority">
		//					<property name="authority" value="CUSTOMER" />
		//				</bean>
		//			</list>
		//		</property>

		final Authority authority;
		authority = new Authority();
		final Collection<Authority> authorities = new ArrayList<>();
		final Collection<Box> customerBoxes = new ArrayList<>();
		final Collection<Box> savedCustomerBoxes = new ArrayList<>();

		authority.setAuthority("CUSTOMER");

		authorities.add(authority);

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

		UserAccount userAccount, savedUserAccount;
		userAccount = this.userAccountService.create();
		userAccount.setUsername("manperalv");
		userAccount.setPassword("1b3231655cebb7a1f783eddf27d254ca");
		userAccount.setAuthorities(authorities);

		savedUserAccount = this.userAccountService.save(userAccount);

		actor = this.actorService.create();
		actor.setName("Manuel");
		actor.setSurname("Pérez Álvarez");
		actor.setPhoto("http://tumblr.com");
		actor.setEmail("manperalv@gmail.dp");
		actor.setPhoneNumber("+34 (689) 326892");
		actor.setAddress("Calle Runge-Kutta N2");
		actor.setNumSocialProfile(1);
		actor.setUserAccount(savedUserAccount);
		actor.setBoxes(savedCustomerBoxes);

		saved = this.actorService.save(actor);
		actors = this.actorService.findAll();
		Assert.isTrue(actors.contains(saved));
	}
}
