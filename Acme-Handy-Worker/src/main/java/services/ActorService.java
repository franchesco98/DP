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
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Box;
import domain.CreditCard;
import domain.Customer;
import domain.HandyWorker;
import domain.Message;
import domain.Profile;
import domain.Sponsor;
import domain.Tutorial;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository		actorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private BoxService			boxService;
	@Autowired
	private MessageService		messageService;
	@Autowired
	private UserAccountService	userAccountService;
	@Autowired
	private ProfileService		profileService;
	@Autowired
	private TutorialService		tutorialService;
	@Autowired
	private HandyWorkerService	handyWorkerService;
	@Autowired
	private CreditCardService	creditCardService;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private SponsorService		sponsorService;


	// Constructors -----------------------------------------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	//	public Actor create() {
	//
	//		Actor result;
	//		result = new Actor();
	//
	//		final UserAccount userAccount = this.userAccountService.create();
	//		final Collection<Box> boxes = new HashSet<Box>();
	//
	//		result.setUserAccount(userAccount);
	//		result.setBoxes(boxes);
	//
	//		return result;
	//	}

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
		if (actor.getMiddleName() != null)
			Assert.isTrue(!(actor.getMiddleName().trim().equals("")));

		if (actor.getAddress() != null)
			Assert.isTrue(!(actor.getAddress().trim().equals("")));
		Actor result;

		if (actor.getId() == 0) {

			actor.setBoxes(this.boxService.originalBoxes());
			actor.setIsBanned(false);
			actor.setIsSuspicious(false);
			result = this.actorRepository.save(actor);

		} else {
			int idPrincipal;
			idPrincipal = LoginService.getPrincipal().getId();
			Assert.isTrue(idPrincipal == actor.getId());
			result = this.actorRepository.save(actor);
		}

		this.userAccountService.save(actor.getUserAccount());
		return result;
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(this.actorRepository.exists(actor.getId()));

		this.actorRepository.delete(actor);
	}

	// Other business methods -------------------------------------------------

	//selec actor by userAcount
	public Actor findActorByPrincipal() {
		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.actorByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Actor actorByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Actor result;

		result = this.actorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public void createNewBox(final Actor actor, final Box newBox) {
		Assert.notNull(actor);
		Assert.notNull(newBox);

		//An actor can only create boxes for himself
		int idPrincipal;
		idPrincipal = LoginService.getPrincipal().getId();
		Assert.isTrue(idPrincipal == actor.getUserAccount().getId());

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
		Assert.isTrue(idPrincipal == actor.getUserAccount().getId());

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
		Assert.isTrue(idPrincipal == actor.getUserAccount().getId());

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

		//An actor can only edit his boxes
		int idPrincipal;
		idPrincipal = LoginService.getPrincipal().getId();
		Assert.isTrue(idPrincipal == actor.getUserAccount().getId());

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

		int idPrincipal;
		idPrincipal = LoginService.getPrincipal().getId();
		Assert.isTrue(idPrincipal == actor.getUserAccount().getId());

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

	//Profile

	public Profile createProfile(final Actor actor, final Profile profile) {
		Assert.notNull(actor);
		Assert.notNull(profile);

		int idPrincipal;
		idPrincipal = LoginService.getPrincipal().getId();
		Assert.isTrue(idPrincipal == actor.getUserAccount().getId());

		Profile result;

		if (profile.getId() != 0)
			//en el caso de actualizar comoprueba que es su profile 
			Assert.isTrue(profile.getActor().getUserAccount().getId() == idPrincipal);

		result = this.profileService.save(profile);

		return result;
	}

	public void deleteProfile(final Actor actor, final Profile profile) {
		Assert.notNull(actor);
		Assert.notNull(profile);

		//An actor 
		int idPrincipal;
		idPrincipal = LoginService.getPrincipal().getId();
		Assert.isTrue(idPrincipal == actor.getUserAccount().getId());
		//en el caso de actualizar comoprueba que es su profile 
		Assert.isTrue(profile.getActor().getUserAccount().getId() == idPrincipal);

		this.profileService.delete(profile);

	}

	//R47.2

	public Map<HandyWorker, Collection<Tutorial>> showProfileHandy(final int handyId) {

		final Map<HandyWorker, Collection<Tutorial>> res = new HashMap<>();

		final Collection<Tutorial> tutorials = this.handyWorkerService.getAllTutorialByHandyId(handyId);
		final HandyWorker handy = this.handyWorkerService.findOne(handyId);

		res.put(handy, tutorials);

		return res;
	}

	public CreditCard createAndUpdateCreditCard(final CreditCard creditCard, final Customer customer, final Sponsor sponsor) {

		Assert.notNull(creditCard);
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		//solo puede ser customer o sponsor
		Assert.isTrue(userAccount.getAuthorities().contains(Authority.CUSTOMER) || userAccount.getAuthorities().contains(Authority.SPONSOR));
		//no pueden ser los dos null
		Assert.isTrue(!(customer == null && sponsor == null));

		if (creditCard.getId() == 0) {
			if (customer == null)
				creditCard.setActor(sponsor);
			else
				//es el customer
				creditCard.setActor(customer);
		} else if (customer == null)
			Assert.isTrue(creditCard.getActor().getId() == sponsor.getId());
		else
			//es el customer
			Assert.isTrue(creditCard.getActor().getId() == customer.getId());

		this.creditCardService.validCreditCard(creditCard);
		return this.creditCardService.save(creditCard);
	}

	public Collection<CreditCard> listCreditCards(final Customer customer, final Sponsor sponsor) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		//solo puede ser customer o sponsor
		Assert.isTrue(userAccount.getAuthorities().contains(Authority.CUSTOMER) || userAccount.getAuthorities().contains(Authority.SPONSOR));
		//no pueden ser los dos null
		Assert.isTrue(!(customer == null && sponsor == null));

		if (customer == null)
			return this.customerService.getCreditCardsByCustomerId(customer.getId());
		else
			//es el customer
			return this.sponsorService.getCreditCardsBySponsorId(sponsor.getId());
	}
	//TODO eliminar tarjetas de credito

}
