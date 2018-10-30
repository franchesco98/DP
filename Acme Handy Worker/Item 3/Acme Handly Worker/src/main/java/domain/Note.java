
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	private Date	wrritenMoment;
	private String	mandatory;
	private String	customerComment;
	private String	refereeComment;
	private String	handyWorkerComment;


	public Note() {
		super();
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getWrritenMoment() {
		return this.wrritenMoment;
	}

	public void setWrritenMoment(final Date wrritenMoment) {
		this.wrritenMoment = wrritenMoment;
	}

	@NotBlank
	public String getMandatory() {
		return this.mandatory;
	}

	public void setMandatory(final String mandatory) {
		this.mandatory = mandatory;
	}

	@NotBlank
	public String getCustomerComment() {
		return this.customerComment;
	}

	public void setCustomerComment(final String customerComment) {
		this.customerComment = customerComment;
	}

	@NotBlank
	public String getRefereeComment() {
		return this.refereeComment;
	}

	public void setRefereeComment(final String refereeComment) {
		this.refereeComment = refereeComment;
	}

	@NotBlank
	public String getHandyWorkerComment() {
		return this.handyWorkerComment;
	}

	public void setHandyWorkerComment(final String handyWorkerComment) {
		this.handyWorkerComment = handyWorkerComment;
	}

}
