
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Configuration;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {

	@Query("select f.ticker,c.ticker,cu.ticker from FixUpTask f, Complaint c, Curriculum cu")
	Collection<String> getAllTickers();
}
