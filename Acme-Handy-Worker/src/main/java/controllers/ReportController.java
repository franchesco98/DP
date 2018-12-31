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
import services.ReportService;
import domain.Report;

@Controller
@RequestMapping("/report")
public class ReportController extends AbstractController {

	//Managed services
	@Autowired
	private RefereeService		refereeService;

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private ReportService		reportService;


	// Constructors -----------------------------------------------------------

	public ReportController() {
		super();
	}

	//referee
	// Create ---------------------------------------------------------------		

	@RequestMapping(value = "referee/create", method = RequestMethod.GET)
	public ModelAndView listReferee(@RequestParam final int complaintId) {
		ModelAndView result;

		Report report;

		report = this.refereeService.writteReportOfRefereeComplaint(complaintId);

		result = this.createEditModelAndView(report, 1);

		result.addObject("toShow", false);

		return result;

	}

	//Edit---------------------------------------------------------------------------------------------------------
	@RequestMapping(value = "referee/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int reportId) {
		final ModelAndView result;
		Report report;

		report = this.reportService.findOne(reportId);
		Assert.notNull(report);

		result = this.createEditModelAndView(report, 1);

		result.addObject("toShow", false);

		result.addObject("hasReport", true);
		boolean finalMode = false;
		if (report != null)
			finalMode = report.getIsFinal();

		result.addObject("finalMode", finalMode);
		return result;
	}
	// Save ---------------------------------------------------------------		
	@RequestMapping(value = "referee/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Report reportToSave, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(reportToSave, 1);
		else
			try {
				this.reportService.save(reportToSave);
				result = new ModelAndView("redirect:show.do?complaintId=" + reportToSave.getComplaint().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(reportToSave, "report.commit.error", 1);
			}

		return result;
	}

	// Show ---------------------------------------------------------------		
	@RequestMapping(value = "referee/show", method = RequestMethod.GET)
	public ModelAndView show(@Valid final int complaintId) {
		ModelAndView result;

		Report report;

		report = this.complaintService.getReportByComplaint(complaintId);
		Assert.notNull(report);

		result = this.createEditModelAndView(report, 0);

		result.addObject("toShow", true);

		result.addObject("hasReport", true);
		boolean finalMode = false;
		if (report != null)
			finalMode = report.getIsFinal();

		result.addObject("finalMode", finalMode);
		return result;

	}
	//Delete-------------------------------------------------------------------------------------------
	@RequestMapping(value = "/referee/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Report report, final BindingResult binding) {
		ModelAndView result;

		try {
			this.reportService.delete(report);
			result = new ModelAndView("redirect:complaint/referee/list.do?my=1");
		} catch (final Throwable oops) {

			result = this.createEditModelAndView(report, "report.commit.error", 1);
		}

		return result;
	}

	// Ancillary Methods -----------------------------------------------------
	protected ModelAndView createEditModelAndView(final Report report, final int edit) {
		ModelAndView result;

		result = this.createEditModelAndView(report, null, edit);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Report report, final String messageCode, final int edit) {
		ModelAndView result;

		if (edit == 1) {
			result = new ModelAndView("report/reportEdit");
			if (report.getId() == 0)
				result = new ModelAndView("report/reportCreate");
		} else
			result = new ModelAndView("report/reportShow");

		result.addObject("report", report);
		result.addObject("message", messageCode);

		return result;
	}
}
