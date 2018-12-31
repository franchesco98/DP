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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CategoryService;
import services.ConfigurationService;
import services.CreditCardService;
import services.CustomerService;
import services.FixUpTaskService;
import services.WarrantyService;
import domain.Application;
import domain.Configuration;
import domain.CreditCard;
import domain.FixUpTask;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController {

	//Managed services
	@Autowired
	private ApplicationService		applicationService;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private WarrantyService			warrantyService;
	@Autowired
	private CategoryService			categoryService;
	@Autowired
	private FixUpTaskService		fixUpTaskService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private CreditCardService		creditCardService;


	// Constructors -----------------------------------------------------------

	public ApplicationController() {
		super();
	}

	//list-------------------------------------------------------------------

	@RequestMapping(value = "/customer/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int fixUpTaskId) {

		final ModelAndView result;
		Collection<Application> applications;
		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);

		applications = this.customerService.findApplicationsByFixUpTaskId(fixUpTask);

		if (applications == null)
			applications = new ArrayList<>();

		result = new ModelAndView("application/ApplicationList");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/customer/list.do");

		// para el color gris

		final boolean greyColor = fixUpTask.getEndTime().before(new Date());
		result.addObject("greyColor", greyColor);

		//para el IVA
		final Configuration configuration = (Configuration) this.configurationService.findAll().toArray()[0];
		final Double vat = configuration.getVAT();
		result.addObject("vat", vat);
		return result;
	}

	// Save ---------------------------------------------------------------
	@RequestMapping(value = "/customer/edit", method = RequestMethod.GET)
	public ModelAndView status(@RequestParam final int applicationId, @RequestParam final int accept) {
		ModelAndView result = null;

		final Application application = this.applicationService.findOne(applicationId);

		if (accept == 0 || accept == 1)
			if (accept == 0)
				try {

					this.customerService.rejecteCustomerApplication(application);
					result = new ModelAndView("redirect:list.do?fixUpTaskId=" + application.getFixUpTask().getId());
				} catch (final Throwable oops) {
					//TODO hacer que salte un panic
					result = new ModelAndView("redirect: /welcome/index");

				}
			else
				result = new ModelAndView("redirect:pay.do?applicationId=" + applicationId);

		return result;
	}

	//Pay---------------------------------------------------------------------------------
	@RequestMapping(value = "/customer/pay", method = RequestMethod.GET)
	public ModelAndView pay(@RequestParam final int applicationId) {

		final ModelAndView result;
		Collection<CreditCard> creditCards;
		final Application application = this.applicationService.findOne(applicationId);

		creditCards = this.creditCardService.getCreditCardsByPrincipal();

		if (creditCards == null)
			creditCards = new ArrayList<>();

		result = new ModelAndView("creditCard/payingApplication");
		result.addObject("creditCards", creditCards);
		result.addObject("application", application);

		return result;
	}

	//Save--------------------------------------------------------------------------------------
	@RequestMapping(value = "/customer/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;

		System.out.println(binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {

				this.customerService.acceptCustomerApplication(application);
				result = new ModelAndView("redirect:list.do?fixUpTaskId=" + application.getFixUpTask().getId());
			} catch (final Throwable oops) {

				result = this.createEditModelAndView(application, "application.commit.error");
			}

		return result;
	}

	//HANDYWORKER CREATE
	//Listing
	@RequestMapping(value = "/handyworker/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Application> applications;

		applications = this.applicationService.findApplicationsOfHW();

		result = new ModelAndView("application/ApplicationList");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/handyworker/list.do");
		return result;

	}
	@RequestMapping(value = "/handyworker/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixUpTaskId) {
		final ModelAndView result;
		Application application;
		FixUpTask fixUpTask;

		fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
		application = this.applicationService.create();
		application.setFixUpTask(fixUpTask);

		result = this.createEditModelAndViewHW(application);
		return result;

	}

	// Edition

	@RequestMapping(value = "/handyworker/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);
		result = this.createEditModelAndViewHW(application);
		result.addObject("toShow", true);

		return result;
	}

	@RequestMapping(value = "/handyworker/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveHandy(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {
				this.applicationService.save(application);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewHW(application, "application.commit.error");
			}

		return result;
	}

	//Delete
	@RequestMapping(value = "/handyworker/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Application application, final BindingResult binding) {
		ModelAndView result;

		try {
			this.applicationService.delete(application);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndViewHW(application, "application.commit.error");
		}

		return result;
	}

	//Ancillary customer method
	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("redirect:list.do?fixUpTaskId=" + application.getFixUpTask().getId());
		result.addObject("message", messageCode);
		return result;
	}
	protected ModelAndView createEditModelAndViewHW(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndViewHW(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewHW(final Application application, final String message) {
		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;

		fixUpTasks = this.fixUpTaskService.findAll();

		result = new ModelAndView("application/edit");
		result.addObject("application", application);
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("message", message);

		return result;
	}

}
