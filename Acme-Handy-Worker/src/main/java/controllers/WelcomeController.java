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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.FixUpTaskService;
import domain.Configuration;
import domain.FixUpTask;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	//Services
	@Autowired
	ConfigurationService	configurationService;
	@Autowired
	FixUpTaskService		fixUpTaskService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "John Doe") final String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);

		final Configuration configuration = (Configuration) this.configurationService.findAll().toArray()[0];

		result.addObject("nameSystem", configuration.getNameSystem());
		result.addObject("confi", configuration);

		final List<FixUpTask> fixUpTasks = new ArrayList<>(this.fixUpTaskService.findAll());
		result.addObject("fixUpTasks", fixUpTasks);
		return result;

	}
}
