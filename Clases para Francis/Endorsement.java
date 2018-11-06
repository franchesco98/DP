
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Endorsement extends DomainEntity {

	private Date	moment;
	private String	comments;


	public Endorsement() {
		super();
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}


	//Relationships
	private Endorser	sender;
	private Endorser	recipient;


	@Valid
	@ManyToOne(optional = false)
	public Endorser getSender() {
		return this.sender;
	}

	public void setSender(final Endorser sender) {
		this.sender = sender;
	}
	@Valid
	@ManyToOne(optional = false)
	public Endorser getRecipient() {
		return this.recipient;
	}

	public void setRecipient(final Endorser recipient) {
		this.recipient = recipient;
	}
}
