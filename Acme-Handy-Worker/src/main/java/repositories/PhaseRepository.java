
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Phase;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Integer> {

	@Query("select distinct p from Phase p, Application a, HandyWorker hw where p.fixUpTask.id=a.fixUpTask.id and a.handyWorker.id = hw.id and hw.userAccount.id = ?1")
	Collection<Phase> findPhasesOfHW(int userId);

}
