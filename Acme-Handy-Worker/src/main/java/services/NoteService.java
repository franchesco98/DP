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
import domain.Actor;
import domain.Note;
import domain.Report;

@Service
@Transactional
public class NoteService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private NoteRepository		noteRepository;
	@Autowired
	private ReportService		reportService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private ComplaintService	complaintService;
	@Autowired
	private RefereeService		refereeService;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Constructors -----------------------------------------------------------

	public NoteService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Note create() {
		Note result;
		result = new Note();
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

	public Collection<Note> getNotesByReportId(final int reportId) {

		final Actor a = this.actorService.findActorByPrincipal();

		final Report report = this.reportService.findOne(reportId);

		//comprobamos que solo accede a sus notes
		final Authority customerAuthority = new Authority();
		customerAuthority.setAuthority("CUSTOMER");
		final Authority refereeAuthority = new Authority();
		refereeAuthority.setAuthority("REFEREE");
		final Authority handyAuthority = new Authority();
		handyAuthority.setAuthority("HANDYWORKER");

		if (a.getUserAccount().getAuthorities().contains(handyAuthority))
			Assert.isTrue(this.handyWorkerService.listComplaints(this.handyWorkerService.findHandyWorkerByPrincipal()).contains(report.getComplaint()));
		else if (a.getUserAccount().getAuthorities().contains(customerAuthority))
			Assert.isTrue(report.getComplaint().getFixUpTask().getCustomer().equals(this.customerService.findCustomerByPrincipal()));
		else if (a.getUserAccount().getAuthorities().contains(refereeAuthority))
			Assert.isTrue(report.getComplaint().getReferee().equals(this.refereeService.findRefereeByPrincipal()));
		else
			Assert.isTrue(false);
		return this.noteRepository.getNotesByReportId(reportId);

	}
}
