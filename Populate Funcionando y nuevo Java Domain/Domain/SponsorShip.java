
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SponsorShip extends DomainEntity {

	private String	bannerLink;
	private String	targetLink;


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


	//relationship-------------------------------------------------------------

	private Tutorial	tutorial;
	private Sponsor		sponsor;
	private CreditCard	creditCard;


	@Valid
	@ManyToOne(optional = true)
	public Tutorial getTutorial() {
		return this.tutorial;
	}

	public void setTutorial(final Tutorial tutorial) {
		this.tutorial = tutorial;
	}

	@Valid
	@ManyToOne(optional = false)
	public Sponsor getSponsor() {
		return this.sponsor;
	}

	public void setSponsor(final Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	@Valid
	@ManyToOne(optional = false)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
