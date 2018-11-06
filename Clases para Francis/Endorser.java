
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

	private double		score;
	private Endorsement	endorsementSent;
	private Endorsement	endorsementReceived;


	public Endorser() {
		super();
	}

	@Min(-1)
	@Max(1)
	@Digits(integer = 1, fraction = 2)
	public double getScore() {
		return this.score;
	}

	public Endorsement getEndorsementSent() {
		return this.endorsementSent;
	}

	public void setEndorsementSent(final Endorsement endorsementSent) {
		this.endorsementSent = endorsementSent;
	}

	public Endorsement getEndorsementReceived() {
		return this.endorsementReceived;
	}

	public void setEndorsementReceived(final Endorsement endorsementReceived) {
		this.endorsementReceived = endorsementReceived;
	}

	//Relationships

}
