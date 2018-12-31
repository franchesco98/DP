
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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	private Date	writtenMoment;
	private String	customerComment;
	private String	refereeComment;
	private String	handyWorkerComment;


	public Note() {
		super();
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getWrittenMoment() {
		return this.writtenMoment;
	}

	public void setWrittenMoment(final Date wrritenMoment) {
		this.writtenMoment = wrritenMoment;
	}

	public String getCustomerComment() {
		return this.customerComment;
	}

	public void setCustomerComment(final String customerComment) {
		this.customerComment = customerComment;
	}

	public String getRefereeComment() {
		return this.refereeComment;
	}

	public void setRefereeComment(final String refereeComment) {
		this.refereeComment = refereeComment;
	}

	public String getHandyWorkerComment() {
		return this.handyWorkerComment;
	}

	public void setHandyWorkerComment(final String handyWorkerComment) {
		this.handyWorkerComment = handyWorkerComment;
	}


	//Relationships
	private Report	report;


	@Valid
	@ManyToOne(optional = false)
	public Report getReport() {
		return this.report;
	}

	public void setReport(final Report reports) {
		this.report = reports;
	}

}
