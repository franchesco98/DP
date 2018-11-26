
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

import domain.Finder;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FinderServiceTest {

	@Autowired
	private FinderService	finderService;


	@SuppressWarnings("deprecation")
	@Test
	public void testSaveFinder() {
		Finder finder;
		Finder saved;
		Collection<Finder> finders;

		final Date dMax = new Date(2019, 02, 01);
		final Date dMin = new Date(2018, 02, 02);
		final Date lastUpdate = new Date(2017, 3, 1);

		finder = this.finderService.create();
		finder.setCategory("Categoría1");
		finder.setDateMax(dMax);
		finder.setDateMin(dMin);
		finder.setKeyWord("keyWord");
		finder.setPriceMax(25.);
		finder.setPriceMin(20.);
		finder.setWarranty("Guarrati");
		finder.setLastUpdate(lastUpdate);

		saved = this.finderService.save(finder);
		finders = this.finderService.findAll();
		Assert.isTrue(finders.contains(saved));
	}
}
