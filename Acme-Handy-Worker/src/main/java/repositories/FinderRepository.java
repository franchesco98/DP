
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.FixUpTask;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select f from FixUpTask f where (f.ticker like concat('%',?1,'%') or f.address like concat('%',?1,'%') or f.description like concat('%',?1,'%')) and f.category.name like concat('%',?2,'%') and f.warranty.title like concat('%',?3,'%') and f.maxPrice>=?4 and f.maxPrice<=?5 and f.startTime>=?6 and f.endTime<=?7")
	Collection<FixUpTask> findTasksByParams(String keyWord, String category, String warranty, Double priceMin, Double priceMax, Date dateMin, Date dateMax);

	@Query("select f from FixUpTask f where (f.ticker like concat('%',?1,'%') or f.address like concat('%',?1,'%') or f.description like concat('%',?1,'%')) and f.category.name like concat('%',?2,'%') and f.warranty.title like concat('%',?3,'%') and f.startTime>=?4 and f.endTime<=?5")
	Collection<FixUpTask> findTasksByParamsWithoutPrice(String keyWord, String category, String warranty, Date dateMin, Date dateMax);

	@Query("select f from FixUpTask f where (f.ticker like concat('%',?1,'%') or f.address like concat('%',?1,'%') or f.description like concat('%',?1,'%')) and f.category.name like concat('%',?2,'%') and f.warranty.title like concat('%',?3,'%') and f.maxPrice>=?4 and f.maxPrice<=?5")
	Collection<FixUpTask> findTasksByParamsWithoutDate(String keyWord, String category, String warranty, Double priceMin, Double priceMax);

	@Query("select f from FixUpTask f where (f.ticker like concat('%',?1,'%') or f.address like concat('%',?1,'%') or f.description like concat('%',?1,'%')) and f.category.name like concat('%',?2,'%') and f.warranty.title like concat('%',?3,'%')")
	Collection<FixUpTask> findTasksByParamsWithoutPriceAndDate(String keyWord, String category, String warranty);
}
