
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import domain.EducationRecord;

@Service
@Transactional
public class EducationRecordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private EducationRecordRepository	educationRecordRepository;


	// Constructors -----------------------------------------------------------

	public EducationRecordService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public EducationRecord create() {
		EducationRecord result;

		result = new EducationRecord();

		return result;
	}

	public Collection<EducationRecord> findAll() {
		Collection<EducationRecord> result;

		result = this.educationRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public EducationRecord findOne(final int educationRecordId) {
		EducationRecord result;

		result = this.educationRecordRepository.findOne(educationRecordId);
		Assert.notNull(result);

		return result;
	}

	public EducationRecord save(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);

		EducationRecord result;

		result = this.educationRecordRepository.save(educationRecord);

		return result;
	}

	public void delete(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		Assert.isTrue(educationRecord.getId() != 0);

		this.educationRecordRepository.delete(educationRecord);
	}

}
