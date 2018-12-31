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

import services.ActorService;
import services.BoxService;
import services.MessageService;
import domain.Actor;
import domain.Box;
import domain.Message;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	//Managed services
	@Autowired
	private MessageService	messageService;

	@Autowired
	private BoxService		boxService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public MessageController() {
		super();
	}

	// List ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int boxId) {
		ModelAndView result;
		Collection<Message> messages = null;

		final Box box = this.boxService.findOne(boxId);

		//Assert para comprobar que un actor no acceda a messages de otros actores
		Assert.isTrue(this.actorService.findActorByPrincipal().getBoxes().contains(box));

		messages = box.getMessages();

		result = new ModelAndView("message/messageList");

		result.addObject("messagesToList", messages);
		result.addObject("box", box);

		result.addObject("requestURI", "message/list.do");

		return result;
	}

	// Create ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Message message;

		message = this.messageService.create();
		result = this.createEditModelAndView(message);

		result.addObject("toShow", false);

		return result;
	}

	// Edit: En este caso el metodo edit solo se usaria para VER mensajes, ya que no se pueden editar ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int boxId, @RequestParam final int messageId) {
		final ModelAndView result;
		Box box;
		Message messageToShow;

		box = this.boxService.findOne(boxId);
		Assert.notNull(box);
		messageToShow = this.messageService.findOne(messageId);
		Assert.notNull(messageToShow);

		//Assert para comprobar que un actor no acceda a messages de otros actores
		Assert.isTrue(this.actorService.findActorByPrincipal().getBoxes().contains(box));
		Assert.isTrue(box.getMessages().contains(messageToShow));

		result = this.createEditModelAndView(messageToShow);

		final Collection<String> priority = new ArrayList<>();
		priority.add(messageToShow.getPriority());
		result.addObject("box", box);
		result.addObject("toShow", true);
		result.addObject("actors", messageToShow.getRecipients());
		result.addObject("priorities", priority);

		return result;
	}

	// Save ---------------------------------------------------------------		
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Message messageToSave, final BindingResult binding) {
		ModelAndView result;

		System.out.println(binding.getAllErrors());

		if (binding.hasErrors())
			result = this.createEditModelAndView(messageToSave);
		else
			try {
				//Para implementar la funcionalidad para enviar un mensaje a todos los actores, se usa temporalmente el atributo flagSpam para ver si el sender lo ha seleccionado o no, despues le asignamos el valor por defecto a flagSpam, y ya en el servicio de message, al guardarlo, se le dara su valor apropiado
				if (messageToSave.getFlagSpam()) {
					final Collection<Actor> allActors = this.actorService.findAll();
					allActors.remove(messageToSave.getSender());
					messageToSave.setRecipients(allActors);
					messageToSave.setFlagSpam(false);
				}
				final Box outBox = this.messageService.save(messageToSave);

				//Nos llevara a la outbox del sender
				final String redirect = "redirect:list.do?boxId=" + outBox.getId();
				result = new ModelAndView(redirect);
			} catch (final Throwable oops) {

				System.out.println(oops.getMessage());
				System.out.println(oops.getStackTrace());

				result = this.createEditModelAndView(messageToSave, "message.commit.error");
			}

		return result;
	}

	// Delete ---------------------------------------------------------------		
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageId, @RequestParam final int boxId) {
		ModelAndView result;
		final Message message = this.messageService.findOne(messageId);
		final Box box = this.boxService.findOne(boxId);
		try {
			final Box trashBox = this.actorService.deleteMessage(message, box);

			//Nos llevara a la trash box, si se elimino el mensjae de la trashbos simplemente se mostrara esta vacia
			final String redirect = "redirect:list.do?boxId=" + trashBox.getId();
			result = new ModelAndView(redirect);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(message, "message.delete.error.exception");

			final Collection<String> priority = new ArrayList<>();
			priority.add(message.getPriority());
			result.addObject("box", box);
			result.addObject("toShow", true);
			result.addObject("actors", message.getRecipients());
			result.addObject("priorities", priority);
		}

		return result;
	}

	// Move ---------------------------------------------------------------		

	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView move(@RequestParam final int boxId, @RequestParam final int messageId) {
		final ModelAndView result;
		Box box;
		Message messageToMove;

		box = this.boxService.findOne(boxId);
		Assert.notNull(box);
		messageToMove = this.messageService.findOne(messageId);
		Assert.notNull(messageToMove);

		//Assert para comprobar que un actor no acceda a messages de otros actores
		final Actor loggedActor = this.actorService.findActorByPrincipal();
		Assert.isTrue(loggedActor.getBoxes().contains(box));
		Assert.isTrue(box.getMessages().contains(messageToMove));

		result = new ModelAndView("message/messageMove");

		//Creamos la collection con las posibles boxes de destino(todas las del actor menos la box de origen)
		final Collection<Box> actorBoxes = loggedActor.getBoxes();
		actorBoxes.remove(box);

		final String a = "";
		result.addObject("String", a);
		result.addObject("originBox", box);
		result.addObject("messageToMove", messageToMove);
		result.addObject("destinationBoxes", actorBoxes);

		return result;
	}

	// Move save ---------------------------------------------------------------	
	@RequestMapping(value = "/move", method = RequestMethod.POST, params = "save")
	public ModelAndView move(@Valid final String destinationBox, final BindingResult binding) {
		ModelAndView result;

		System.out.println(binding.getAllErrors());

		if (binding.hasErrors()) {
			final String redirect = "redirect:create.do";
			result = new ModelAndView(redirect);
		} else
			try {
				System.out.println(destinationBox);
				final String redirect = "redirect:create.do";
				result = new ModelAndView(redirect);
			} catch (final Throwable oops) {

				System.out.println(oops.getMessage());
				System.out.println(oops.getStackTrace());

				final String redirect = "redirect:create.do";
				result = new ModelAndView(redirect);
			}

		return result;
	}
	// Ancillary Methods -----------------------------------------------------
	protected ModelAndView createEditModelAndView(final Message messageObject) {
		ModelAndView result;

		result = this.createEditModelAndView(messageObject, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message messageToCreate, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("message/create");

		//Posibles recipients del message (todos los actores menos el que lo envia)
		final Collection<Actor> actors = this.actorService.findAll();
		final Actor logedActor = this.actorService.findActorByPrincipal();
		actors.remove(logedActor);

		//Posibles priorities
		final Collection<String> priorities = new ArrayList<>();
		priorities.add("HIGH");
		priorities.add("NEUTRAL");
		priorities.add("LOW");

		result.addObject("priorities", priorities);
		result.addObject("actors", actors);
		result.addObject("messageToCreate", messageToCreate);
		result.addObject("message", messageCode);

		return result;
	}
}
