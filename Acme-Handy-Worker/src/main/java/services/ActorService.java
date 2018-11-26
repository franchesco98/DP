/*
 * ActorService.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository	actorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private BoxService		boxService;

	@Autowired
	private MessageService	messageService;


	// Constructors -----------------------------------------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result;

		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public Actor save(final Actor actor) {

		Assert.notNull(actor);
		//comprobamos que no nos han dado cadenas vacias en los at opcionales
		Assert.isTrue(!(actor.getMiddleName().equals("")));
		Assert.isTrue(!(actor.getAddress().equals("")));
		Actor result;

		if (actor.getId() == 0) {

			actor.setBoxes(this.boxService.originalBoxes());
			actor.setIsBanned(false);
			actor.setIsSuspicious(false);
			actor.setNumSocialProfile(0);
			result = this.actorRepository.save(actor);

		} else {
			int idPrincipal;
			idPrincipal = LoginService.getPrincipal().getId();
			Assert.isTrue(idPrincipal == actor.getId());
			result = this.actorRepository.save(actor);
		}
		return result;
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(this.actorRepository.exists(actor.getId()));

		this.actorRepository.delete(actor);
	}

	// Other business methods -------------------------------------------------

	public void createNewBox(final Actor actor, final Box newBox) {
		Assert.notNull(actor);
		Assert.notNull(newBox);

		//An actor can only create boxes for himself
		int idPrincipal;
		idPrincipal = LoginService.getPrincipal().getId();
		Assert.isTrue(idPrincipal == actor.getId());

		//Checks that the name of the new box is not the same as a system box
		boolean isSystemBoxName = false;
		final String[] SystemBoxNames = {
			"in box", "out box", "spam box", "trash box"
		};
		for (final String SystemBoxName : SystemBoxNames)
			if (SystemBoxName.equals(newBox.getName())) {
				isSystemBoxName = true;
				break;
			}
		Assert.isTrue(!isSystemBoxName);
		newBox.setSystemBox(false);
		this.boxService.save(newBox);

		//adds the new box in the actor's boxes and updates him
		final Collection<Box> actorBoxes = actor.getBoxes();
		actorBoxes.add(newBox);
		actor.setBoxes(actorBoxes);
		this.save(actor);

	}

	public void deleteBox(final Actor actor, final Box boxToDelete) {
		Assert.notNull(actor);
		Assert.notNull(boxToDelete);

		//An actor can only delete boxes for himself
		int idPrincipal;
		idPrincipal = LoginService.getPrincipal().getId();
		Assert.isTrue(idPrincipal == actor.getId());

		//Checks that the actor is not deleting a system box
		Assert.isTrue(!boxToDelete.getSystemBox());

		this.boxService.delete(boxToDelete);

		//removes the box in the actor's boxes and updates him
		final Collection<Box> actorBoxes = actor.getBoxes();
		actorBoxes.remove(boxToDelete);
		actor.setBoxes(actorBoxes);
		this.save(actor);
	}

	public void editBoxName(final Actor actor, final Box boxToEdit) {
		Assert.notNull(actor);
		Assert.notNull(boxToEdit);

		//An actor can only edit his boxes
		int idPrincipal;
		idPrincipal = LoginService.getPrincipal().getId();
		Assert.isTrue(idPrincipal == actor.getId());

		//Checks that the actor is not editing a system box
		Assert.isTrue(!boxToEdit.getSystemBox());

		//Checks that the actor is not editing a box of another actor
		boolean isHisBox = false;
		for (final Box actorBox : actor.getBoxes())
			if (actorBox.getId() == boxToEdit.getId())
				isHisBox = true;
		Assert.isTrue(isHisBox);

		//Checks that the name of the box is not the same as the name of the system boxes
		boolean isSystemBoxName = false;
		final String[] SystemBoxNames = {
			"in box", "out box", "spam box", "trash box"
		};

		for (final String SystemBoxName : SystemBoxNames)
			if (SystemBoxName.equals(boxToEdit.getName())) {
				isSystemBoxName = true;
				break;
			}

		Assert.isTrue(!isSystemBoxName);

		this.boxService.save(boxToEdit);
	}

	public void deleteMessage(final Message message, final Box referencedBox, final Actor actor) {
		Assert.notNull(message);
		Assert.notNull(referencedBox);
		Assert.notNull(actor);

		final int idPrincipal = LoginService.getPrincipal().getId();

		//the actor can only delete his own messages
		Assert.isTrue(actor.getId() == idPrincipal);

		boolean actorIsSender = false;
		boolean actorIsRecipient = false;
		if (message.getSender().getId() == actor.getId())
			actorIsSender = true;
		else if (message.getRecipients().contains(actor))
			actorIsRecipient = true;

		//the actor appears as sender or as recipient of the message
		Assert.isTrue(actorIsSender || actorIsRecipient);

		//Checks if the message is referenced in other boxes of other actors
		final Collection<Actor> recipients = message.getRecipients();
		//remove the actor if he is recipient
		if (actorIsRecipient)
			recipients.remove(actor);

		boolean isInAnotherBox = false;
		for (final Actor recipient : recipients)
			for (final Box box : recipient.getBoxes())
				if (box.getMessages().contains(message)) {
					isInAnotherBox = true;
					break;
				}

		if (!actorIsSender)
			for (final Box box : message.getSender().getBoxes())
				if (box.getMessages().contains(message)) {
					isInAnotherBox = true;
					break;
				}

		if (referencedBox.getName().equals("trash box")) {
			final Collection<Message> trashBoxMessages = referencedBox.getMessages();
			trashBoxMessages.remove(message);
			referencedBox.setMessages(trashBoxMessages);
			this.boxService.save(referencedBox);
			if (!isInAnotherBox)
				this.messageService.delete(message);
		} else {

			//delete the message from the box
			final Collection<Message> boxMessages = referencedBox.getMessages();
			boxMessages.remove(message);
			referencedBox.setMessages(boxMessages);

			//Obtains the trashBox of the actor
			final Collection<Box> actorBoxes = actor.getBoxes();
			Box trashBox = null;

			for (final Box box : actorBoxes)
				if (box.getSystemBox() && box.getName().equals("trash box")) {
					trashBox = box;
					break;
				}

			//And adds the message deleted from the box
			final Collection<Message> trashBoxMessages = trashBox.getMessages();
			trashBoxMessages.add(message);
			trashBox.setMessages(trashBoxMessages);

			//Update the couple of boxes edited
			this.boxService.save(trashBox);
			this.boxService.save(referencedBox);
		}
	}

	public void moveMessage(final Actor actor, final Message message, final Box originBox, final Box destinationBox) {
		Assert.notNull(message);
		Assert.notNull(originBox);
		Assert.notNull(destinationBox);
		Assert.notNull(actor);

		final int idPrincipal = LoginService.getPrincipal().getId();
		//the actor can only move his own messages
		Assert.isTrue(actor.getId() == idPrincipal);

		//Checks that the actor owns the two boxes
		Assert.isTrue(actor.getBoxes().contains(destinationBox) && actor.getBoxes().contains(originBox));

		//deletes the message from the originBox
		final Collection<Message> originBoxMessages = originBox.getMessages();
		originBoxMessages.remove(message);
		originBox.setMessages(originBoxMessages);

		//adds the message to the destinationBox
		final Collection<Message> destinationBoxMessages = destinationBox.getMessages();
		destinationBoxMessages.remove(message);
		destinationBox.setMessages(destinationBoxMessages);

		this.boxService.save(originBox);
		this.boxService.save(destinationBox);

	}

}
