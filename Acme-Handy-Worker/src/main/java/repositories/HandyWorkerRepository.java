
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.HandyWorker;
import domain.Report;
import domain.Tutorial;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	@Query("select hw from HandyWorker hw where hw.userAccount.id = ?1")
	HandyWorker findByUserAccountId(int userAccountId);

	@Query("select distinct c from Complaint c, FixUpTask f, Application a, HandyWorker hw where c.fixUpTask = f.id and a.fixUpTask = f.id and a.handyWorker=hw.id and hw.id = ?1")
	Collection<Complaint> displayComplaints(int userAccountId);

	@Query("select distinct r from Report r, Complaint c, FixUpTask f, Application a, HandyWorker hw where r.complaint = c.id and c.fixUpTask = f.id and a.fixUpTask = f.id and a.handyWorker=hw.id and hw.id = ?1")
	Collection<Report> displayComplaintsReport(int handyWorkerId);

	@Query("select a from Application a where a.handyWorker.id = ?1")
	Collection<Application> findAllApplicationsByHandyWorkerId(int handyWorkerId);

	@Query("select distinct c from Customer c, Application a, HandyWorker hw where a.status = 'ACCEPTED' and a.fixUpTask.customer=c.id and a.handyWorker = ?1")
	Collection<Customer> findRelatedCustomersByHandyWorkerId(int handyWorkerId);

	@Query("select t from Tutorial t where t.handyWorker.id=?1")
	Collection<Tutorial> getAllTutorialsByHandyId(int handyWorkerId);

}
