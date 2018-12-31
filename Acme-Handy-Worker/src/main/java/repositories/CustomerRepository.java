
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.Complaint;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Phase;
import domain.Report;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.userAccount.id = ?1")
	Customer findByUserAccountId(int userAccountId);

	@Query("select f from FixUpTask f where f.customer.id = ?1")
	Collection<FixUpTask> findByCustomerId(int customerId);

	@Query("select a from Application a, FixUpTask f, Customer c where a.fixUpTask = f.id and f.customer.id = ?1")
	Collection<Application> findApplicationsByCustomerId(int customerId);

	@Query("select c from Complaint c  where c.fixUpTask.id= ?1")
	Collection<Complaint> findComplaintByFixUpTaskId(int fixUpTaskId);

	@Query("select a from Application a where a.fixUpTask.id = ?1")
	Collection<Application> findApplicationsByFixUpTaskId(int fixUpTaskId);

	@Query("select p from Phase p where p.fixUpTask.id = ?1")
	Collection<Phase> findPhaseByFixUpTaskId(int fixUpTaskId);

	@Query("select distinct hw from HandyWorker hw, Customer c, Application a, FixUpTask f where a.status = 'ACCEPTED' and a.fixUpTask.customer=c.id and c.id = ?1")
	Collection<HandyWorker> findRelatedHandyWorkersByCustomerId(int customerId);

	@Query("select cc  from CreditCard cc where cc.actor.id = ?1")
	Collection<CreditCard> findCreditCardsByCustomerId(int customerId);

	@Query("select r from Report r where r.complaint.customer.id = ?1")
	Collection<Report> findReportByCustomerId(int customerId);

}
