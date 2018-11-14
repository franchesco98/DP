
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	private int	patternPN;
	private int	finderCacheTime;
	private int	numberOfResult;


	public Configuration() {
		super();
	}

	@Digits(integer = 4, fraction = 0)
	public int getPatternPN() {
		return this.patternPN;
	}

	public void setPatternPN(final int patternPN) {
		this.patternPN = patternPN;
	}

	@Min(0)
	@Max(24)
	public int getFinderCacheTime() {
		return this.finderCacheTime;
	}

	public void setFinderCacheTime(final int finderCacheTime) {
		this.finderCacheTime = finderCacheTime;
	}

	@Min(0)
	@Max(100)
	public int getNumberOfResult() {
		return this.numberOfResult;
	}

	public void setNumberOfResult(final int numberOfResult) {
		this.numberOfResult = numberOfResult;
	}

}
