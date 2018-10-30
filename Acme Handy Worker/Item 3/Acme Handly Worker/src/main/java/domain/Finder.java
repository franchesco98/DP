
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
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


	public Finder() {
		super();
	}

	@NotBlank
	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	@NotBlank
	public String getCategory() {
		return this.category;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

	@NotBlank
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
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDateMin() {
		return this.dateMin;
	}

	public void setDateMin(final Date dateMin) {
		this.dateMin = dateMin;
	}
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDateMax() {
		return this.dateMax;
	}

	public void setDateMax(final Date dateMax) {
		this.dateMax = dateMax;
	}

}
