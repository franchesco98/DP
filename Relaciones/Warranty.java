
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Warranty extends DomainEntity {

	private String	title;
	private String	terms;
	private String	applicableLaws;
	private Boolean	isFinal;


	public Warranty() {
		super();
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getTerms() {
		return this.terms;
	}

	public void setTerms(final String terms) {
		this.terms = terms;
	}

	@NotBlank
	@Min(1)
	public String getApplicableLaws() {
		return this.applicableLaws;
	}

	public void setApplicableLaws(final String applicableLaws) {
		this.applicableLaws = applicableLaws;
	}

	public Boolean getIsFinal() {
		return this.isFinal;
	}

	public void setIsFinal(final Boolean isFinal) {
		this.isFinal = isFinal;
	}


	///////////////////////////////////relationship//////////////////////////////////////////
	private FixUpTask	fixUpTask;


	@Valid
	@OneToOne(optional = false)
	public FixUpTask getFixUpTask() {
		return this.fixUpTask;
	}

}
