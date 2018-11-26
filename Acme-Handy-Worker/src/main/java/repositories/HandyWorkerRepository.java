
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Report;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	@Query("select hw from HandyWorker hw where hw.userAccount.id = ?1")
	HandyWorker findByUserAccountId(int userAccountId);

	@Query("select distinct f from FixUpTask f, Finder fin, HandyWorker hw where hw.finder=fin.id and fin.fixUpTask = f.id and hw.id = ?1")
	Collection<FixUpTask> displayFixUpTasks(int userAccountId);

	@Query("select distinct c from Complaint c, FixUpTask f, Application a, HandyWorker hw where c.fixUpTask = f.id and a.fixUpTask = f.id and a.handyWorker=hw.id and hw.id = ?1")
	Collection<Complaint> displayComplaints(int userAccountId);

	@Query("select distinct r from Report r, Complaint c, FixUpTask f, Application a, HandyWorker hw where c.report = r.id and c.fixUpTask = f.id and a.fixUpTask = f.id and a.handyWorker=hw.id and hw.id = ?1")
	Collection<Report> displayComplaintsReport(int userAccountId);

	//	@Query("select p from Phase p where p.handy.id = ?1")
	//	HandyWorker findPhasesByUserAccountId(int userAccountId);

}
