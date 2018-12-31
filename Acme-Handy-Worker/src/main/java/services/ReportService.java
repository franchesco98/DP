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

import repositories.ReportRepository;
import security.Authority;
import security.LoginService;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class ReportService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ReportRepository	reportRepository;

	@Autowired
	private RefereeService		refereeService;


	// Constructors -----------------------------------------------------------

	public ReportService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Report create() {
		Report result;

		result = new Report();
		return result;
	}

	public Collection<Report> findAll() {
		Collection<Report> result;

		result = this.reportRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Report findOne(final int reportId) {
		Report result;

		result = this.reportRepository.findOne(reportId);
		Assert.notNull(result);

		return result;
	}

	public Report save(final Report report) {
		Assert.notNull(report);

		Report result;

		final Referee referee = this.refereeService.findByUserAccount(LoginService.getPrincipal());
		//el principal debe ser  un referee
		final Authority customerAuthority = new Authority();
		customerAuthority.setAuthority("REFEREE");
		Assert.isTrue(referee.getUserAccount().getAuthorities().contains(customerAuthority));
		//el referee que guarda el report debe ser el que gestiona el complaint
		Assert.isTrue(report.getComplaint().getReferee().equals(referee));

		if (report.getId() != 0) {
			//un report solo se puede actualizar si esta en modo borrador
			final Report oldReport = this.findOne(report.getId());
			Assert.isTrue(!oldReport.getIsFinal());
		}

		result = this.reportRepository.save(report);
		return result;

	}

	public void delete(final Report report) {
		Assert.notNull(report);
		Assert.isTrue(report.getId() != 0);
		final Referee referee = this.refereeService.findByUserAccount(LoginService.getPrincipal());
		//el principal debe ser  un referee
		final Authority customerAuthority = new Authority();
		customerAuthority.setAuthority("REFEREE");
		Assert.isTrue(referee.getUserAccount().getAuthorities().contains(customerAuthority));

		//un report solo se puede eliminar si esta en modo borrador
		Assert.isTrue(!report.getIsFinal());

		this.reportRepository.delete(report);
	}
	// Other business methods -------------------------------------------------

}
