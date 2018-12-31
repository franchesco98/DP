
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CreditCard;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

	@Query("select c from CreditCard c where c.actor.id = ?1")
	Collection<CreditCard> findByPrincipalId(int actorId);

	@Query("select c from CreditCard c where c.name = ?1")
	CreditCard findCreditCardByName(String name);
}
