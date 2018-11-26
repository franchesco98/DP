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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;
import domain.Message;
import domain.Referee;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;

	// Business repository -----------------------------------------------------

	@Autowired
	private MessageService			messageService;
	private ActorService			actorService;
	private RefereeService			refereeService;


	// Constructors -----------------------------------------------------------

	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Administrator create(final Administrator admin) {
		int idPrincipal;
		idPrincipal = LoginService.getPrincipal().getId();
		Assert.isTrue(idPrincipal == admin.getId());

		Administrator result;
		result = new Administrator();

		final UserAccount userAccount = new UserAccount();
		final Authority authotity = new Authority();
		authotity.setAuthority(Authority.ADMIN);
		final Collection<Authority> authorities = new ArrayList<>();
		authorities.add(authotity);
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);
		return result;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Administrator findOne(final int administratorId) {
		Administrator result;

		result = this.administratorRepository.findOne(administratorId);
		Assert.notNull(result);

		return result;
	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);

		Administrator result;

		//				if (!(administrator.getId() == 0))
		//					final UserAccount customerAccont = administrator.getUserAccount();
		//					customerAccont.setUsername(username);
		//					customerAccont.setPassword(password);

		result = this.administratorRepository.save(administrator);

		return result;
	}
	public void delete(final Administrator administrator) {
		Assert.notNull(administrator);
		Assert.isTrue(administrator.getId() != 0);

		//lo que dijo de que teniamos que comprobar si el que borraba su cuenta era el de la cuenta
		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(administrator.getUserAccount().equals(userAccount));

		this.administratorRepository.delete(administrator);
	}

	// Other business methods -------------------------------------------------

	public void setMessageToAll(final Administrator administrator, final Message message) {
		Assert.notNull(administrator);
		Assert.isTrue(administrator.getId() != 0);

		//lo que dijo de que teniamos que comprobar si el que borraba su cuenta era el de la cuenta
		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(administrator.getUserAccount().equals(userAccount));
		//comprobamos si el administrador que envia el mensaje es el mismo que lo crea
		Assert.isTrue(message.getSender().getId() == administrator.getId());

		final Collection<Actor> allActors = this.actorService.findAll();

		message.setRecipients(allActors);

		this.messageService.save(message);

	}

	public Referee createNewReferee() {
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		final Referee res;
		res = this.refereeService.create();

		return res;

	}

	public Collection<Actor> listSuspiciousActor() {

		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		final Collection<Actor> suspiciousActors = this.administratorRepository.suspiciousActors();

		return suspiciousActors;

	}

	public void banActor(final Actor actor) {
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		if (actor.getIsSuspicious() == true)
			actor.setIsBanned(true);

		this.actorService.save(actor);
	}

	public void unbanActor(final Actor actor) {
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));
		if (actor.getIsBanned() == true)
			actor.setIsBanned(false);
		actor.setIsSuspicious(false);
		this.actorService.save(actor);
	}

	//Métodos de consultas

	public Collection<Double> ammsNumberFixUpTasksUser() {

		final Collection<Double> result = this.administratorRepository.ammsNumberFixUpTasksUser();

		return result;
	}

	public Collection<Double> ammsNumberApplicationsFixUpTask() {

		final Collection<Double> result = this.administratorRepository.ammsNumberApplicationsFixUpTask();

		return result;
	}

	public Collection<Double> ammsMaximumPriceFixUpTasks() {

		final Collection<Double> result = this.administratorRepository.ammsMaximumPriceFixUpTasks();

		return result;
	}

	public Collection<Double> ammsPriceOfferedApplications() {

		final Collection<Double> result = this.administratorRepository.ammsPriceOfferedApplications();

		return result;
	}

	public Double RatioPendingApplications() {

		Double result;

		result = this.administratorRepository.RatioPendingApplications();

		return result;
	}

	public Double RatioAcceptedApplications() {

		Double result;

		result = this.administratorRepository.RatioAcceptedApplications();

		return result;
	}

	public Double RatioRejectedApplications() {

		Double result;

		result = this.administratorRepository.RatioRejectedApplications();

		return result;
	}

	public Double RatioPendingCannotChangesStatus() {

		Double result;

		result = this.administratorRepository.RatioPendingApplications();

		return result;
	}

	public Collection<Customer> CustomerPublished10MoreFixUpTasks() {

		final Collection<Customer> result = this.administratorRepository.CustomerPublished10MoreFixUpTasks();

		return result;
	}
	public Collection<HandyWorker> handyPublished10MoreApplications() {

		final Collection<HandyWorker> result = this.administratorRepository.handyPublished10MoreApplications();

		return result;
	}
	public Collection<Double> ammsNumberNotesRefereeReport() {

		final Collection<Double> result = this.administratorRepository.ammsNumberNotesRefereeReport();

		return result;
	}

	public Double RatioFixUpTasksComplaint() {

		Double result;

		result = this.administratorRepository.RatioFixUpTasksComplaint();

		return result;
	}

	public Collection<Customer> TopThreeCustomersComplaints() {
		final Collection<Customer> result = this.administratorRepository.topThreeCustomersComplaints();

		return result;
	}
	public Collection<HandyWorker> TopThreeHandyWorkersComplaints() {
		final Collection<HandyWorker> result = this.administratorRepository.topThreeHandyWorkersComplaints();

		return result;
	}

}
