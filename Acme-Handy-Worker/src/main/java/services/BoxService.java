/*
 * CustomerService.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class BoxService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BoxRepository	boxRepository;


	// Constructors -----------------------------------------------------------

	public BoxService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Box create() {
		Box result;
		final Collection<Message> messages = new ArrayList<>();

		result = new Box();
		result.setMessages(messages);
		result.setSystemBox(false);

		return result;
	}

	public Collection<Box> findAll() {
		Collection<Box> result;

		result = this.boxRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Box findOne(final int BoxId) {
		Box result;

		result = this.boxRepository.findOne(BoxId);
		Assert.notNull(result);

		return result;
	}

	public Box save(final Box box) {
		Assert.notNull(box);
		Box result;

		result = this.boxRepository.save(box);

		return result;
	}

	public void delete(final Box box) {
		Assert.notNull(box);
		Assert.isTrue(box.getId() != 0);

		this.boxRepository.delete(box);
	}

	//Business method

	public Collection<Box> originalBoxes() {

		final Collection<Box> res = new ArrayList<>();
		final Collection<Message> messages = new ArrayList<>();
		Box saved;

		final Box inBox = this.create();
		inBox.setName("in box");
		inBox.setSystemBox(true);
		inBox.setMessages(messages);
		saved = this.save(inBox);
		res.add(saved);

		final Box outBox = this.create();
		outBox.setName("out box");
		outBox.setSystemBox(true);
		outBox.setMessages(messages);
		saved = this.save(outBox);
		res.add(saved);

		final Box spamBox = this.create();
		spamBox.setName("spam box");
		spamBox.setSystemBox(true);
		spamBox.setMessages(messages);
		saved = this.save(spamBox);
		res.add(saved);

		final Box trashBox = this.create();
		trashBox.setName("trash box");
		trashBox.setSystemBox(true);
		trashBox.setMessages(messages);
		saved = this.save(trashBox);
		res.add(saved);

		return res;
	}

	public Collection<Box> searchBoxesByMessage(final Actor actor, final Message message) {
		final Collection<Box> res = new ArrayList<>();

		for (final Box b : actor.getBoxes()) {
			if (b.getMessages().contains(message)) {
				res.add(b);
			}
		}

		return res;
	}

}
