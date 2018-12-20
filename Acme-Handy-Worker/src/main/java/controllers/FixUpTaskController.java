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

import services.CustomerService;
import services.FixUpTaskService;
import domain.FixUpTask;

@Controller
@RequestMapping("/fixUpTask")
public class FixUpTaskController extends AbstractController {

	//Managed services
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Constructors -----------------------------------------------------------

	public FixUpTaskController() {
		super();
	}

	//list-------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;

		fixUpTasks = this.customerService.listingFixUpTasksCreatedByCustomerPrincipal();
		System.out.println("hola");
		result = new ModelAndView("fixUpTask/fixUpTasksList");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("requestURI", "fixUpTask/list.do");

		return result;
	}
}
