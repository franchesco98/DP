
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Endorser extends Actor {

	private double	score;


	public Endorser() {
		super();
	}

	@Min(-1)
	@Max(1)
	@Digits(integer = 1, fraction = 2)
	public double getScore() {
		return this.score;
	}

}
