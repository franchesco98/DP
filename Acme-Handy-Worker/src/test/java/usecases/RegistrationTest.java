/*
 * RegistrationTest.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package usecases;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.FixUpTaskService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class RegistrationTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private FixUpTaskService	customerService;


	// Tests ------------------------------------------------------------------

	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				"customer1", "announcement6", null
			}, {
				null, "announcement6", IllegalArgumentException.class
			}, {
				"reviewer1", "announcement6", IllegalArgumentException.class
			}, {
				"customer1", "announcement2", IllegalArgumentException.class
			}
		};

	}

}
