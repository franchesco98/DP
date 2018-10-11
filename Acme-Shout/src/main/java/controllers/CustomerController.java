/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ShoutService;
import domain.Shout;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	@Autowired
	private ShoutService	shoutService;


	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/action-1", method = RequestMethod.GET)
	public ModelAndView action1() {

		ModelAndView result;

		Collection<Shout> shouts;
		shouts = this.shoutService.findAll();

		result = new ModelAndView("customer/action-1");
		result.addObject("shouts", shouts);
		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("customer/action-2");

		return result;
	}
}
