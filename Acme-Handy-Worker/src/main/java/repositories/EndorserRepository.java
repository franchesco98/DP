
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Endorsement;
import domain.Endorser;

@Repository
public interface EndorserRepository extends JpaRepository<Endorser, Integer> {

	@Query("select e from Endorsement e where e.recipient = ?1 or e.sender = ?1")
	Collection<Endorsement> findEndorsementsByEndorserId(int endorserId);

}
