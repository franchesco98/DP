
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Phase;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	@Query("select f.customer from FixUpTask f where f.id = ?1")
	Customer findCustomerByFixUpTaskId(int fixUpTaskId);

	@Query("select p from Phase p where p.fixUpTask = ?1")
	Collection<Phase> findPhasesPerFixUpTask(int fixUpTaskId);

	@Query("select a.handyWorker from Application a where a.fixUpTask = ?1 and a.status = 'ACCEPTED'")
	HandyWorker findAcceptedHandyWorker(int fixUpTaskId);

}
