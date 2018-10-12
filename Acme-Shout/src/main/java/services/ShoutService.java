
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ShoutRepository;
import domain.Shout;

@Service
@Transactional
public class ShoutService {

	@Autowired
	private ShoutRepository	shoutRepository;


	public Collection<Shout> findAll() {

		Collection<Shout> result;
		result = this.shoutRepository.findAll();

		return result;
	}
}
