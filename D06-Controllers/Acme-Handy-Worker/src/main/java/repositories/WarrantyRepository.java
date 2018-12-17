
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;
import domain.Warranty;

@Repository
public interface WarrantyRepository extends JpaRepository<Warranty, Integer> {

	@Query("select f from FixUpTask f where f.warranty = ?1")
	Collection<FixUpTask> getFiUpTasksByWarrantyId(int warrantyId);

}
