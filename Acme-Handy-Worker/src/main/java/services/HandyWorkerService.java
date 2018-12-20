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
import security.UserAccountService;
import domain.Application;
import domain.Complaint;
import domain.Configuration;
import domain.Customer;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Note;
import domain.Phase;
import domain.Report;
import domain.Section;
import domain.Tutorial;

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
	@Autowired
	private PhaseService			phaseService;
	@Autowired
	private ApplicationService		applicationService;

	private ActorService			actorService;
	@Autowired
	private EndorserService			endorserService;
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private BoxService				boxService;
	@Autowired
	private SectionService			sectionService;
	@Autowired
	private TutorialService			tutorialService;


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
		Assert.notNull(handyWorker.getUserAccount().getUsername());
		Assert.notNull(handyWorker.getUserAccount().getPassword());

		//comprobamos que no nos han dado cadenas vacias en los at opcionales
		if (handyWorker.getMiddleName() != null)
			Assert.isTrue(!(handyWorker.getMiddleName().trim().equals("")));

		if (handyWorker.getAddress() != null)
			Assert.isTrue(!(handyWorker.getAddress().trim().equals("")));

		final HandyWorker result;

		if (handyWorker.getId() == 0) {

			handyWorker.setBoxes(this.boxService.originalBoxes());
			handyWorker.getUserAccount().setAccountNonLocked(true);
			handyWorker.setIsSuspicious(false);

			//le ponemos el make
			if (handyWorker.getMake() == null) {
				handyWorker.setMake(handyWorker.getName());
				final Finder finder = this.finderService.create();
				handyWorker.setFinder(finder);
			}
		} else {
			//comprobar que es el
			final UserAccount userAccount;
			userAccount = LoginService.getPrincipal();
			Assert.isTrue(handyWorker.getUserAccount().equals(userAccount));
		}

		result = this.handyWorkerRepository.save(handyWorker);
		return result;
	}

	//	public void delete(final HandyWorker handyWorker) {
	//		Assert.notNull(handyWorker);
	//		Assert.isTrue(handyWorker.getId() != 0);
	//
	//		//lo que dijo de que teniamos que comprobar si el que borraba su cuenta era el de la cuenta
	//		final UserAccount userAccount;
	//		userAccount = LoginService.getPrincipal();
	//		Assert.isTrue(handyWorker.getUserAccount().equals(userAccount));
	//
	//		this.handyWorkerRepository.delete(handyWorker);
	//	}

	// Other business methods -------------------------------------------------

	public HandyWorker findHandyWorkerByPrincipal() {
		HandyWorker result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public HandyWorker findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		HandyWorker result;

		result = this.handyWorkerRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

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

		final List<Configuration> configuration = new ArrayList<>(this.configurationService.findAll());
		//date	
		final Date lastUpdate = finder.getLastUpdate();
		final Date currentlyHour = new Date();
		final long difference = (currentlyHour.getTime() - lastUpdate.getTime()) / (60 * 60 * 1000);

		final Collection<FixUpTask> allFixUpTasks = this.fixUpTaskService.findAll();
		final Collection<FixUpTask> returnFixUpTasks = new HashSet<>();

		final int maxResult = configuration.get(0).getNumberOfResult();

		if (difference < configuration.get(0).getFinderCacheTime())
			return finder.getFixUpTasks();
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

	//R 37.1	Change the filters of his or her finder.
	public void changeFiltersFinder(final HandyWorker handyWorker, final Finder finder) {

		Assert.notNull(handyWorker);
		Assert.notNull(finder);
		Assert.isTrue(handyWorker.getId() != 0);

		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(handyWorker.getUserAccount().equals(userAccount));
		Assert.isTrue(handyWorker.getFinder().equals(finder));

		if (finder.getKeyWord() != null)
			Assert.isTrue(finder.getKeyWord().trim().equals(""));
		if (finder.getCategory() != null)
			Assert.isTrue(finder.getCategory().trim().equals(""));
		if (finder.getWarranty() != null)
			Assert.isTrue(finder.getWarranty().trim().equals(""));

		this.finderService.save(finder);

	}

	//R37.2   Display the fix-up tasks in his or her finder

	public Collection<FixUpTask> displayFixUpTaskFinder(final HandyWorker handyWorker) {

		Assert.notNull(handyWorker);
		Assert.isTrue(handyWorker.getId() != 0);
		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(handyWorker.getUserAccount().equals(userAccount));

		return handyWorker.getFinder().getFixUpTasks();

	}

	//R 37.3	List the complaints regarding the fix-up tasks in which he or she has been involved.

	public Collection<Complaint> listComplaints(final HandyWorker handyWorker) {

		Assert.notNull(handyWorker);
		Assert.isTrue(handyWorker.getId() != 0);
		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(handyWorker.getUserAccount().equals(userAccount));

		final Collection<Complaint> complaintList = this.handyWorkerRepository.displayComplaints(handyWorker.getId());

		return complaintList;

	}

	//R37.4	Write a note regarding any of the reports that a referee has written regarding any of the complaints in which he or she is involved.

	public void writeNoteReportByHandyWorker(final HandyWorker handyWorker, final Report report, final Note note) {

		Assert.notNull(handyWorker);
		Assert.notNull(report);
		Assert.isTrue(handyWorker.getId() != 0);
		//report en estado final
		Assert.isTrue(report.getIsFinal());

		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(handyWorker.getUserAccount().equals(userAccount));

		final Collection<Report> reportList = this.handyWorkerRepository.displayComplaintsReport(handyWorker.getId());
		Assert.isTrue(reportList.contains(report));

		Assert.notNull(note.getHandyWorkerComment());
		//solo puede ser un comentario del handy

		final Note oldNote = this.noteService.findOne(note.getId());
		if (note.getCustomerComment() != null)
			Assert.isTrue(note.getCustomerComment().equals(oldNote.getCustomerComment()));
		if (note.getRefereeComment() != null)
			Assert.isTrue(note.getRefereeComment().equals(oldNote.getRefereeComment()));

		note.setReport(report);
		this.noteService.save(note);

	}

	//R37.5

	public void writeCommentNoteByHandyWorker(final Note note, final String handyWorkerComment, final Report report) {
		Assert.notNull(note);
		Assert.notNull(handyWorkerComment);
		Assert.notNull(report);
		Assert.isTrue(!handyWorkerComment.equals(""));
		//report en estado final
		Assert.isTrue(report.getIsFinal());

		//solo lo usan HandyWorker
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.HANDYWORKER));

		//comprobar que en el report esta involucrado ese handy
		final Collection<Report> reportList = this.handyWorkerRepository.displayComplaintsReport(this.findHandyWorkerByPrincipal().getId());
		Assert.isTrue(reportList.contains(report));
		Assert.isTrue(note.getReport().equals(report));

		note.setHandyWorkerComment(handyWorkerComment);
		this.noteService.save(note);

	}

	//APPLICATION METHODS-----------------

	//R11.3
	public Collection<Application> listHisApplications(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);

		int idPrincipal;
		idPrincipal = LoginService.getPrincipal().getId();
		Assert.isTrue(idPrincipal == handyWorker.getUserAccount().getId());

		return this.handyWorkerRepository.findAllApplicationsByHandyWorkerId(handyWorker.getId());
	}

	public Application createApplication(final HandyWorker handyWorker, final Application application, final FixUpTask fixUpTask) {
		Assert.notNull(handyWorker);
		Assert.notNull(application);
		Assert.notNull(fixUpTask);

		int idPrincipal;
		idPrincipal = LoginService.getPrincipal().getId();
		Assert.isTrue(idPrincipal == handyWorker.getUserAccount().getId());

		//En el formulario de creación de application solo se habrán introducido el offeredPrice y comment, habrá que asignar valores al resto de atributos

		application.setMoment(new Date());
		application.setStatus("PENDING");
		application.setHandyWorker(handyWorker);
		application.setFixUpTask(fixUpTask);
		application.setCustomerComments(null);

		this.applicationService.save(application);
		return application;
	}

	public void deleteApplication(final HandyWorker handyWorker, final Application application) {
		Assert.notNull(handyWorker);
		Assert.notNull(application);

		this.applicationService.delete(application);
	}

	//R 11.4 PHASE METHODS-----------------

	public Collection<Phase> showPhasesPerFixUpTask(final HandyWorker handyWorker, final FixUpTask fixUpTask) {
		Assert.notNull(handyWorker);
		Assert.notNull(fixUpTask);

		int idPrincipal;
		idPrincipal = LoginService.getPrincipal().getId();
		Assert.isTrue(idPrincipal == handyWorker.getUserAccount().getId());

		return this.fixUpTaskService.getPhasesPerFixUpTask(fixUpTask.getId());
	}

	public Phase createOrUpdatePhase(final HandyWorker handyWorker, final FixUpTask fixUpTask, final Phase phase) {
		Assert.notNull(handyWorker);
		Assert.notNull(fixUpTask);
		Assert.notNull(phase);

		int idPrincipal;
		idPrincipal = LoginService.getPrincipal().getId();
		Assert.isTrue(idPrincipal == handyWorker.getUserAccount().getId());

		//the handyworker must own the fixUpTask application accepted
		Assert.isTrue(this.fixUpTaskService.getAcceptedHandyWorker(fixUpTask.getId()).getId() == handyWorker.getId());

		//phases can not be carried out before the start date or after the end date of the corresponding fixUpTask
		Assert.isTrue(fixUpTask.getStartTime().before(phase.getStartMoment()));
		Assert.isTrue(fixUpTask.getEndTime().after(phase.getEndMoment()));

		if (phase.getId() == 0)
			phase.setFixUpTask(fixUpTask);

		this.phaseService.save(phase);

		return phase;
	}

	public void deletePhase(final Phase phase) {
		Assert.notNull(phase);
		this.phaseService.delete(phase);
	}

	public Collection<Customer> getRelatedCustomers(final int handyWorkerId) {

		return this.handyWorkerRepository.findRelatedCustomersByHandyWorkerId(handyWorkerId);
	}

	//R47.2

	public Collection<Tutorial> getAllTutorialByHandyId(final int handyId) {

		//solo lo usan Handy
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.HANDYWORKER));

		return this.handyWorkerRepository.getAllTutorialsByHandyId(handyId);
	}

	//R49.1

	public Section createAndUpdateSection(final Tutorial tutorial, final Section section) {
		Assert.notNull(section);
		Assert.notNull(tutorial);
		//solo lo usan Handy
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.HANDYWORKER));
		Assert.isTrue(tutorial.getHandyWorker().equals(this.findHandyWorkerByPrincipal()));

		final int number = tutorial.getSections().size();
		section.setNumber(number);

		final Collection<Section> sections = tutorial.getSections();
		sections.add(section);
		tutorial.setSections(sections);

		this.tutorialService.save(tutorial, this.findHandyWorkerByPrincipal());
		return this.sectionService.save(section);
	}

	public Collection<Section> showSectionByTutorial(final Tutorial tutorial) {

		Assert.notNull(tutorial);
		//solo lo usan Handy
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.HANDYWORKER));
		Assert.isTrue(tutorial.getHandyWorker().equals(this.findHandyWorkerByPrincipal()));

		return this.tutorialService.findSectionByTutorial(tutorial.getId());
	}

}
