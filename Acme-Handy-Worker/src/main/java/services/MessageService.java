
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.LoginService;
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


	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Message create() {

		Message result;
		result = new Message();

		result.setRecipients(new HashSet<Actor>());

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

	public Message save(final Message message) {
		Assert.notNull(message);
		final int userAccountId = LoginService.getPrincipal().getId();
		Assert.isTrue(message.getSender().getId() == userAccountId);

		final Message result;

		final Collection<Box> boxes = message.getSender().getBoxes();

		//Añadir mensaje a la outbox del sender
		Box outBox = null;
		for (final Box box : boxes)
			if (box.getSystemBox() && box.getName().equals("out box"))
				outBox = box;

		final Collection<Message> outBoxMessages = outBox.getMessages();
		outBoxMessages.add(message);
		outBox.setMessages(outBoxMessages);
		this.boxService.save(outBox);
		//

		//Comprueba si el mensaje es spam
		this.setFlagSpam(message);
		if (message.getFlagSpam()) {
			Box spamBox = null;

			//Recorremos los recipient del mensaje pasado como parámetro
			for (final Actor recipient : message.getRecipients()) {
				//Recorremos las boxes del recipient
				for (final Box b : recipient.getBoxes())
					if (b.getSystemBox() && b.getName().equals("spam box"))
						spamBox = b;
				final Collection<Message> spamBoxMessages = spamBox.getMessages();

				//Añadir mensaje a la spam box de los recipients
				spamBoxMessages.add(message);
				spamBox.setMessages(spamBoxMessages);
				this.boxService.save(spamBox);
			}
		} else {
			Box inBox = null;

			//Recorremos los recipients del mensaje pasado como parámetro
			for (final Actor recipient : message.getRecipients()) {
				//Recorremos las boxes del recipient
				for (final Box b : recipient.getBoxes())
					if (b.getSystemBox() && b.getName().equals("in box"))
						inBox = b;
				final Collection<Message> inBoxMessages = inBox.getMessages();

				//Añadir mensaje a las in boxes de los recipients
				inBoxMessages.add(message);
				inBox.setMessages(inBoxMessages);
				this.boxService.save(inBox);
			}
		}

		result = this.messageRepository.save(message);

		return result;
	}
	public void delete(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);

		this.messageRepository.delete(message);
	}
	// Other business methods -------------------------------------------------

	public void setFlagSpam(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);

		final String body = message.getBody().toLowerCase();
		final String subject = message.getSubject().toLowerCase();
		final List<Configuration> configuration = new ArrayList<>(this.configurationService.findAll());
		final String[] spamWords = configuration.get(0).getSpamWords().split(",");

		for (final String spamWord : spamWords)
			if (subject.contains(spamWord.trim().toLowerCase()) || body.contains(spamWord.trim().toLowerCase()))
				message.setFlagSpam(true);
	}
}
