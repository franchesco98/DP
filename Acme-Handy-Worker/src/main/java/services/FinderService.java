
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Finder;
import domain.FixUpTask;

@Service
@Transactional
public class FinderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FinderRepository	finderRepository;


	// Constructors -----------------------------------------------------------

	public FinderService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Finder create() {
		Finder result;

		result = new Finder();

		return result;
	}

	public Collection<Finder> findAll() {
		Collection<Finder> result;

		result = this.finderRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Finder findOne(final int finderId) {
		Finder result;

		result = this.finderRepository.findOne(finderId);
		Assert.notNull(result);

		return result;
	}

	public Finder save(final Finder finder) {
		Assert.notNull(finder);

		Finder result;

		result = this.finderRepository.save(finder);

		return result;
	}

	public void delete(final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);

		this.finderRepository.delete(finder);
	}

	//Other businnes methods

	public Collection<FixUpTask> findTasksByParams(final Finder finder) {
		final Collection<FixUpTask> f;

		if (finder.getPriceMin() == null && finder.getPriceMax() == null && finder.getDateMin() == null && finder.getDateMax() == null)
			f = this.finderRepository.findTasksByParamsWithoutPriceAndDate(finder.getKeyWord(), finder.getCategory(), finder.getWarranty());
		else if (finder.getDateMin() == null && finder.getDateMax() == null)
			f = this.finderRepository.findTasksByParamsWithoutDate(finder.getKeyWord(), finder.getCategory(), finder.getWarranty(), finder.getPriceMin(), finder.getPriceMax());
		else if (finder.getPriceMin() == null && finder.getPriceMax() == null)
			f = this.finderRepository.findTasksByParamsWithoutPrice(finder.getKeyWord(), finder.getCategory(), finder.getWarranty(), finder.getDateMin(), finder.getDateMax());
		else
			f = this.finderRepository.findTasksByParams(finder.getKeyWord(), finder.getCategory(), finder.getWarranty(), finder.getPriceMin(), finder.getPriceMax(), finder.getDateMin(), finder.getDateMax());
		return f;

	}
}
