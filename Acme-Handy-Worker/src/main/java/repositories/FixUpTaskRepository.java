
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	@Query("select f.customer from FixUpTask f where f.id = ?1")
	Customer findCustomerByFixUpTaskId(int fixUpTaskId);

}
