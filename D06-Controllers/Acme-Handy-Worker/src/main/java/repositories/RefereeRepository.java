
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Referee;
import domain.Report;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Integer> {

	@Query("select r from Referee r where r.userAccount.id = ?1")
	Referee findRefereeByUserAccountId(int userAccountId);

	@Query("select re from Report re where re.complaint.referee.id=?1")
	Collection<Report> reportsByReferee(int refereeId);

}
