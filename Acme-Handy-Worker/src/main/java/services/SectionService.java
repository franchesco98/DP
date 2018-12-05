
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.Section;

@Service
@Transactional
public class SectionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SectionRepository	sectionRepository;


	// Constructors -----------------------------------------------------------

	public SectionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Section create() {
		Section result;

		result = new Section();

		return result;
	}

	public Collection<Section> findAll() {
		Collection<Section> result;

		result = this.sectionRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Section findOne(final int sectionId) {
		Section result;

		result = this.sectionRepository.findOne(sectionId);
		Assert.notNull(result);

		return result;
	}

	public Section save(final Section section) {
		Assert.notNull(section);

		Section result;

		result = this.sectionRepository.save(section);

		return result;
	}

	public void delete(final Section section) {
		Assert.notNull(section);
		Assert.isTrue(section.getId() != 0);

		this.sectionRepository.delete(section);
	}

}
