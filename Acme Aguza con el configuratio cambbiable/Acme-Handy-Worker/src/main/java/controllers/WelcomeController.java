/*
 * WelcomeController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import domain.Configuration;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(final Locale locale) {
		ModelAndView result;
		String welcomeMessage = "";

		final Configuration configuration = (Configuration) this.configurationService.findAll().toArray()[0];
		if ("es".equals(locale.getLanguage()))

			welcomeMessage = configuration.getWelcomeMessageS();
		else if ("en".equals(locale.getLanguage()))
			welcomeMessage = configuration.getWelcomeMessageS();

		result = new ModelAndView("welcome/index");
		result.addObject("welcomeMessage", welcomeMessage);

		return result;
	}
}
