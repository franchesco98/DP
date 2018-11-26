
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Complaint;
import domain.Customer;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class ComplaintService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ComplaintRepository	complaintRepository;


	// Constructors -----------------------------------------------------------

	public ComplaintService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Complaint create() {
		final Complaint result;
		final UserAccount userAccount = LoginService.getPrincipal();
		//Compruebo que en todo momento es un HW
		final Authority authority = new Authority();
		authority.setAuthority("Customer");
		Assert.isTrue(userAccount.getAuthorities().contains(authority), "Usted nos es un Customer, por favor inicie sesion de nuevo");
		result = new Complaint();

		return result;
	}

	//public Collection<Complaint> findAll() {
	//Collection<Complaint> result;

	//result = this.complaintRepository.findAll();
	//Assert.notNull(result);

	//return result;
	//}

	//Metodo de mostrar las complaints
	public Complaint showOne(final int complaintId) {
		Complaint result;

		final UserAccount userAccount = LoginService.getPrincipal();
		//Compruebo que en todo momento es un HW
		final Authority authority = new Authority();
		authority.setAuthority("customer");
		Assert.isTrue(userAccount.getAuthorities().contains(authority), "Usted nos es un Customer, por favor inicie sesion de nuevo");
		result = this.complaintRepository.findOne(complaintId);
		Assert.notNull(result);

		return result;
	}

	//Metodo de listas todas las complaints de un determinado customer

	public Collection<Complaint> listComplaint() {
		final Collection<Complaint> complaintList = new ArrayList<>();
		final int userAccountId = LoginService.getPrincipal().getId();
		final UserAccount userAccount = LoginService.getPrincipal();
		//Compruebo que en todo momento es un HW
		final Authority authority = new Authority();
		authority.setAuthority("customer");
		Assert.isTrue(userAccount.getAuthorities().contains(authority), "Usted no es un Customer, por favor inicie sesion de nuevo");
		final Collection<Complaint> complaintPerUser = this.complaintRepository.findAllComplaintByCustomerId(userAccountId);
		complaintList.addAll(complaintPerUser);
		return complaintList;

	}

	public Collection<Complaint> listComplaintReferee() {
		final Collection<Complaint> complaintList = new ArrayList<>();
		final int userAccountId = LoginService.getPrincipal().getId();
		final UserAccount userAccount = LoginService.getPrincipal();
		//Compruebo que en todo momento es un HW
		final Authority authority = new Authority();
		authority.setAuthority("referee");// es el refeerree un authority del sistema?
		Assert.isTrue(userAccount.getAuthorities().contains(authority), "Usted no es  un Referee, por favor inicie sesion de nuevo");
		final Collection<Complaint> complaintPerUser = this.complaintRepository.findAllComplaintByRefereeId(userAccountId);
		complaintList.addAll(complaintPerUser);
		return complaintList;

	}

	// hay que completarlo queda añadir la tarae de complaint al referee

	public Collection<Complaint> listComplaintSinReferee(final Referee referee, final Report report) {
		final Collection<Complaint> complaintList = new ArrayList<>();
		final int userAccountId = LoginService.getPrincipal().getId();
		final UserAccount userAccount = LoginService.getPrincipal();
		//Compruebo que en todo momento es un HW
		final Authority authority = new Authority();
		authority.setAuthority("referee");// es el refeerree un authority del sistema?
		Assert.isTrue(userAccount.getAuthorities().contains(authority), "Usted no es  un Referee, por favor inicie sesion de nuevo");
		final Collection<Complaint> withoutComplaint = this.complaintRepository.findAllComplaintWhithoutByRefeee();
		for (final Complaint complaint : withoutComplaint) {
			Assert.isTrue(report.getReferee().equals(referee));
			complaint.setReport(report);
			complaintList.add(complaint);
			withoutComplaint.remove(complaint);

			break;
		}

		return complaintList;

	}

	// como guardar esto para mandar a base de dTO
	// DONDE DEBO LLAMAR A ESTO
	// deberia estrçar todo esto dentro un if ????

	public Complaint save(final Complaint complaint) {
		Assert.notNull(complaint);

		Complaint result;

		result = this.complaintRepository.save(complaint);

		return result;
	}

	public void delete(final Complaint complaint) {
		Assert.notNull(complaint);
		Assert.isTrue(complaint.getId() != 0);

		this.complaintRepository.delete(complaint);
	}

	public Integer numComplaintByCustomer(final Customer customer) {

		final Integer result = this.complaintRepository.findAllComplaintByCustomerId(customer.getId()).size();
		return result;
	}

}
