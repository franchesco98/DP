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

import repositories.NoteRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Note;

@Service
@Transactional
public class NoteService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private NoteRepository	noteRepository;


	// Constructors -----------------------------------------------------------

	public NoteService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Note create() {
		Note result;
		result = new Note();
		final UserAccount userAccount = LoginService.getPrincipal();
		//Compruebo que en todo momento es un HW
		final Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);// es el refeerree un authority del sistema?
		Assert.isTrue(userAccount.getAuthorities().contains(authority), "Usted no es  un Customer, por favor inicie sesion de nuevo");
		Assert.isTrue(result.getReport().getIsFinal());// me aseguro que el report sea final para escribir notas y que se aun referee customer solo??
		return result;
	}

	public Collection<Note> findAll() {
		Collection<Note> result;

		result = this.noteRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Note findOne(final int noteId) {
		Note result;

		result = this.noteRepository.findOne(noteId);
		Assert.notNull(result);

		return result;
	}

	public Note save(final Note note) {
		Assert.notNull(note);

		Note result;

		result = this.noteRepository.save(note);

		return result;
	}

	public void delete(final Note note) {
		Assert.notNull(note);
		Assert.isTrue(note.getId() != 0);

		this.noteRepository.delete(note);
	}

	// Other business methods -------------------------------------------------

}
