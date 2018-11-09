
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Box extends DomainEntity {

	private String	name;
	private Boolean	systemBox;


	public Box() {
		super();
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Boolean getSystemBox() {
		return this.systemBox;
	}

	public void setSystemBox(final Boolean systemBox) {
		this.systemBox = systemBox;
	}


	//relationships

	private Message	message;


	@Valid
	@ManyToOne(optional = true)
	public Message getMessage() {
		return this.message;
	}

	public void setMessage(final Message message) {
		this.message = message;
	}

}
