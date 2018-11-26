
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.Customer;
import domain.FixUpTask;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.userAccount.id = ?1")
	Customer findByUserAccountId(int userAccountId);

	@Query("select f from FixUpTask f where f.customer = ?1")
	Collection<FixUpTask> findByCustomerId(int customerId);

	@Query("select a from Application a, FixUpTask f, Customer c where a.fixUpTask = f.id and f.customer = ?1")
	Collection<Application> findApplicationsByCustomerId(int customerId);

}
