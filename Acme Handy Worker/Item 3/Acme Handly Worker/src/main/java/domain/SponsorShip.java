
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SponsorShip extends DomainEntity {

	private String	bannerLink;
	private String	targetLink;
	private String	creditCardNumber;


	public SponsorShip() {
		super();
	}
	@URL
	public String getBannerLink() {
		return this.bannerLink;
	}

	public void setBannerLink(final String bannerLink) {
		this.bannerLink = bannerLink;
	}
	@URL
	public String getTargetLink() {
		return this.targetLink;
	}

	public void setTargetLink(final String targetLink) {
		this.targetLink = targetLink;
	}
	@CreditCardNumber
	public String getCreditCardNumber() {
		return this.creditCardNumber;
	}

	public void setCreditCardNumber(final String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

}
