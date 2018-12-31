
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import domain.Finder;
import domain.FixUpTask;

@Controller
@RequestMapping("/finder/handyworker")
public class FinderController extends AbstractController {

	//Managed services

	@Autowired
	private FinderService	finderService;


	//Constructors
	public FinderController() {
		super();
	}

	//R11.2 Search Fix-Up Tasks by params

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView showForm() {
		final ModelAndView result;
		final Finder f = this.finderService.create();

		result = new ModelAndView("finder/searchForm");
		result.addObject("finder", f);
		return result;

	}
	@RequestMapping(value = "/searchPost")
	public ModelAndView searchFixUpTasks(@Valid @ModelAttribute("finder") final Finder f, final BindingResult binding) {
		final ModelAndView result;
		final Collection<FixUpTask> fixUpTasks;

		if (binding.hasErrors())
			result = new ModelAndView("finder/searchForm");
		else {
			result = new ModelAndView("fixUpTask/fixUpTasksList");
			fixUpTasks = this.finderService.findTasksByParams(f);
			result.addObject("fixUpTasks", fixUpTasks);
			result.addObject("requestURI", "finder/handyworker/searchPost.do");
		}

		return result;

	}
}
