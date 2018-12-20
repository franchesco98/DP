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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Administrator;
import domain.Category;
import domain.Configuration;
import domain.Customer;
import domain.Endorsement;
import domain.HandyWorker;
import domain.Message;
import domain.Referee;
import domain.Warranty;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;

	// Business repository -----------------------------------------------------

	@Autowired
	private MessageService			messageService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private WarrantyService			warrantyService;
	@Autowired
	private CategoryService			categoryService;
	@Autowired
	private RefereeService			refereeService;
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private EndorserService			endorserService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private BoxService				boxService;


	// Constructors -----------------------------------------------------------

	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Administrator create() {

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
		Assert.notNull(administrator.getUserAccount().getUsername());
		Assert.notNull(administrator.getUserAccount().getPassword());

		//comprobamos que no nos han dado cadenas vacias en los at opcionales
		if (administrator.getMiddleName() != null)
			Assert.isTrue(!(administrator.getMiddleName().trim().equals("")));

		if (administrator.getAddress() != null)
			Assert.isTrue(!(administrator.getAddress().trim().equals("")));

		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Administrator result;

		if (!(administrator.getId() == 0)) {
			// comprobamos que el que la crear es un admin
			Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

			administrator.setBoxes(this.boxService.originalBoxes());
			administrator.getUserAccount().setAccountNonLocked(true);
			administrator.setIsSuspicious(false);
		} else
			//comprobamos que es su cuenta
			Assert.isTrue(administrator.getUserAccount().equals(userAcount));

		result = this.administratorRepository.save(administrator);

		return result;
	}

	public void delete(final Administrator administrator) {
		Assert.notNull(administrator);
		Assert.isTrue(administrator.getId() != 0);

		//si el que borra su cuenta era el de la cuenta
		final UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(administrator.getUserAccount().equals(userAccount));

		this.administratorRepository.delete(administrator);
	}

	// Other business methods -------------------------------------------------

	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public Administrator findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Administrator result;

		result = this.administratorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	//R12.2

	public Collection<Warranty> listAllWarrantiesByAdmin() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		return this.warrantyService.findAll();
	}

	public Warranty showWarrantyByAdminforId(final int warrantyId) {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		return this.warrantyService.findOne(warrantyId);
	}

	//R12.3

	public Collection<Category> listAllCategoriesByAdmin() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		return this.categoryService.findAll();
	}

	public Category showCategoryByAdminforId(final int CategoryId) {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		return this.categoryService.findOne(CategoryId);
	}

	//R 12.4

	public void messageToAll(final Message message) {
		Assert.notNull(message);

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		//metemos en el sender el admin
		final Actor adminCreator = this.actorService.actorByUserAccount(userAcount);
		message.setSender(adminCreator);

		final Collection<Actor> allActors = this.actorService.findAll();
		//borramos a el mismo
		allActors.remove(adminCreator);
		message.setRecipients(allActors);

		this.messageService.save(message);
	}

	//R38.1
	public Referee createNewReferee() {
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		final Referee newR = this.refereeService.create();
		return this.refereeService.save(newR);
	}

	//R38.2
	public Collection<Actor> listSuspiciousActor() {

		//solo pueden usarlo admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		final Collection<Actor> suspiciousActors = this.administratorRepository.suspiciousActors();

		return suspiciousActors;

	}

	//38.3
	public void banActor(final Actor actor) {
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));
		Assert.isTrue(actor.getIsSuspicious());

		actor.getUserAccount().setAccountNonLocked(false);

		this.actorService.save(actor);
	}
	//TODO mirar lo de isAccountNonLocked
	//38.4
	public void unbanActor(final Actor actor) {

		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));
		Assert.isTrue(!actor.getUserAccount().isAccountNonLocked());

		actor.setIsSuspicious(false);
		actor.getUserAccount().setAccountNonLocked(true);
		this.actorService.save(actor);
	}

	//R12.5

	public Collection<Double> ammsNumberFixUpTasksUser() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		final Collection<Double> result = this.administratorRepository.ammsNumberFixUpTasksUser();

		return result;
	}

	public Collection<Double> ammsNumberApplicationsFixUpTask() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		final Collection<Double> result = this.administratorRepository.ammsNumberApplicationsFixUpTask();

		return result;
	}

	public Collection<Double> ammsMaximumPriceFixUpTasks() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		final Collection<Double> result = this.administratorRepository.ammsMaximumPriceFixUpTasks();

		return result;
	}

	public Collection<Double> ammsPriceOfferedApplications() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		final Collection<Double> result = this.administratorRepository.ammsPriceOfferedApplications();

		return result;
	}

	public Double RatioPendingApplications() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		Double result;

		result = this.administratorRepository.RatioPendingApplications();

		return result;
	}

	public Double RatioAcceptedApplications() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		Double result;

		result = this.administratorRepository.RatioAcceptedApplications();

		return result;
	}

	public Double RatioRejectedApplications() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		Double result;

		result = this.administratorRepository.RatioRejectedApplications();

		return result;
	}

	public Double RatioPendingCannotChangesStatus() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		Double result;

		result = this.administratorRepository.RatioPendingApplications();

		return result;
	}

	public Collection<Customer> CustomerPublished10MoreFixUpTasks() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		final Collection<Customer> result = this.administratorRepository.CustomerPublished10MoreFixUpTasks();

		return result;
	}
	public Collection<HandyWorker> handyPublished10MoreApplications() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		final Collection<HandyWorker> result = this.administratorRepository.handyPublished10MoreApplications();

		return result;
	}

	//38.5
	public Collection<Double> ammsNumberComplaintsFixUpTasks() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		final Collection<Double> result = this.administratorRepository.ammsNumberComplaintsFixUpTasks();

		return result;
	}
	public Collection<Double> ammsNumberNotesRefereeReport() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		final Collection<Double> result = this.administratorRepository.ammsNumberNotesRefereeReport();

		return result;
	}

	public Double RatioFixUpTasksComplaint() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		Double result;

		result = this.administratorRepository.RatioFixUpTasksComplaint();

		return result;
	}

	public Collection<Customer> TopThreeCustomersComplaints() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		final Pageable pageable = new PageRequest(0, 3);
		final List<Customer> result = this.administratorRepository.topThreeCustomersComplaints(pageable);

		return result;
	}
	public Collection<HandyWorker> TopThreeHandyWorkersComplaints() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		final Pageable pageable = new PageRequest(0, 3);
		final List<HandyWorker> result = this.administratorRepository.topThreeHandyWorkersComplaints(pageable);

		return result;
	}

	//R50.1

	//sumo positivas-negativas/total

	public void computedScore() {

		//ver que el principal sea un admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		//cogemos las palabras positivas y negativas en ambos idiomas
		final List<Configuration> aux = new ArrayList<>(this.configurationService.findAll());
		final Configuration configuration = aux.get(0);
		final String[] positiveWordsE = configuration.getPositiveWordsE().split(",");
		final String[] negativeWordsE = configuration.getNegativeWordsE().split(",");
		final String[] positiveWordsS = configuration.getPositiveWordsS().split(",");
		final String[] negativeWordsS = configuration.getNegativeWordsS().split(",");

		//para el customer		
		final Collection<Customer> allCustomers = this.customerService.findAll();

		for (final Customer customer : allCustomers) {

			final Collection<Endorsement> endorsementsRecived = this.endorserService.findEndorsementsRecipientByEndorserId(customer);

			Double countPositiveWordE = 0.0;
			Double countNegativeWordE = 0.0;
			Double countPositiveWordsS = 0.0;
			Double countNegativeWordsS = 0.0;

			for (final Endorsement endorsement : endorsementsRecived) {
				//comentarios de un endorsement
				final String[] comments = endorsement.getComments().split(";;");

				//compruebo las negative/positive words de cada coment
				for (final String comment : comments) {
					for (final String positiveWordE : positiveWordsE)
						if (comment.contains(positiveWordE.trim()))
							countPositiveWordE++;
					for (final String negativeWordE : negativeWordsE)
						if (comment.contains(negativeWordE.trim()))
							countNegativeWordE++;
					for (final String positiveWordS : positiveWordsS)
						if (comment.contains(positiveWordS.trim()))
							countPositiveWordsS++;
					for (final String negativeWordS : negativeWordsS)
						if (comment.contains(negativeWordS.trim()))
							countNegativeWordsS++;
				}
			}

			final Double score = (countPositiveWordE + countPositiveWordsS) - (countNegativeWordE + countNegativeWordsS) / (countPositiveWordE + countPositiveWordsS + countNegativeWordE + countNegativeWordsS);
			customer.setScore(score);
			this.endorserService.save(customer);
			this.customerService.save(customer);
		}

		//para el handy
		final Collection<HandyWorker> allHandy = this.handyWorkerService.findAll();

		for (final HandyWorker handy : allHandy) {

			final Collection<Endorsement> endorsementsRecived = this.endorserService.findEndorsementsRecipientByEndorserId(handy);

			Double countPositiveWordE = 0.0;
			Double countNegativeWordE = 0.0;
			Double countPositiveWordsS = 0.0;
			Double countNegativeWordsS = 0.0;

			for (final Endorsement endorsement : endorsementsRecived) {
				//comentarios de un endorsement
				final String[] comments = endorsement.getComments().split(";;");

				//compruebo las negative/positive words de cada coment
				for (final String comment : comments) {
					for (final String positiveWordE : positiveWordsE)
						if (comment.contains(positiveWordE.trim()))
							countPositiveWordE++;
					for (final String negativeWordE : negativeWordsE)
						if (comment.contains(negativeWordE.trim()))
							countNegativeWordE++;
					for (final String positiveWordS : positiveWordsS)
						if (comment.contains(positiveWordS.trim()))
							countPositiveWordsS++;
					for (final String negativeWordS : negativeWordsS)
						if (comment.contains(negativeWordS.trim()))
							countNegativeWordsS++;
				}
			}

			final Double score = (countPositiveWordE + countPositiveWordsS) - (countNegativeWordE + countNegativeWordsS) / (countPositiveWordE + countPositiveWordsS + countNegativeWordE + countNegativeWordsS);
			handy.setScore(score);
			this.endorserService.save(handy);
			this.handyWorkerService.save(handy);
		}

	}

	//R50.2

	//listing
	//English
	public List<String> listPosivieWordE() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		//coger el configuration
		final List<Configuration> aux = new ArrayList<>(this.configurationService.findAll());
		final Configuration configuration = aux.get(0);

		final String[] positiveWordsE = configuration.getPositiveWordsE().split(",");
		final List<String> res = new ArrayList<>();
		for (final String word : positiveWordsE)
			res.add(word.trim());

		return res;

	}

	public List<String> listNegativeWordsE() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		//coger el configuration
		final List<Configuration> aux = new ArrayList<>(this.configurationService.findAll());
		final Configuration configuration = aux.get(0);

		final String[] negativeWordsE = configuration.getNegativeWordsE().split(",");
		final List<String> res = new ArrayList<>();
		for (final String word : negativeWordsE)
			res.add(word.trim());

		return res;

	}

	//spanish

	public List<String> listPosivieWordS() {

		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		//coger el configuration
		final List<Configuration> aux = new ArrayList<>(this.configurationService.findAll());
		final Configuration configuration = aux.get(0);

		final String[] positiveWordsS = configuration.getPositiveWordsS().split(",");
		final List<String> res = new ArrayList<>();
		for (final String word : positiveWordsS)
			res.add(word.trim());

		return res;

	}

	public List<String> listNegativeWordsS() {

		//ver que el principal sea un admin
		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		//coger la configuracion
		final List<Configuration> aux = new ArrayList<>(this.configurationService.findAll());
		final Configuration configuration = aux.get(0);

		final String[] negativeWordsS = configuration.getNegativeWordsS().split(",");
		final List<String> res = new ArrayList<>();
		for (final String word : negativeWordsS)
			res.add(word.trim());

		return res;

	}

	//updating

	//english	

	//	public String updatePosiveWordE(final String newWord) {
	//
	//		Assert.notNull(newWord);
	//
	//		//ver que el principal sea un admin
	//		//solo lo usan admin
	//		UserAccount userAcount;
	//		userAcount = LoginService.getPrincipal();
	//		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));
	//
	//		//coger la configuracion
	//		final List<Configuration> aux = new ArrayList<>(this.configurationService.findAll());
	//		final Configuration configuration = aux.get(0);
	//
	//		//ponemos la newWord en el formato correcto
	//		newWord.replaceAll(",", "");
	//		newWord.trim();
	//
	//		final String res = configuration.getPositiveWordsE() + "," + newWord;
	//		//caso en el que la lista este vacia
	//		if (configuration.getNegativeWordsS().isEmpty()) {
	//			res.replaceAll(",", "");
	//		}
	//		configuration.setPositiveWordsE(res);
	//		this.configurationService.save(configuration);
	//
	//		return res;
	//	}
	//
	//	public String updateNegativeWordE(final String newWord) {
	//
	//		Assert.notNull(newWord);
	//
	//		//ver que el principal sea un admin
	//		//solo lo usan admin
	//		UserAccount userAcount;
	//		userAcount = LoginService.getPrincipal();
	//		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));
	//
	//		//coger la configuracion
	//		final List<Configuration> aux = new ArrayList<>(this.configurationService.findAll());
	//		final Configuration configuration = aux.get(0);
	//
	//		//ponemos la newWord en el formato correcto
	//		newWord.replaceAll(",", "");
	//		newWord.trim();
	//
	//		final String res = configuration.getNegativeWordsE() + "," + newWord;
	//		//caso en el que la lista este vacia
	//		if (configuration.getNegativeWordsS().isEmpty()) {
	//			res.replaceAll(",", "");
	//		}
	//		configuration.setNegativeWordsE(res);
	//		this.configurationService.save(configuration);
	//
	//		return res;
	//	}
	//
	//	//spanish
	//
	//	public String updatePositiveWordS(final String newWord) {
	//
	//		Assert.notNull(newWord);
	//
	//		//ver que el principal sea un admin
	//		//solo lo usan admin
	//		UserAccount userAcount;
	//		userAcount = LoginService.getPrincipal();
	//		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));
	//
	//		//coger la configuracion
	//		final List<Configuration> aux = new ArrayList<>(this.configurationService.findAll());
	//		final Configuration configuration = aux.get(0);
	//
	//		//ponemos la newWord en el formato correcto
	//		newWord.replaceAll(",", "");
	//		newWord.trim();
	//
	//		final String res = configuration.getPositiveWordsS() + "," + newWord;
	//		//caso en el que la lista este vacia
	//		if (configuration.getNegativeWordsS().isEmpty()) {
	//			res.replaceAll(",", "");
	//		}
	//		configuration.setPositiveWordsS(res);
	//		this.configurationService.save(configuration);
	//
	//		return res;
	//	}
	//
	//	public String updateNegativeWordS(final String newWord) {
	//
	//		Assert.notNull(newWord);
	//
	//		//ver que el principal sea un admin
	//		//solo lo usan admin
	//		UserAccount userAcount;
	//		userAcount = LoginService.getPrincipal();
	//		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));
	//
	//		//coger la configuracion
	//		final List<Configuration> aux = new ArrayList<>(this.configurationService.findAll());
	//		final Configuration configuration = aux.get(0);
	//
	//		//ponemos la newWord en el formato correcto
	//		newWord.replaceAll(",", "");
	//		newWord.trim();
	//
	//		final String res = configuration.getNegativeWordsS() + "," + newWord;
	//		//caso en el que la lista este vacia
	//		if (configuration.getNegativeWordsS().isEmpty()) {
	//			res.replaceAll(",", "");
	//		}
	//		configuration.setNegativeWordsS(res);
	//		this.configurationService.save(configuration);
	//
	//		return res;
	//	}

	//	//deleting
	//	//english	
	//
	//	public String deletePosiveWordE(final String deleteWord) {
	//
	//		Assert.notNull(deleteWord);
	//
	//		//ver que el principal sea un admin
	//		//solo lo usan admin
	//		UserAccount userAcount;
	//		userAcount = LoginService.getPrincipal();
	//		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));
	//
	//		//coger la configuracion
	//		final List<Configuration> aux = new ArrayList<>(this.configurationService.findAll());
	//		final Configuration configuration = aux.get(0);
	//
	//		//ponemos la deleteWord en el formato correcto
	//		deleteWord.replaceAll(",", "");
	//		deleteWord.trim();
	//
	//		final String res = configuration.getPositiveWordsE().replaceAll(deleteWord, "DELETE");
	//		res.replaceAll(",DELETE,", ",");
	//		res.replaceAll("DELETE,", "");
	//		res.replaceAll(",DELETE", "");
	//		configuration.setPositiveWordsE(res);
	//		this.configurationService.save(configuration);
	//
	//		return res;
	//	}
	//
	//	public String deleteNegativeWordE(final String deleteWord) {
	//
	//		Assert.notNull(deleteWord);
	//
	//		//ver que el principal sea un admin
	//		//solo lo usan admin
	//		UserAccount userAcount;
	//		userAcount = LoginService.getPrincipal();
	//		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));
	//
	//		//coger la configuracion
	//		final List<Configuration> aux = new ArrayList<>(this.configurationService.findAll());
	//		final Configuration configuration = aux.get(0);
	//
	//		//ponemos la deleteWord en el formato correcto
	//		deleteWord.replaceAll(",", "");
	//		deleteWord.trim();
	//
	//		final String res = configuration.getNegativeWordsE().replaceAll(deleteWord, "DELETE");
	//		res.replaceAll(",DELETE,", ",");
	//		res.replaceAll("DELETE,", "");
	//		res.replaceAll(",DELETE", "");
	//		configuration.setNegativeWordsE(res);
	//		this.configurationService.save(configuration);
	//
	//		return res;
	//	}
	//
	//	//Spanish
	//
	//	public String deletePosiveWordS(final String deleteWord) {
	//
	//		Assert.notNull(deleteWord);
	//
	//		//ver que el principal sea un admin
	//		//solo lo usan admin
	//		UserAccount userAcount;
	//		userAcount = LoginService.getPrincipal();
	//		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));
	//
	//		//coger la configuracion
	//		final List<Configuration> aux = new ArrayList<>(this.configurationService.findAll());
	//		final Configuration configuration = aux.get(0);
	//
	//		//ponemos la deleteWord en el formato correcto
	//		deleteWord.replaceAll(",", "");
	//		deleteWord.trim();
	//
	//		final String res = configuration.getPositiveWordsS().replaceAll(deleteWord, "DELETE");
	//		res.replaceAll(",DELETE,", ",");
	//		res.replaceAll("DELETE,", "");
	//		res.replaceAll(",DELETE", "");
	//		configuration.setPositiveWordsS(res);
	//		this.configurationService.save(configuration);
	//
	//		return res;
	//	}

	//	public String deleteNegativeWordS(final String deleteWord) {
	//
	//		Assert.notNull(deleteWord);
	//
	//		//ver que el principal sea un admin
	//		//solo lo usan admin
	//		UserAccount userAcount;
	//		userAcount = LoginService.getPrincipal();
	//		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));
	//
	//		//coger la configuracion
	//		final List<Configuration> aux = new ArrayList<>(this.configurationService.findAll());
	//		final Configuration configuration = aux.get(0);
	//
	//		//ponemos la deleteWord en el formato correcto
	//		deleteWord.replaceAll(",", "");
	//		deleteWord.trim();
	//
	//		final String res = configuration.getNegativeWordsS().replaceAll(deleteWord, "DELETE");
	//		res.replaceAll(",DELETE,", ",");
	//		res.replaceAll("DELETE,", "");
	//		res.replaceAll(",DELETE", "");
	//		configuration.setNegativeWordsS(res);
	//		this.configurationService.save(configuration);
	//
	//		return res;
	//	}

	public Configuration updateConfigurationNoListString(final Configuration configuration) {

		//solo se llama a este metodo directamente si se editan atributos que no son string de una lista de palabras

		//ver que el principal sea un admin

		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		//coger la configuracion antigua
		final List<Configuration> aux = new ArrayList<>(this.configurationService.findAll());
		final Configuration configurationOld = aux.get(0);

		//no se puede modificar este tipo de Atributos
		configuration.setPositiveWordsE(configurationOld.getPositiveWordsE());
		configuration.setPositiveWordsE(configurationOld.getPositiveWordsS());
		configuration.setPositiveWordsE(configurationOld.getNegativeWordsE());
		configuration.setPositiveWordsE(configurationOld.getNegativeWordsS());
		configuration.setPositiveWordsE(configurationOld.getCreditCardMakes());
		configuration.setPositiveWordsE(configurationOld.getSpamWords());

		return this.configurationService.save(configuration);
	}

	public Configuration updateCredicCardMakes(final Configuration configuration) {

		Assert.notNull(configuration);

		final String credicCardMakes = configuration.getCreditCardMakes();
		//comprobamos que la cadena sigue el patron adecuado
		Assert.isTrue(!credicCardMakes.trim().equals(""));
		if (credicCardMakes.contains(","))
			Assert.isTrue(!credicCardMakes.replaceAll(",", "").trim().equals(""));
		//ver que el principal sea un admin
		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		return this.configurationService.save(configuration);
	}

	public Configuration updateSpamWords(final Configuration configuration) {

		Assert.notNull(configuration);

		final String spamWords = configuration.getSpamWords();
		//comprobamos que la cadena sigue el patron adecuado
		Assert.isTrue(!spamWords.trim().equals(""));
		if (spamWords.contains(","))
			Assert.isTrue(!spamWords.replaceAll(",", "").trim().equals(""));
		//ver que el principal sea un admin
		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		return this.configurationService.save(configuration);
	}

	public Configuration updateNegativeWordsS(final Configuration configuration) {

		Assert.notNull(configuration);

		final String negativeWordsS = configuration.getNegativeWordsS();
		//comprobamos que la cadena sigue el patron adecuado
		Assert.isTrue(!negativeWordsS.trim().equals(""));
		if (negativeWordsS.contains(","))
			Assert.isTrue(!negativeWordsS.replaceAll(",", "").trim().equals(""));
		//ver que el principal sea un admin
		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		return this.configurationService.save(configuration);
	}

	public Configuration updateNegativeWordsE(final Configuration configuration) {

		Assert.notNull(configuration);

		final String negativeWordsE = configuration.getNegativeWordsE();
		//comprobamos que la cadena sigue el patron adecuado
		Assert.isTrue(!negativeWordsE.trim().equals(""));
		if (negativeWordsE.contains(","))
			Assert.isTrue(!negativeWordsE.replaceAll(",", "").trim().equals(""));
		//ver que el principal sea un admin
		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		return this.configurationService.save(configuration);
	}

	public Configuration updatePositiveWordsS(final Configuration configuration) {

		Assert.notNull(configuration);

		final String positiveWordsS = configuration.getPositiveWordsS();
		//comprobamos que la cadena sigue el patron adecuado
		Assert.isTrue(!positiveWordsS.trim().equals(""));
		if (positiveWordsS.contains(","))
			Assert.isTrue(!positiveWordsS.replaceAll(",", "").trim().equals(""));
		//ver que el principal sea un admin
		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		return this.configurationService.save(configuration);
	}

	public Configuration updatePositiveWordsE(final Configuration configuration) {

		Assert.notNull(configuration);

		final String positiveWordsE = configuration.getPositiveWordsE();
		//comprobamos que la cadena sigue el patron adecuado
		Assert.isTrue(!positiveWordsE.trim().equals(""));
		if (positiveWordsE.contains(","))
			Assert.isTrue(!positiveWordsE.replaceAll(",", "").trim().equals(""));
		//ver que el principal sea un admin
		//solo lo usan admin
		UserAccount userAcount;
		userAcount = LoginService.getPrincipal();
		Assert.isTrue(userAcount.getAuthorities().contains(Authority.ADMIN));

		return this.configurationService.save(configuration);
	}

}
