
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PersonalRecordRepository	personalRecordRepository;


	// Constructors -----------------------------------------------------------

	public PersonalRecordService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public PersonalRecord create() {
		PersonalRecord result;

		result = new PersonalRecord();

		return result;
	}

	public Collection<PersonalRecord> findAll() {
		Collection<PersonalRecord> result;

		result = this.personalRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public PersonalRecord findOne(final int personalRecordId) {
		PersonalRecord result;

		result = this.personalRecordRepository.findOne(personalRecordId);
		Assert.notNull(result);

		return result;
	}

	public PersonalRecord save(final PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);

		PersonalRecord result;

		result = this.personalRecordRepository.save(personalRecord);

		return result;
	}

	public void delete(final PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);
		Assert.isTrue(personalRecord.getId() != 0);

		this.personalRecordRepository.delete(personalRecord);
	}

}
