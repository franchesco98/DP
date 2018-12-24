
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("select distinct sub from Category c join c.categories sub where c.id = ?1")
	Collection<Category> getChildren(int categoryId);

	@Query("select c from Category c where c.name = ?1")
	Category findCategoryByName(String category);
}
