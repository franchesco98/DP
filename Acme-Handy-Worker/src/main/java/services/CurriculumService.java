
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import domain.Curriculum;

@Service
@Transactional
public class CurriculumService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CurriculumRepository	curriculumRepository;


	// Constructors -----------------------------------------------------------

	public CurriculumService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Curriculum create() {
		Curriculum result;

		result = new Curriculum();

		return result;
	}

	public Collection<Curriculum> findAll() {
		Collection<Curriculum> result;

		result = this.curriculumRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Curriculum findOne(final int curriculumId) {
		Curriculum result;

		result = this.curriculumRepository.findOne(curriculumId);
		Assert.notNull(result);

		return result;
	}

	public Curriculum save(final Curriculum curriculum) {
		Assert.notNull(curriculum);

		Curriculum result;

		result = this.curriculumRepository.save(curriculum);

		return result;
	}

	public void delete(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		Assert.isTrue(curriculum.getId() != 0);

		this.curriculumRepository.delete(curriculum);
	}

}
