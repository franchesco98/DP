
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FinderServiceTest extends AbstractTest {

	@Autowired
	private FinderService	finderService;


	//	@SuppressWarnings("deprecation")
	//	@Test
	//	public void testSaveFinder() {
	//		Finder finder;
	//		Finder saved;
	//		Collection<Finder> finders;
	//
	//		final Date dMax = new Date(2019 - 1900, 2, 1);
	//		final Date dMin = new Date(2018 - 1900, 2, 2);
	//		final Date lastUpdate = new Date(2017 - 1900, 3, 1);
	//
	//		finder = this.finderService.create();
	//		finder.setCategory("Categoría1");
	//		finder.setDateMax(dMax);
	//		finder.setDateMin(dMin);
	//		finder.setKeyWord("keyWord");
	//		finder.setPriceMax(25.);
	//		finder.setPriceMin(20.);
	//		finder.setWarranty("Guarrati");
	//		finder.setLastUpdate(lastUpdate);
	//
	//		saved = this.finderService.save(finder);
	//		finders = this.finderService.findAll();
	//		Assert.isTrue(finders.contains(saved));
	//	}

	@Test
	public void testPrueba() {
		super.authenticate("handy2");
		final UserAccount user = LoginService.getPrincipal();
		final Authority handyWorkerAuthority = new Authority();
		handyWorkerAuthority.setAuthority(Authority.HANDYWORKER);
		System.out.println(user.getAuthorities() + " " + handyWorkerAuthority.getAuthority());
		Assert.isTrue(user.getAuthorities().contains(handyWorkerAuthority));
	}
}
