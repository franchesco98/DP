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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import repositories.ActorRepository;
import security.LoginService;
import services.ActorService;
import services.AdministratorService;
import services.BoxService;
import services.CustomerService;
import services.HandyWorkerService;
import services.RefereeService;
import services.SponsorService;
import domain.Actor;
import domain.Box;
import domain.Customer;

@Controller
@RequestMapping("/box")
public class BoxController extends AbstractController {

	//Managed services
	@Autowired
	private BoxService				boxService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private RefereeService			refereeService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private ActorService			actorService;

	@Autowired
	private ActorRepository			actorRepository;


	// Constructors -----------------------------------------------------------

	public BoxController() {
		super();
	}

	// List ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Box> boxes = null;

		System.out.println("he llegado aqui");
		System.out.println(LoginService.getPrincipal().getId());
		final Actor actor = this.actorRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		System.out.println("he llegado aqui 1");
		System.out.println(actor);

		//		final Authority Authority = (Authority) LoginService.getPrincipal().getAuthorities().toArray()[0];
		//
		//		//Comprueba si el que está logueado es ADMIN
		//		if (Authority.equals(security.Authority.listAuthorities().toArray()[0])) {
		//			final Administrator admin = this.administratorService.findByPrincipal();
		//			boxes = admin.getBoxes();
		//		}
		//
		//		//Comprueba si el que está logueado es CUSTOMER
		//		if (Authority.equals(security.Authority.listAuthorities().toArray()[1])) {
		//			final Customer customer = this.customerService.findCustomerByPrincipal();
		//			boxes = customer.getBoxes();
		//		}
		//
		//		//Comprueba si el que está logueado es REFEREE
		//		if (Authority.equals(security.Authority.listAuthorities().toArray()[2])) {
		//			final Referee referee = this.refereeService.findRefereeByPrincipal();
		//			boxes = referee.getBoxes();
		//		}
		//
		//		//Comprueba si el que está logueado es SPONSOR
		//		if (Authority.equals(security.Authority.listAuthorities().toArray()[3])) {
		//			final Sponsor sponsor = this.sponsorService.findSponsorByPrincipal();
		//			boxes = sponsor.getBoxes();
		//		}
		//
		//		//Comprueba si el que está logueado es HANDYWORKER
		//		if (Authority.equals(security.Authority.listAuthorities().toArray()[4])) {
		//			final HandyWorker handyWorker = this.handyWorkerService.findHandyWorkerByPrincipal();
		//			boxes = handyWorker.getBoxes();
		//		}

		//boxes = actor.getBoxes();

		result = new ModelAndView("box/boxList");

		result.addObject("boxes", boxes);
		result.addObject("requestURI", "box/list.do");

		return result;
	}

	// Create ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		return null;
	}

	protected ModelAndView createEditModelAndView(final Customer customer) {
		ModelAndView result;

		result = this.createEditModelAndView(customer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Customer customer, final String messageCode) {
		ModelAndView result;
		final String rol = "customer";
		final Boolean toShow = false;
		result = new ModelAndView("actor/edit");
		result.addObject("customer", customer);
		result.addObject("rol", rol);
		result.addObject("toShow", toShow);

		return result;
	}
}
