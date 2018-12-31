
package controllers;

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

import services.FixUpTaskService;
import services.PhaseService;
import domain.FixUpTask;
import domain.Phase;

@Controller
@RequestMapping("/phase/handyworker")
public class PhaseController extends AbstractController {

	//Managed services
	@Autowired
	private PhaseService		phaseService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	//Constructors
	public PhaseController() {
		super();
	}

	//Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Phase> phases;

		phases = this.phaseService.findPhasesOfHW();

		result = new ModelAndView("phase/list");
		result.addObject("phases", phases);
		result.addObject("requestURI", "phase/handyworker/list.do");
		return result;

	}

	//Creation
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixUpTaskId) {
		final ModelAndView result;
		Phase phase;
		FixUpTask fixUpTask;

		fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
		phase = this.phaseService.create();
		phase.setFixUpTask(fixUpTask);

		result = this.createEditModelAndView(phase);
		result.addObject("toShow", false);
		return result;

	}

	// Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int phaseId) {
		ModelAndView result;
		Phase phase;

		phase = this.phaseService.findOne(phaseId);
		Assert.notNull(phase);
		result = this.createEditModelAndView(phase);
		result.addObject("toShow", true);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Phase phase, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(phase);
		else
			try {
				this.phaseService.save(phase);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(phase, "phase.commit.error");
			}

		return result;
	}

	//Delete
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Phase phase, final BindingResult binding) {
		ModelAndView result;

		try {
			this.phaseService.delete(phase);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(phase, "phase.commit.error");
		}

		return result;
	}

	// Ancillary methods 

	protected ModelAndView createEditModelAndView(final Phase phase) {
		ModelAndView result;

		result = this.createEditModelAndView(phase, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Phase phase, final String message) {
		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;

		fixUpTasks = this.fixUpTaskService.findAll();

		result = new ModelAndView("phase/edit");
		result.addObject("phase", phase);
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("message", message);

		return result;
	}

}
