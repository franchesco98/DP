
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	@Query("select co from Complaint co where co.customer.id = ?1")
	Collection<Complaint> findAllComplaintByCustomerId(int customerId);

	@Query("select co from Complaint co where co.referee.id = ?2")
	Collection<Complaint> findAllComplaintByRefereeId(int userAccountId);

	@Query("select co from Complaint co where not exists (select co.referee from Complaint co)")
	Collection<Complaint> findAllComplaintWhithoutByRefeee();
}
