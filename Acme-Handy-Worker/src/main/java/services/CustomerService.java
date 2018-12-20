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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Application;
import domain.Category;
import domain.Complaint;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Message;
import domain.Note;
import domain.Report;
import domain.Warranty;

@Service
@Transactional
public class CustomerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CustomerRepository		customerRepository;
	@Autowired
	private FixUpTaskService		fixUpTaskService;
	@Autowired
	private ComplaintService		complaintService;
	@Autowired
	private ApplicationService		applicationService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private EndorserService			endorserService;
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private MessageService			messageService;
	@Autowired
	private CreditCardService		creditCardService;
	@Autowired
	private NoteService				noteService;
	@Autowired
	private BoxService				boxService;


	// Constructors -----------------------------------------------------------

	public CustomerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Customer create() {
		Customer result;

		result = new Customer();
		//meterle el authority
		final UserAccount userAccount = new UserAccount();
		final Authority authotity = new Authority();
		authotity.setAuthority(Authority.CUSTOMER);
		final Collection<Authority> authorities = new ArrayList<>();
		authorities.add(authotity);
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);

		return result;
	}
	public Collection<Customer> findAll() {
		Collection<Customer> result;

		result = this.customerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Customer findOne(final int customerId) {
		Customer result;

		result = this.customerRepository.findOne(customerId);
		Assert.notNull(result);

		return result;
	}

	public Customer save(final Customer customer) {
		Assert.notNull(customer);
		Assert.notNull(customer.getUserAccount().getUsername());
		Assert.notNull(customer.getUserAccount().getPassword());

		//comprobamos que no nos han dado cadenas vacias en los at opcionales
		if (customer.getMiddleName() != null)
			Assert.isTrue(!(customer.getMiddleName().trim().equals("")));

		if (customer.getAddress() != null)
			Assert.isTrue(!(customer.getAddress().trim().equals("")));

		Customer result;
		if (customer.getId() == 0) {

			customer.setBoxes(this.boxService.originalBoxes());
			customer.getUserAccount().setAccountNonLocked(true);
			customer.setIsSuspicious(false);

		} else {
			int idPrincipal;
			idPrincipal = LoginService.getPrincipal().getId();
			Assert.isTrue(idPrincipal == customer.getId());
		}

		result = this.customerRepository.save(customer);

		return result;
	}

	//	public void delete(final Customer customer) {
	//		Assert.notNull(customer);
	//		Assert.isTrue(customer.getId() != 0);
	//
	//		//comprueba si es el
	//		final UserAccount userAccount;
	//		userAccount = LoginService.getPrincipal();
	//		Assert.isTrue(customer.getUserAccount().equals(userAccount));
	//		//no puede tener fixUpTask 
	//		Assert.isTrue(this.fixUpTaskService.numFixUpTaskByCustomer(customer).equals(0));
	//		//no puede tener complaint
	//		Assert.isTrue(this.complaintService.numComplaintByCustomer(customer).equals(0));
	//
	//		this.customerRepository.delete(customer);
	//	}

	// Other business methods -------------------------------------------------

	public Customer findCustomerByPrincipal() {
		Customer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Customer findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Customer result;

		result = this.customerRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	//R10

	public Collection<FixUpTask> listingFixUpTasksCreatedByCustomerPrincipal() {

		final Customer customer = this.findByUserAccount(LoginService.getPrincipal());

		//comprobamos que el principal es un customer
		final Authority customerAuthority = new Authority();
		customerAuthority.setAuthority("CUSTOMER");
		Assert.isTrue(customer.getUserAccount().getAuthorities().contains(customerAuthority));

		return this.customerRepository.findByCustomerId(customer.getId());

	}

	public FixUpTask showFixUpTask(final int fixUpTaskId, final Customer customer) {
		Assert.notNull(customer);
		final int userAccountId = LoginService.getPrincipal().getId();
		Assert.isTrue(userAccountId == customer.getUserAccount().getId());

		final FixUpTask res = this.fixUpTaskService.findOne(fixUpTaskId);
		return res;
	}

	public FixUpTask saveFixUpTaskCustomer(final Customer customer, final FixUpTask fixUpTask, final Category category, final Warranty warranty) {
		Assert.notNull(customer);
		Assert.notNull(fixUpTask);
		Assert.notNull(category);
		Assert.notNull(warranty);

		final int userAccountId = LoginService.getPrincipal().getId();
		Assert.isTrue(userAccountId == customer.getUserAccount().getId());
		//comprobamos valided de fechas
		Assert.isTrue(fixUpTask.getStartTime().before(fixUpTask.getEndTime()));
		//solo se pueden asociar fixUpTask finales
		Assert.isTrue(fixUpTask.getWarranty().getIsFinal());

		final FixUpTask res;

		if (fixUpTask.getId() != 0)
			fixUpTask.setCustomer(customer);
		else {

			//ponemos el customer que la crea
			fixUpTask.setCustomer(customer);
			//ponemos el momento de creacion
			fixUpTask.setMoment(new Date());
			//ponemos su ticker
			fixUpTask.setTicker(this.configurationService.getValidTicker());
		}

		//su categoty
		fixUpTask.setCategory(category);
		//su warranty
		fixUpTask.setWarranty(warranty);

		res = this.fixUpTaskService.save(fixUpTask);

		return res;

	}

	public void deleteFixUpTaskCustomer(final Customer customer, final FixUpTask f) {

		Assert.notNull(f);
		Assert.notNull(customer);

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains("CUSTOMER"));

		final int customerId = LoginService.getPrincipal().getId();
		Assert.isTrue(customerId == customer.getUserAccount().getId());
		Assert.isTrue(f.getCustomer().getUserAccount().getId() == customerId);

		//una fixUpTask no puede tener complaint 
		Assert.isTrue(this.customerRepository.findComplaintByFixUpTaskId(f.getId()).size() == 0);
		//una fixUpTask no puede tener application
		Assert.isTrue(this.customerRepository.findApplicationsByFixUpTaskId(f.getId()).size() == 0);
		//una fixUpTask no puede tener phase
		Assert.isTrue(this.customerRepository.findPhaseByFixUpTaskId(f.getId()).size() == 0);

		this.fixUpTaskService.delete(f);
	}

	public Collection<Application> findApplicationsByCustomerId(final Customer customer) {
		Assert.notNull(customer);
		//si el que borra su cuenta era el de la cuenta
		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(customer.getUserAccount().equals(userAccount));

		final Collection<Application> res = this.customerRepository.findApplicationsByCustomerId(customer.getId());
		return res;
	}

	public Collection<HandyWorker> getRelatedHandyWorkers(final int customerId) {

		return this.customerRepository.findRelatedHandyWorkersByCustomerId(customerId);
	}

	//R10
	public Application updateCustomerApplication(final Customer customer, final Application application, final CreditCard creditCard) {

		Assert.notNull(customer);
		Assert.notNull(application);
		//el que borra su cuenta es el de la cuenta
		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(customer.getUserAccount().equals(userAccount));

		//El status de la application solo puede cambiar de pending a accepted
		final Application outDate = this.applicationService.findOne(application.getId());
		Assert.isTrue(outDate.getStatus().equals("PENDING"));

		if (application.equals("ACCEPTED"))
			this.creditCardService.validCreditCard(creditCard);

		this.sendMessageDueToApplicationUpdating(customer, application);
		final Application res = this.applicationService.save(application);

		return res;
	}

	//R19

	public Message sendMessageDueToApplicationUpdating(final Customer customer, final Application application) {
		Assert.notNull(customer);
		Assert.notNull(application);

		final Message message = this.messageService.create();
		message.setSender(customer);

		final Collection<Actor> recipients = new HashSet<Actor>();
		recipients.add(application.getHandyWorker());
		message.setRecipients(recipients);
		//TODO poner salto de linea en html donde ponga \n
		final String subject = "Status of application changed. \n Estado de solicitud cambiado.";
		final String body = "Status of application about fix-up Task with ticker " + application.getFixUpTask().getTicker() + " has changed.\n El estado de la solicitud sobre la tarea con tikcer : " + application.getFixUpTask().getTicker()
			+ " ha cambiado.";
		message.setBody(body);
		message.setSubject(subject);
		message.setPriority("HIGH");
		this.messageService.save(message);
		return message;
	}

	public Collection<CreditCard> getCreditCardsByCustomerId(final int customerId) {

		return this.customerRepository.findCreditCardsByCustomerId(customerId);

	}

	//COMPLAINT-------------------------------------------------------------------

	//R35.1

	public Collection<Complaint> listCustomerComplaints() {

		//solo lo usan CUSTOMER
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.CUSTOMER));

		final Collection<Complaint> complaintPerUser = this.complaintService.getAllComplaintByCustomerId(this.findCustomerByPrincipal().getId());
		return complaintPerUser;

	}

	public Complaint showOne(final int complaintId) {
		Complaint result;

		//solo lo usan CUSTOMER
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.CUSTOMER));

		result = this.complaintService.findOne(complaintId);
		return result;
	}

	public Complaint createComplaint(final Complaint complaint) {

		//solo lo usan CUSTOMER
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.CUSTOMER));

		complaint.setCustomer(this.findCustomerByPrincipal());

		return this.complaintService.save(complaint);
	}

	//R35.2
	public void writeNoteReportByCustomer(final Customer customer, final Report report, final Note note) {

		Assert.notNull(customer);
		Assert.notNull(report);
		Assert.isTrue(customer.getId() != 0);
		//report en estado final
		Assert.isTrue(report.getIsFinal());
		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(customer.getUserAccount().equals(userAccount));

		final Collection<Report> reportList = this.customerRepository.findReportByCustomerId(customer.getId());
		Assert.isTrue(reportList.contains(report));

		Assert.notNull(note.getCustomerComment());
		//la nota no puede ser vacia
		Assert.isTrue(!note.getCustomerComment().equals(""));

		//solo puede ser un comentario del customer
		final Note oldNote = this.noteService.findOne(note.getId());
		if (note.getHandyWorkerComment() != null)
			Assert.isTrue(note.getHandyWorkerComment().equals(oldNote.getHandyWorkerComment()));
		if (note.getRefereeComment() != null)
			Assert.isTrue(note.getRefereeComment().equals(oldNote.getRefereeComment()));

		note.setReport(report);
		this.noteService.save(note);

	}

	//R35.3

	public void writeCommentNoteByCustomer(final Note note, final String customerComment, final Report report) {
		Assert.notNull(note);
		Assert.notNull(customerComment);
		Assert.notNull(report);
		Assert.isTrue(!customerComment.equals(""));
		//report en estado final
		Assert.isTrue(report.getIsFinal());

		//solo lo usan HandyWorker
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.CUSTOMER));

		//comprobar que en el report esta involucrado ese handy
		final Collection<Report> reportList = this.customerRepository.findReportByCustomerId(this.findCustomerByPrincipal().getId());
		Assert.isTrue(reportList.contains(report));
		Assert.isTrue(note.getReport().equals(report));

		note.setCustomerComment(customerComment);
		this.noteService.save(note);

	}

}
