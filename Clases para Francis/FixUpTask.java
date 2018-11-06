
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
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
public class FixUpTask extends DomainEntity {

	private String	ticker;
	private Date	moment;
	private String	description;
	private String	address;
	private double	maxPrice;
	private Date	startTime;
	private Date	endTime;


	public FixUpTask() {
		super();
	}

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^([0-9]{2})([0-1])([0-9])([0-3])([0-9])(-)(?:[a-zA-Z0-9]*)$")
	//	Preguntar cómo utilizar la expresión regular aquí
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
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
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
	@NotBlank
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}
	@Min(0)
	@Digits(integer = 4, fraction = 2)
	public double getMaxPrice() {
		return this.maxPrice;
	}

	public void setMaxPrice(final double maxPrice) {
		this.maxPrice = maxPrice;
	}
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(final Date startTime) {
		this.startTime = startTime;
	}
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(final Date endTime) {
		this.endTime = endTime;
	}


	//Relationships

	private Finder		finder;
	private Application	application;
	private Category	category;


	@Valid
	@ManyToOne(optional = false)
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	@Valid
	@ManyToOne(optional = false)
	public Application getApplication() {
		return this.application;
	}

	public void setApplication(final Application application) {
		this.application = application;
	}

	@Valid
	@ManyToOne(optional = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

}
