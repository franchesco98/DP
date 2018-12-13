
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.SponsorShip;

@Repository
public interface SponsorShipRepository extends JpaRepository<SponsorShip, Integer> {

}
