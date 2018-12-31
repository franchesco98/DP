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

import repositories.ComplaintRepository;
import domain.Complaint;
import domain.Report;

@Service
@Transactional
public class ComplaintService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ComplaintRepository		complaintRepository;
	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public ComplaintService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Complaint create() {
		Complaint result;

		result = new Complaint();

		return result;
	}

	public Collection<Complaint> findAll() {
		Collection<Complaint> result;

		result = this.complaintRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Complaint findOne(final int complaintId) {
		Complaint result;

		result = this.complaintRepository.findOne(complaintId);
		Assert.notNull(result);

		return result;
	}

	public Complaint save(final Complaint complaint) {
		Assert.notNull(complaint);

		Complaint result;

		result = this.complaintRepository.save(complaint);
		return result;

	}

	// Other business methods -------------------------------------------------

	public Collection<Complaint> getAllComplaintByCustomerId(final int customerId) {

		return this.complaintRepository.findAllComplaintByCustomerId(customerId);
	}

	public Collection<Complaint> getAllComplaintWhithoutByRefeee() {
		return this.complaintRepository.findAllComplaintWhithoutByRefeee();
	}

	public Collection<Complaint> getAllComplaintByRefeee(final int refereeId) {
		return this.complaintRepository.findAllComplaintByRefereeId(refereeId);
	}

	public Report getReportByComplaint(final int complaintId) {
		return this.complaintRepository.findReportByComplaint(complaintId);
	}

}
