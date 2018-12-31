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

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import domain.Configuration;
import domain.CreditCard;

@Controller
@RequestMapping("/creditCard")
public class CreditCardController extends AbstractController {

	//Managed services
	@Autowired
	private CreditCardService	creditCardService;


	// Constructors -----------------------------------------------------------

	public CreditCardController() {
		super();
	}

	// List ---------------------------------------------------------------		

	@RequestMapping(value = "/customer,sponsor/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<CreditCard> creditCard = null;

		creditCard = this.creditCardService.getCreditCardsByPrincipal();

		result = new ModelAndView("creditCard/creditCardList");

		result.addObject("creditCard", creditCard);
		result.addObject("requestURI", "creditCard/list.do");

		return result;
	}

	// Create ---------------------------------------------------------------		

	@RequestMapping(value = "/customer,sponsor/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		CreditCard creditCard;

		creditCard = this.creditCardService.create();
		result = this.createEditModelAndView(creditCard);
		return result;
	}

	// Edit ---------------------------------------------------------------		

	@RequestMapping(value = "/customer,sponsor/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int creditCardId) {
		final ModelAndView result;
		final CreditCard creditCard;

		creditCard = this.creditCardService.findOne(creditCardId);

		result = this.createEditModelAndView(creditCard);
		return result;
	}

	// Save ---------------------------------------------------------------		
	@RequestMapping(value = "/customer,sponsor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CreditCard creditCard, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding);
			result = this.createEditModelAndView(creditCard);
		} else
			try {
				this.creditCardService.save(creditCard);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(creditCard, "creditCard.commit.error");
			}

		return result;
	}
	// Ancillary Methods -----------------------------------------------------
	protected ModelAndView createEditModelAndView(final CreditCard creditCard) {
		ModelAndView result;

		result = this.createEditModelAndView(creditCard, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreditCard creditCard, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("creditCard/creditCardEdit");

		result.addObject("creditCard", creditCard);
		result.addObject("message", messageCode);

		final Configuration configuration = (Configuration) this.configurationService.findAll().toArray()[0];
		final Collection<String> brandNames = this.configurationService.splitComa(configuration.getCreditCardMakes());

		result.addObject("brandNames", brandNames);

		return result;
	}
}
