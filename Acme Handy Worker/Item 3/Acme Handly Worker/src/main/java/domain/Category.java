
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private String	name;


	public Category() {
		super();
	}

	@NotBlank
	@Column(unique = true)
	@Pattern(
		regexp = "^CARPENTRY|CEILING REPAIR|CLEANING|CONCRETE WORK| FAN INSTALLATION|DOORS|ELECTRICAL WIRING|FENCE FIXING|HOME SECURITY SYSTEMS|INSULATION INSTALLATION|LAMP REPAIRS|MOVING|PAINTING|PEST CONTROL|PLUMBING REPAIRS|ROOFING|SHELFINSTALLATION|SOLAR PANELS|SOUNDPROOFING|SPRINKLER REPAIR|WINDOW REPAIR$")
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
