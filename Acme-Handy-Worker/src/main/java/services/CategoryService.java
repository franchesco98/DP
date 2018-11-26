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

import repositories.CategoryRepository;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CategoryRepository	categoryRepository;


	// Constructors -----------------------------------------------------------

	public CategoryService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Category create() {
		Category result;

		result = new Category();

		return result;
	}

	public Collection<Category> findAll() {
		Collection<Category> result;

		result = this.categoryRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Category findOne(final int categoryId) {
		Category result;

		result = this.categoryRepository.findOne(categoryId);
		Assert.notNull(result);

		return result;
	}

	public Category save(final Category category) {
		Assert.notNull(category);

		Category result;

		result = this.categoryRepository.save(category);

		return result;
	}

	public void delete(final Category category) {
		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);

		this.categoryRepository.delete(category);
	}

	// Other business methods -------------------------------------------------

}
