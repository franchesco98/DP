
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Complaint;
import domain.Note;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class RefereeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RefereeRepository		refereeRepository;
	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private ComplaintService		complaintService;
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
	private ReportService			reportService;
	@Autowired
	private BoxService				boxService;


	// Constructors -----------------------------------------------------------

	public RefereeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Referee create() {
		Referee result;

		result = new Referee();
		//meterle el authority
		final UserAccount userAccount = new UserAccount();
		final Authority authotity = new Authority();
		authotity.setAuthority(Authority.REFEREE);
		final Collection<Authority> authorities = new ArrayList<>();
		authorities.add(authotity);
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);
		return result;
	}

	public Collection<Referee> findAll() {
		Collection<Referee> result;

		result = this.refereeRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Referee findOne(final int refereeId) {
		Referee result;

		result = this.refereeRepository.findOne(refereeId);
		Assert.notNull(result);

		return result;
	}

	public Referee save(final Referee referee) {
		Assert.notNull(referee);
		Assert.notNull(referee.getUserAccount().getUsername());
		Assert.notNull(referee.getUserAccount().getPassword());

		//comprobamos que no nos han dado cadenas vacias en los at opcionales
		if (referee.getMiddleName() != null)
			Assert.isTrue(!(referee.getMiddleName().trim().equals("")));

		if (referee.getAddress() != null)
			Assert.isTrue(!(referee.getAddress().trim().equals("")));

		final Referee result;

		if (referee.getId() != 0) {

			//comprobar que es el
			final UserAccount userAccount;
			userAccount = LoginService.getPrincipal();
			Assert.isTrue(referee.getUserAccount().equals(userAccount));
		} else {
			referee.setBoxes(this.boxService.originalBoxes());
			referee.getUserAccount().setAccountNonLocked(true);
			referee.setIsSuspicious(false);
		}

		result = this.refereeRepository.save(referee);
		return result;
	}

	public void delete(final Referee referee) {
		Assert.notNull(referee);
		Assert.isTrue(referee.getId() != 0);

		this.refereeRepository.delete(referee);
	}

	// Other business methods -------------------------------------------------

	public Referee findRefereeByPrincipal() {
		Referee result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Referee findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Referee result;

		result = this.refereeRepository.findRefereeByUserAccountId(userAccount.getId());

		return result;
	}

	//R36.1
	public Collection<Complaint> listComplaintwithoutReferee() {

		//solo lo usan REFEREE
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.REFEREE));

		final Collection<Complaint> complaintPerUser = this.complaintService.getAllComplaintWhithoutByRefeee();
		return complaintPerUser;

	}

	public Complaint selectComplaintByReferee(final Complaint complaint) {
		Assert.notNull(complaint);

		//solo lo usan REFEREE
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.REFEREE));

		complaint.setReferee(this.findRefereeByPrincipal());
		return this.complaintService.save(complaint);
	}

	//R36.2

	public Collection<Complaint> listComplaintByReferee() {

		//solo lo usan REFEREE
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.REFEREE));

		return this.complaintService.getAllComplaintByRefeee(this.findRefereeByPrincipal().getId());

	}

	//R36.3

	public Report writteReportOfRefereeComplaint(final Complaint complaint, final Report report) {

		Assert.notNull(complaint);
		Assert.notNull(report);
		Assert.isTrue(report.getComplaint().getReferee().equals(this.findRefereeByPrincipal()));

		return this.reportService.save(report);
	}

	//R36.4	Write a note regarding any of the reports that a referee has written regarding any of the complaints in which he or she is involved.

	public void writeNoteReportByReferee(final Referee referee, final Report report, final Note note) {

		Assert.notNull(referee);
		Assert.notNull(report);
		Assert.isTrue(referee.getId() != 0);
		//report en estado final
		Assert.isTrue(report.getIsFinal());

		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(referee.getUserAccount().equals(userAccount));

		final Collection<Report> reportList = this.refereeRepository.reportsByReferee(referee.getId());
		Assert.isTrue(reportList.contains(report));

		Assert.notNull(note.getRefereeComment());
		//la nota no puede ser vacia
		Assert.isTrue(!note.getRefereeComment().equals(""));

		//solo puede ser un comentario del handy
		final Note oldNote = this.noteService.findOne(note.getId());
		if (note.getCustomerComment() != null)
			Assert.isTrue(note.getCustomerComment().equals(oldNote.getCustomerComment()));
		if (note.getHandyWorkerComment() != null)
			Assert.isTrue(note.getHandyWorkerComment().equals(oldNote.getHandyWorkerComment()));
		note.setReport(report);
		this.noteService.save(note);

	}

	//R36.5

	public void writeCommentNoteByReferee(final Note note, final String refereeComment, final Report report) {
		Assert.notNull(note);
		Assert.notNull(refereeComment);
		Assert.notNull(report);
		Assert.isTrue(!refereeComment.equals(""));
		//report en estado final
		Assert.isTrue(report.getIsFinal());

		//solo lo usan Referee
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.REFEREE));

		//comprobar que en el report esta involucrado ese handy

		Assert.isTrue(report.getComplaint().getReferee().equals(this.findRefereeByPrincipal()));
		Assert.isTrue(note.getReport().equals(report));

		note.setRefereeComment(refereeComment);
		this.noteService.save(note);

	}

}
