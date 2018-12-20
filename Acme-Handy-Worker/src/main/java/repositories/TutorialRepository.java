
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Section;
import domain.SponsorShip;
import domain.Tutorial;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Integer> {

	@Query("select s from SponsorShip s where s.tutorial = ?1")
	Collection<SponsorShip> getSponsorShip(int tutorialId);

	@Query("select t.sections from Tutorial t join t.sections s where t.id = ?1 order by s.number")
	Collection<Section> getSectionByTutorial(int tutorialId);

}
