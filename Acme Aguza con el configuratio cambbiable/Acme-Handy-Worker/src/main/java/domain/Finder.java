
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String	keyWord;
	private String	category;
	private String	warranty;
	private Double	priceMin;
	private Double	priceMax;
	private Date	dateMin;
	private Date	dateMax;
	private Date	lastUpdate;


	public Finder() {
		super();
	}

	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

	public String getWarranty() {
		return this.warranty;
	}

	public void setWarranty(final String warranty) {
		this.warranty = warranty;
	}
	@Min(0)
	@Digits(integer = 4, fraction = 2)
	public Double getPriceMin() {
		return this.priceMin;
	}

	public void setPriceMin(final Double priceMin) {
		this.priceMin = priceMin;
	}
	@Min(0)
	@Digits(integer = 4, fraction = 2)
	public Double getPriceMax() {
		return this.priceMax;
	}

	public void setPriceMax(final Double priceMax) {
		this.priceMax = priceMax;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDateMin() {
		return this.dateMin;
	}

	public void setDateMin(final Date dateMin) {
		this.dateMin = dateMin;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDateMax() {
		return this.dateMax;
	}

	public void setDateMax(final Date dateMax) {
		this.dateMax = dateMax;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	//Relationships

	private Collection<FixUpTask>	fixUpTasks;


	@Valid
	@ManyToMany
	public Collection<FixUpTask> getFixUpTasks() {
		return this.fixUpTasks;
	}

	public void setFixUpTasks(final Collection<FixUpTask> fixUpTasks) {
		this.fixUpTasks = fixUpTasks;
	}

}
