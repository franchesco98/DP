
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Complaint extends DomainEntity {

	private String	ticker;
	private Date	moment;
	private String	descrition;
	private Integer	attachementNumber;


	public Complaint() {
		super();
	}

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^([0-9]{2})([0-1])([0-9])([0-3])([0-9])(-)(?:[a-zA-Z0-9]*)$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@Past
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getDescrition() {
		return this.descrition;
	}

	public void setDescrition(final String descrition) {
		this.descrition = descrition;
	}

	public Integer getAttachementNumber() {
		return this.attachementNumber;
	}

	public void setAttachementNumber(final Integer attachementNumber) {
		this.attachementNumber = attachementNumber;
	}


	//relationships

	private Customer	customer;
	private Report		report;
	private FixUpTask	fixUpTask;


	@Valid
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@Valid
	@ManyToOne(optional = true)
	public Report getReport() {
		return this.report;
	}

	public void setReport(final Report report) {
		this.report = report;
	}

	@Valid
	@ManyToOne(optional = false)
	public FixUpTask getFixUpTask() {
		return this.fixUpTask;
	}

	public void setFixUpTask(final FixUpTask fixUpTask) {
		this.fixUpTask = fixUpTask;
	}

}
