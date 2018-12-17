
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	private Date	moment;
	private String	status;
	private double	offeredPrice;
	private String	handyWorkerComments;
	private String	customerComments;


	public Application() {
		super();
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@Pattern(regexp = "^ACCEPTED|PENDING|REJECTED|$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@Min(0)
	@Digits(integer = 4, fraction = 2)
	public double getOfferedPrice() {
		return this.offeredPrice;
	}

	public void setOfferedPrice(final double offeredPrice) {
		this.offeredPrice = offeredPrice;
	}

	@NotBlank
	public String getHandyWorkerComments() {
		return this.handyWorkerComments;
	}

	public void setHandyWorkerComments(final String handyWorkerComments) {
		this.handyWorkerComments = handyWorkerComments;
	}

	public String getCustomerComments() {
		return this.customerComments;
	}

	public void setCustomerComments(final String customerComments) {
		this.customerComments = customerComments;
	}


	//relationships

	private HandyWorker	handyWorker;
	private FixUpTask	fixUpTask;
	private CreditCard	creditCard;


	@Valid
	@ManyToOne(optional = false)
	public HandyWorker getHandyWorker() {
		return this.handyWorker;
	}

	public void setHandyWorker(final HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}

	@Valid
	@ManyToOne(optional = false)
	public FixUpTask getFixUpTask() {
		return this.fixUpTask;
	}

	public void setFixUpTask(final FixUpTask fixUpTask) {
		this.fixUpTask = fixUpTask;
	}

	@Valid
	@ManyToOne(optional = true)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
