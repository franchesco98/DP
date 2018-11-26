
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByUserAccountId(int userAccountId);

	@Query("select avg(1.0*(select count(f.customer) from FixUpTask f where f.customer=c.id)), min(1*(select count(f.customer) from FixUpTask f where f.customer=c.id)), max(1*(select count(f.customer) from FixUpTask f where f.customer=c.id)), stddev(1.0*(select count(f.customer) from FixUpTask f where f.customer=c.id)) from Customer c")
	Collection<Double> ammsNumberFixUpTasksUser();

	@Query("select avg(1.0*(select count(a.fixUpTask) from Application a where a.fixUpTask=f.id)), min(1*(select count(a.fixUpTask) from Application a where a.fixUpTask=f.id)), max(1*(select count(a.fixUpTask) from Application a where a.fixUpTask=f.id)), stddev(1.0*(select count(a.fixUpTask) from Application a where a.fixUpTask=f.id)) from FixUpTask f")
	Collection<Double> ammsNumberApplicationsFixUpTask();

	@Query("select avg(f.maxPrice),min(f.maxPrice),max(f.maxPrice),stddev(f.maxPrice) from FixUpTask f")
	Collection<Double> ammsMaximumPriceFixUpTasks();

	@Query("select avg(a.offeredPrice),min(a.offeredPrice),max(a.offeredPrice),stddev(a.offeredPrice) from Application a")
	Collection<Double> ammsPriceOfferedApplications();

	@Query("select ((count(a)*1.0)/ (select count(a1)*1.0 from Application a1)) from Application a where a.status='PENDING'")
	Double RatioPendingApplications();

	@Query("select ((count(a)*1.0)/ (select count(a1)*1.0 from Application a1)) from Application a where a.status='ACCEPTED'")
	Double RatioAcceptedApplications();

	@Query("select ((count(a)*1.0)/ (select count(a1)*1.0 from Application a1)) from Application a where a.status='REJECTED'")
	Double RatioRejectedApplications();

	@Query("select ((count(a)*1.0)/ (select count(a1)*1.0 from Application a1)) from Application a where a.status='PENDING' and a.fixUpTask.startTime<CURRENT_TIMESTAMP()")
	Double RatioPendingCannotChangesStatus();

	@Query("select c from Customer c where (select (count(f1)*1.0) / (select count(c2) from Customer c2) from FixUpTask f1)* 1.1 <= (select (count(f3)*1.0) from FixUpTask f3 where f3.customer.id= c.id)")
	Collection<Customer> CustomerPublished10MoreFixUpTasks();

	@Query("select hw from HandyWorker hw where (select (count(a1)*1.0) / (select count(a2) from Application a2) from Application a1 where a1.status='ACCEPTED')* 1.1 <= (select (count(a3)*1.0) / (select count(a4) from Application a4 where hw.id= a4.handyWorker.id)from Application a3 where a3.status='ACCEPTED' and hw.id=a3.handyWorker.id) order by(1.0*(select count(a.handyWorker) from Application a where a.handyWorker=hw.id))")
	Collection<HandyWorker> handyPublished10MoreApplications();

	@Query("select a from Actor a where a.isSuspicious=true")
	Collection<Actor> suspiciousActors();

	@Query("select avg(1.0*(select count(co.fixUpTask) from Complaint co where co.fixUpTask=f.id)), min(1*(select count(co.fixUpTask) from Complaint co where co.fixUpTask=f.id)), max(1*(select count(co.fixUpTask) from Complaint co where co.fixUpTask=f.id)), stddev(1.0*(select count(co.fixUpTask) from Complaint co where co.fixUpTask=f.id)) from FixUpTask f")
	Collection<Double> ammsNumberComplaintsFixUpTasks();

	@Query("select avg(1.0*(select count(n.report) from Note n where n.report=r.id)), min(1*(select count(n.report) from Note n where n.report=r.id)), max(1*(select count(n.report) from Note n where n.report=r.id)), stddev(1.0*(select count(n.report) from Note n where n.report=r.id)) from Report r")
	Collection<Double> ammsNumberNotesRefereeReport();

	@Query("select count(*)/(select count(f)*1.0 from FixUpTask f) from Complaint c group by c.fixUpTask having count(*)=1")
	Double RatioFixUpTasksComplaint();

	@Query("select c from Customer c order by (1.0*(select count(co.customer) from Complaint co where co.customer=c.id)) desc")
	Collection<Customer> topThreeCustomersComplaints();

	@Query("select h from HandyWorker h, Application a, FixUpTask f, Complaint c where h.id=a.handyWorker.id and a.fixUpTask.id=f.id and f.id=c.fixUpTask.id group by h.id order by count(*) desc")
	Collection<HandyWorker> topThreeHandyWorkersComplaints();

}
