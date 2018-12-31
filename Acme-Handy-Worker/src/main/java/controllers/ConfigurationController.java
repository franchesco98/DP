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

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ConfigurationService;
import domain.Configuration;

@Controller
@RequestMapping("/configuration/administrator")
public class ConfigurationController extends AbstractController {

	//Managed services
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------

	public ConfigurationController() {
		super();
	}

	//list-------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int edit) {

		ModelAndView result;
		Configuration configuration;

		configuration = (Configuration) this.configurationService.findAll().toArray()[0];

		result = new ModelAndView("admin/configurationEdit");
		result.addObject("config", configuration);

		boolean toShow;
		if (edit == 0)
			toShow = true;
		else
			toShow = false;
		result.addObject("toShow", toShow);

		final List<String> listPositiveWordsE = this.administratorService.listPosivieWordE();
		result.addObject("listPositiveWordsE", listPositiveWordsE);
		final List<String> listNegativeWordsE = this.administratorService.listNegativeWordsE();
		result.addObject("listNegativeWordsE", listNegativeWordsE);
		final List<String> listPositiveWordsS = this.administratorService.listPosivieWordS();
		result.addObject("listPositiveWordsS", listPositiveWordsS);
		final List<String> listNegativeWordsS = this.administratorService.listNegativeWordsS();
		result.addObject("listNegativeWordsS", listNegativeWordsS);

		result.addObject("requestURI", "configuration/administrator/list.do");

		return result;
	}

	// Save ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Configuration configuration, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(configuration);
		else
			try {

				this.configurationService.save(configuration);

				result = new ModelAndView("redirect:list.do?edit=0");

			} catch (final Throwable oops) {

				result = this.createEditModelAndView(configuration, "configuration.commit.error");
			}

		return result;
	}

	// Ancillary methods----------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Configuration configuration) {
		ModelAndView result;

		result = this.createEditModelAndView(configuration, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Configuration configuration, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("configuration/administrator/list.do?edit=1");
		result.addObject("configuration", configuration);

		result.addObject("toShow", false);

		return result;
	}

}
