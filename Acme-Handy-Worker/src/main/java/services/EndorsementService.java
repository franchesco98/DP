
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import domain.Endorsement;

@Service
@Transactional
public class EndorsementService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private EndorsementRepository	endorsementRepository;


	// Constructors -----------------------------------------------------------

	public EndorsementService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Endorsement create() {
		Endorsement result;

		result = new Endorsement();

		return result;
	}

	public Collection<Endorsement> findAll() {
		Collection<Endorsement> result;

		result = this.endorsementRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Endorsement findOne(final int endorsementId) {
		Endorsement result;

		result = this.endorsementRepository.findOne(endorsementId);
		Assert.notNull(result);

		return result;
	}

	public Endorsement save(final Endorsement endorsement) {
		Assert.notNull(endorsement);

		Endorsement result;

		result = this.endorsementRepository.save(endorsement);

		return result;
	}

	public void delete(final Endorsement endorsement) {
		Assert.notNull(endorsement);
		Assert.isTrue(endorsement.getId() != 0);

		this.endorsementRepository.delete(endorsement);
	}

}
