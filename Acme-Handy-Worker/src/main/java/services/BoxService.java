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

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import domain.Box;

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

		result = new Box();

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

		final Collection<Box> res = new HashSet<>();

		final Box inBox = this.create();
		inBox.setName("inBox");
		res.add(inBox);

		final Box outBox = this.create();
		inBox.setName("outBox");
		res.add(outBox);

		final Box spamBox = this.create();
		inBox.setName("spamBox");
		res.add(spamBox);

		final Box trashBox = this.create();
		inBox.setName("trashBox");
		res.add(trashBox);

		return res;
	}

}
