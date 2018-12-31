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
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.CustomerService;
import services.FixUpTaskService;
import services.WarrantyService;
import domain.Category;
import domain.Configuration;
import domain.FixUpTask;
import domain.Warranty;

@Controller
@RequestMapping("/fixUpTask")
public class FixUpTaskController extends AbstractController {

	//Managed services
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;
	@Autowired
	private WarrantyService		warrantyService;
	@Autowired
	private CategoryService		categoryService;


	// Constructors -----------------------------------------------------------

	public FixUpTaskController() {
		super();
	}

	//list-------------------------------------------------------------------

	@RequestMapping(value = "/customer/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;

		fixUpTasks = this.customerService.listingFixUpTasksCreatedByCustomerPrincipal();

		result = new ModelAndView("fixUpTask/fixUpTasksList");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("requestURI", "fixUpTask/customer/list.do");

		//para el IVA
		final Configuration configuration = (Configuration) this.configurationService.findAll().toArray()[0];
		final Double vat = configuration.getVAT();
		result.addObject("vat", vat);

		return result;
	}

	// Create ---------------------------------------------------------------

	@RequestMapping(value = "/customer/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		FixUpTask fixUpTask;

		fixUpTask = this.fixUpTaskService.create();

		result = this.createEditModelAndView(fixUpTask, 1);

		return result;
	}

	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/customer/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int fixUpTaskId, @RequestParam final int edit) {
		final ModelAndView result;
		FixUpTask fixUpTask;

		fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
		Assert.notNull(fixUpTask);

		result = this.createEditModelAndView(fixUpTask, edit);
		return result;
	}

	// Save ---------------------------------------------------------------
	@RequestMapping(value = "/customer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FixUpTask fixUpTask, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(fixUpTask, 1);
		else
			try {

				this.customerService.saveFixUpTaskCustomer(fixUpTask);

				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {

				result = this.createEditModelAndView(fixUpTask, "fixUpTask.commit.error", 1);
			}

		return result;
	}
	// Delete ---------------------------------------------------------------

	@RequestMapping(value = "/customer/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final FixUpTask fixUpTask, final BindingResult binding) {
		ModelAndView result;

		try {
			this.customerService.deleteFixUpTaskCustomer(fixUpTask);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {

			result = this.createEditModelAndView(fixUpTask, "fixUpTask.commit.error", 1);
		}

		return result;
	}

	//athicalate method
	protected ModelAndView createEditModelAndView(final FixUpTask fixUpTask, final int edit) {
		ModelAndView result;

		result = this.createEditModelAndView(fixUpTask, null, edit);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FixUpTask fixUpTask, final String messageCode, final int edit) {
		ModelAndView result;

		Boolean toShow = null;
		if (edit == 0)
			toShow = true;
		else
			toShow = false;

		result = new ModelAndView("fixUpTask/fixUpTaskView");
		result.addObject("fixUpTask", fixUpTask);

		result.addObject("toShow", toShow);

		//nos traemos la collection de waranties y cotegories
		final List<Warranty> warranties = this.warrantyService.findFinalWarranties();
		final Collection<Category> categories = this.categoryService.findAll();
		result.addObject("warranties", warranties);
		result.addObject("categories", categories);

		//para el IVA
		final Configuration configuration = (Configuration) this.configurationService.findAll().toArray()[0];
		final Double vat = configuration.getVAT();
		result.addObject("vat", vat);
		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/handyworker/listAll", method = RequestMethod.GET)
	public ModelAndView listAll() {

		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;

		fixUpTasks = this.fixUpTaskService.findAll();
		result = new ModelAndView("fixUpTask/fixUpTasksList");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("requestURI", "fixUpTask/handyworker/listAll.do");

		return result;
	}

}
