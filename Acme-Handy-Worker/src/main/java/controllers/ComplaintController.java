/*
 * BoxController.java
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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.CustomerService;
import services.FixUpTaskService;
import services.RefereeService;
import domain.Complaint;
import domain.FixUpTask;
import domain.Report;

@Controller
@RequestMapping("/complaint")
public class ComplaintController extends AbstractController {

	//Managed services
	@Autowired
	private RefereeService		refereeService;

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;
	@Autowired
	private CustomerService		customerService;


	// Constructors -----------------------------------------------------------

	public ComplaintController() {
		super();
	}

	//referee
	// List ---------------------------------------------------------------		

	@RequestMapping(value = "referee/list", method = RequestMethod.GET)
	public ModelAndView listReferee(@RequestParam final int my) {
		ModelAndView result;
		result = new ModelAndView("complaint/complaintList");
		Collection<Complaint> complaints = null;
		if (my == 0) {
			complaints = this.refereeService.listComplaintwithoutReferee();
			result.addObject("requestURI", "complaint/referee/list.do?my=0");
		} else if (my == 1) {
			complaints = this.refereeService.listComplaintByReferee();
			result.addObject("requestURI", "complaint/referee/list.do?my=1");
		}

		result.addObject("complaints", complaints);

		return result;
	}

	//assing--------------------------------------------------------------------------------------
	@RequestMapping(value = "referee/assing", method = RequestMethod.GET)
	public ModelAndView assing(@RequestParam final int complaintId) {
		ModelAndView result;

		final Complaint complaint = this.complaintService.findOne(complaintId);
		this.refereeService.assingComplaintByReferee(complaint);

		final Collection<Complaint> complaints = this.refereeService.listComplaintByReferee();
		result = new ModelAndView("complaint/complaintList");

		result.addObject("complaints", complaints);
		result.addObject("requestURI", "complaint/referee/list.do?my=1");

		return result;
	}

	//customer
	// List ---------------------------------------------------------------		

	@RequestMapping(value = "customer/list", method = RequestMethod.GET)
	public ModelAndView listCustomer(@RequestParam final int fixUpTaskId) {

		ModelAndView result;
		result = new ModelAndView("complaint/complaintList");
		Collection<Complaint> complaints = null;

		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);

		complaints = this.fixUpTaskService.getRelatedComplaint(fixUpTask);

		result.addObject("complaints", complaints);
		result.addObject("requestURI", "complaint/customer/list.do?fixUpTaskId=" + fixUpTaskId);

		return result;
	}

	// Create ---------------------------------------------------------------		

	@RequestMapping(value = "customer/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixUpTaskId) {
		final ModelAndView result;
		Complaint complaint;

		complaint = this.customerService.createComplaint(fixUpTaskId);
		result = this.createEditModelAndView(complaint);

		result.addObject("toShow", false);

		return result;
	}

	@RequestMapping(value = "customer,referee/show", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int complaintId) {
		final ModelAndView result;
		Complaint complaint;

		complaint = this.complaintService.findOne(complaintId);
		Assert.notNull(complaint);

		result = this.createEditModelAndView(complaint);

		result.addObject("toShow", true);

		final Collection<String> listAttachments = new ArrayList<>();
		if (complaint.getAttachments() != null)
			for (final String attachment : complaint.getAttachments().split(";"))
				listAttachments.add(attachment.trim());

		result.addObject("listAttachments", listAttachments);

		final Report report = this.complaintService.getReportByComplaint(complaintId);

		result.addObject("hasReport", report != null);
		boolean finalMode = false;
		if (report != null)
			finalMode = report.getIsFinal();

		result.addObject("finalMode", finalMode);
		return result;
	}
	// Save ---------------------------------------------------------------		
	@RequestMapping(value = "customer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Complaint complaintToSave, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(complaintToSave);
		else
			try {
				this.complaintService.save(complaintToSave);
				result = new ModelAndView("redirect:list.do?fixUpTaskId=" + complaintToSave.getFixUpTask().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(complaintToSave, "complaint.commit.error");
			}

		return result;
	}

	// Ancillary Methods -----------------------------------------------------
	protected ModelAndView createEditModelAndView(final Complaint complaint) {
		ModelAndView result;

		result = this.createEditModelAndView(complaint, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Complaint complaint, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("complaint/complaintShow");
		if (complaint.getId() == 0)
			result = new ModelAndView("complaint/complaintCreate");

		result.addObject("complaint", complaint);
		result.addObject("message", messageCode);

		return result;
	}
}
