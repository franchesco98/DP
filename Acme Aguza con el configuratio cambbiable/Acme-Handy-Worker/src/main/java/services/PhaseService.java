
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PhaseRepository	phaseRepository;


	// Constructors -----------------------------------------------------------

	public PhaseService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Phase create() {
		Phase result;

		result = new Phase();

		return result;
	}

	public Collection<Phase> findAll() {
		Collection<Phase> result;

		result = this.phaseRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Phase findOne(final int phaseId) {
		Phase result;

		result = this.phaseRepository.findOne(phaseId);
		Assert.notNull(result);

		return result;
	}

	public Phase save(final Phase phase) {
		Assert.notNull(phase);

		Phase result;

		result = this.phaseRepository.save(phase);

		return result;
	}

	public void delete(final Phase phase) {
		Assert.notNull(phase);
		Assert.isTrue(phase.getId() != 0);

		this.phaseRepository.delete(phase);
	}

}
