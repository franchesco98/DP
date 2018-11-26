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
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Complaint;
import domain.Configuration;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Note;
import domain.Report;

@Service
@Transactional
public class HandyWorkerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private NoteService				noteService;


	// Constructors -----------------------------------------------------------

	public HandyWorkerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public HandyWorker create() {
		HandyWorker result;

		result = new HandyWorker();
		//meterle el authority
		final UserAccount userAccount = new UserAccount();
		final Authority authotity = new Authority();
		authotity.setAuthority(Authority.HANDYWORKER);
		final Collection<Authority> authorities = new ArrayList<>();
		authorities.add(authotity);
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);
		return result;
	}

	public Collection<HandyWorker> findAll() {
		Collection<HandyWorker> result;

		result = this.handyWorkerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public HandyWorker findOne(final int handyWorkerId) {
		HandyWorker result;

		result = this.handyWorkerRepository.findOne(handyWorkerId);
		Assert.notNull(result);

		return result;
	}

	public HandyWorker save(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);

		HandyWorker result;

		if (handyWorker.getId() == 0) {
			//le ponemos el make
			if (handyWorker.getMake() == null)
				handyWorker.setMake(handyWorker.getName());
		} else {
			//comprobar si el que borraba su cuenta era el de la cuenta
			final UserAccount userAccount;
			userAccount = LoginService.getPrincipal();
			Assert.isTrue(handyWorker.getUserAccount().equals(userAccount));
		}
		result = this.handyWorkerRepository.save(handyWorker);
		return result;
	}

	public void delete(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);
		Assert.isTrue(handyWorker.getId() != 0);

		//lo que dijo de que teniamos que comprobar si el que borraba su cuenta era el de la cuenta
		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(handyWorker.getUserAccount().equals(userAccount));

		//		//no puede tener phases
		//		Assert.isTrue();
		//		//no puede tener application
		//		Assert.isTrue();
		//		//no puede tener tutrials
		//		Assert.isTrue();

		this.handyWorkerRepository.delete(handyWorker);
	}

	// Other business methods -------------------------------------------------

	public Collection<FixUpTask> showFilteredFixUpTasks(final HandyWorker handyWorker, final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(handyWorker.getFinder().getId() == finder.getId());

		//comprobamos que los parametros no sean cadenas vacias en caso de no ser null
		if (finder.getKeyWord() != null)
			Assert.isTrue(!(finder.getKeyWord().equals("")));
		if (finder.getCategory() != null)
			Assert.isTrue(!(finder.getCategory().equals("")));
		if (finder.getWarranty() != null)
			Assert.isTrue(!(finder.getWarranty().equals("")));

		int idPrincipal;
		idPrincipal = LoginService.getPrincipal().getId();
		Assert.isTrue(idPrincipal == handyWorker.getId());

		//COMPROBAR SI EL FINDER ESTA EN CACHE O NO

		final List<Configuration> configuration = new ArrayList<>(this.configurationService.findAll());
		//date	
		final Date lastUpdate = finder.getLastUpdate();
		final Date currentlyHour = new Date();
		final long difference = (currentlyHour.getTime() - lastUpdate.getTime()) / (60 * 60 * 1000);

		final Collection<FixUpTask> allFixUpTasks = this.fixUpTaskService.findAll();
		final Collection<FixUpTask> returnFixUpTasks = new HashSet<>();

		final int maxResult = configuration.get(0).getNumberOfResult();

		if (difference < configuration.get(0).getFinderCacheTime())
			//lo coge de la cache  
			return null;
		else
			for (final FixUpTask fixUpTask : allFixUpTasks) {
				if (finder.getKeyWord() != null)
					if (returnFixUpTasks.size() == maxResult)
						break;
				if (fixUpTask.getTicker().contains(finder.getKeyWord()) || fixUpTask.getDescription().contains(finder.getKeyWord()) || fixUpTask.getAddress().contains(finder.getKeyWord()))

					returnFixUpTasks.add(fixUpTask);
				if (finder.getCategory() != null)
					if (returnFixUpTasks.size() == maxResult)
						break;
				if (fixUpTask.getCategory().getName().contains(finder.getCategory()))
					returnFixUpTasks.add(fixUpTask);
				if (finder.getWarranty() != null)
					if (returnFixUpTasks.size() == maxResult)
						break;
				if (fixUpTask.getWarranty().getTitle().contains(finder.getWarranty()))
					returnFixUpTasks.add(fixUpTask);

				if (finder.getPriceMin() != null && finder.getPriceMax() != null)
					if (returnFixUpTasks.size() == maxResult)
						break;
				if (fixUpTask.getMaxPrice() >= finder.getPriceMin() && fixUpTask.getMaxPrice() <= finder.getPriceMax())
					returnFixUpTasks.add(fixUpTask);
				if (finder.getDateMin() != null && finder.getDateMax() != null)
					if (returnFixUpTasks.size() == maxResult)
						break;
				if (fixUpTask.getEndTime().before(finder.getDateMin()) && fixUpTask.getEndTime().after(finder.getDateMax()))
					returnFixUpTasks.add(fixUpTask);
			}

		//GUARDAR EN CACHE
		finder.setFixUpTasks(returnFixUpTasks);
		this.finderService.save(finder);
		return returnFixUpTasks;
	}

	public Collection<FixUpTask> findAllFixUpTask(final HandyWorker handyWorker) {

		//comprobar si el que borraba su cuenta era el de la cuenta
		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(handyWorker.getUserAccount().equals(userAccount));
		return this.fixUpTaskService.findAll();

	}

	//	Change the filters of his or her finder.
	public void changeFiltersFinder(final HandyWorker handyWorker, final Finder f) {

		Assert.notNull(handyWorker);
		Assert.isTrue(handyWorker.getId() != 0);
		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(handyWorker.getUserAccount().equals(userAccount));
		Assert.isTrue(handyWorker.getFinder().equals(f));

		this.finderService.save(f);

	}

	// Display the fix-up tasks in his or her finder
	public Collection<FixUpTask> displayFixUpTaskFinder(final HandyWorker handyWorker) {

		Assert.notNull(handyWorker);
		Assert.isTrue(handyWorker.getId() != 0);
		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(handyWorker.getUserAccount().equals(userAccount));

		final Collection<FixUpTask> fixUpTaskList = this.handyWorkerRepository.displayFixUpTasks(handyWorker.getId());

		return fixUpTaskList;

	}

	//	List the complaints regarding the fix-up tasks in which he or she has been involved.
	public Collection<Complaint> listComplaints(final HandyWorker handyWorker) {

		Assert.notNull(handyWorker);
		Assert.isTrue(handyWorker.getId() != 0);
		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(handyWorker.getUserAccount().equals(userAccount));

		final Collection<Complaint> complaintList = this.handyWorkerRepository.displayComplaints(handyWorker.getId());

		return complaintList;

	}

	//	Write a note regarding any of the reports that a referee has written regarding any of the complaints in which he or she is involved.

	public void writeNoteReport(final HandyWorker handyWorker, final Report report) {
		Assert.notNull(handyWorker);
		Assert.isTrue(handyWorker.getId() != 0);
		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(handyWorker.getUserAccount().equals(userAccount));

		final Collection<Report> reportList = this.handyWorkerRepository.displayComplaintsReport(handyWorker.getId());
		Assert.isTrue(reportList.contains(report));

		Note res;
		res = this.noteService.create();
		res.setReport(report);
		this.noteService.save(res);

	}

	public void writeCommentNote(final HandyWorker handyWorker, final Report report, final Note note, final String handyWorkerComment) {
		Assert.notNull(handyWorker);
		Assert.isTrue(handyWorker.getId() != 0);
		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(handyWorker.getUserAccount().equals(userAccount));

		final Collection<Report> reportList = this.handyWorkerRepository.displayComplaintsReport(handyWorker.getId());
		Assert.isTrue(reportList.contains(report));
		Assert.isTrue(note.getReport().equals(report));

		note.setHandyWorkerComment(handyWorkerComment);
		this.noteService.save(note);

	}

}
