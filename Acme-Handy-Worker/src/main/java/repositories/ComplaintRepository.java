
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;
import domain.Report;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	@Query("select co from Complaint co where co.customer.id = ?1")
	Collection<Complaint> findAllComplaintByCustomerId(int customerId);

	@Query("select co from Complaint co where co.referee.id = ?1")
	Collection<Complaint> findAllComplaintByRefereeId(int refereeId);

	@Query("select co from Complaint co where co.referee.id = null")
	Collection<Complaint> findAllComplaintWhithoutByRefeee();

	@Query("select r from Report r where r.complaint.id = ?1")
	Report findReportByComplaint(int complaintId);

	@Query("select co from Complaint co where co.ticker = ?1")
	Complaint findComplaintByTicker(String ticker);

}
