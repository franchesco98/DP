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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import domain.Warranty;

@Service
@Transactional
public class WarrantyService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private WarrantyRepository	warrantyRepository;


	// Constructors -----------------------------------------------------------

	public WarrantyService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Warranty create() {
		Warranty result;

		result = new Warranty();

		return result;
	}

	public Collection<Warranty> findAll() {
		Collection<Warranty> result;

		result = this.warrantyRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Warranty findOne(final int warrantyId) {
		Warranty result;

		result = this.warrantyRepository.findOne(warrantyId);
		Assert.notNull(result);

		return result;
	}

	public Warranty save(final Warranty warranty) {
		Assert.notNull(warranty);

		Warranty result;

		result = this.warrantyRepository.save(warranty);
		//una Warranty solo se puede eliminar si esta en modo borrador
		Assert.isTrue(!result.getIsFinal());
		return result;
	}

	public void delete(final Warranty warranty) {
		Assert.notNull(warranty);
		Assert.isTrue(warranty.getId() != 0);
		//una Warranty solo se puede eliminar si esta en modo borrador
		Assert.isTrue(!warranty.getIsFinal());
		this.warrantyRepository.delete(warranty);
	}

	// Other business methods -------------------------------------------------

}
