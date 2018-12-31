
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Box;
import domain.Configuration;
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageRepository		messageRepository;

	// Managed serviced ---------------------------------------

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private BoxService				boxService;
	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Message create() {

		Message result;
		result = new Message();
		final Actor logedActor = this.actorService.findActorByPrincipal();
		result.setSender(logedActor);
		result.setRecipients(new HashSet<Actor>());
		result.setFlagSpam(false);
		result.setMoment(new Date());

		return result;
	}
	public Collection<Message> findAll() {
		Collection<Message> result;

		result = this.messageRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Message findOne(final int MessageId) {
		Message result;

		result = this.messageRepository.findOne(MessageId);
		Assert.notNull(result);

		return result;
	}

	public Box save(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getRecipients().size() >= 1);
		//El actor solo puede enviar mensajes sobre el mismo
		final Actor logedActor = this.actorService.findActorByPrincipal();
		message.setSender(logedActor);

		//Comprueba que no tags en blank
		if (message.getTag() != null) {

			final String[] aux = message.getTag().split(",");
			for (final String tag : aux)
				Assert.isTrue(!tag.trim().equals(""));
		}

		//Da valor al atributo moment y comprueba si el mensaje es spam
		message.setMoment(new Date());

		Message result;

		result = this.checkAndSetFlagSpam(message);

		result = this.messageRepository.save(message);

		final Collection<Box> boxes = result.getSender().getBoxes();

		//Añadir mensaje a la outbox del sender
		Box outBox = null;
		for (final Box box : boxes)
			if (box.getSystemBox() && box.getName().equals("Out box"))
				outBox = box;

		final Collection<Message> outBoxMessages = outBox.getMessages();
		outBoxMessages.add(result);
		outBox.setMessages(outBoxMessages);
		outBox = this.boxService.save(outBox);

		//Comprueba si el mensaje es spam
		if (message.getFlagSpam()) {
			Box spamBox = null;
			//ponemos el actor como sospechoso
			final Actor sender = result.getSender();
			sender.setIsSuspicious(true);
			this.actorService.save(sender);

			//Recorremos los recipient del mensaje pasado como parámetro
			for (final Actor recipient : message.getRecipients()) {
				//Recorremos las boxes del recipient
				for (final Box b : recipient.getBoxes())
					if (b.getSystemBox() && b.getName().equals("Spam box"))
						spamBox = b;
				final Collection<Message> spamBoxMessages = spamBox.getMessages();

				//Añadir mensaje a la spam box de los recipients
				spamBoxMessages.add(result);
				spamBox.setMessages(spamBoxMessages);
				spamBox = this.boxService.save(spamBox);
			}
		} else {
			Box inBox = null;

			//Recorremos los recipients del mensaje pasado como parámetro
			for (final Actor recipient : message.getRecipients()) {
				//Recorremos las boxes del recipient
				for (final Box b : recipient.getBoxes())
					if (b.getSystemBox() && b.getName().equals("In box"))
						inBox = b;
				final Collection<Message> inBoxMessages = inBox.getMessages();

				//Añadir mensaje a las in boxes de los recipients
				inBoxMessages.add(result);
				inBox.setMessages(inBoxMessages);
				this.boxService.save(inBox);
			}
		}

		return outBox;
	}
	public void delete(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);

		this.messageRepository.delete(message);
	}
	// Other business methods -------------------------------------------------

	public Message checkAndSetFlagSpam(final Message message) {
		boolean flagSpam = false;
		Assert.notNull(message);

		final String body = message.getBody().toLowerCase();
		final String subject = message.getSubject().toLowerCase();
		final List<Configuration> configuration = new ArrayList<>(this.configurationService.findAll());
		final String[] spamWords = configuration.get(0).getSpamWords().split(",");

		for (final String spamWord : spamWords)
			if (subject.contains(spamWord.trim().toLowerCase()) || body.contains(spamWord.trim().toLowerCase()))
				flagSpam = true;

		message.setFlagSpam(flagSpam);

		return message;
	}
}
