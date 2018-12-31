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
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BoxService;
import domain.Box;

@Controller
@RequestMapping("/box")
public class BoxController extends AbstractController {

	//Managed services
	@Autowired
	private BoxService		boxService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public BoxController() {
		super();
	}

	// List ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Box> boxes = null;

		boxes = this.actorService.findActorByPrincipal().getBoxes();

		result = new ModelAndView("box/boxList");

		result.addObject("boxes", boxes);
		result.addObject("requestURI", "box/list.do");

		return result;
	}

	// Create ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Box box;

		box = this.boxService.create();
		result = this.createEditModelAndView(box);
		return result;
	}

	// Edit ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int boxId) {
		final ModelAndView result;
		Box box;

		box = this.boxService.findOne(boxId);
		Assert.notNull(box);

		//Assert para comprobar que un actor no accede a edit box de otros actores
		Assert.isTrue(this.actorService.findActorByPrincipal().getBoxes().contains(box));

		result = this.createEditModelAndView(box);
		return result;
	}

	// Save ---------------------------------------------------------------		
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Box box, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(box);
		else
			try {
				if (box.getId() == 0)
					this.actorService.createNewBox(box);
				else
					this.actorService.editBoxName(box);

				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(box, "box.commit.error");
			}

		return result;
	}

	// Delete ---------------------------------------------------------------		
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Box box, final BindingResult binding) {
		ModelAndView result;

		try {
			this.actorService.deleteBox(box);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(box, "box.commit.error.exception");
		}

		return result;
	}

	// Ancillary Methods -----------------------------------------------------
	protected ModelAndView createEditModelAndView(final Box box) {
		ModelAndView result;

		result = this.createEditModelAndView(box, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Box box, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("box/boxEdit");

		result.addObject("box", box);
		result.addObject("message", messageCode);

		return result;
	}
}
