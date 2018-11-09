
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Section extends DomainEntity {

	private int		number;
	private String	title;
	private String	pieceOfText;
	private String	pictures;


	public Section() {
		super();
	}

	@NotBlank
	@Column(unique = true)
	@Min(0)
	public int getNumber() {
		return this.number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}
	@NotBlank
	public String getPieceOfText() {
		return this.pieceOfText;
	}

	public void setPieceOfText(final String pieceOfText) {
		this.pieceOfText = pieceOfText;
	}
	@URL
	public String getPictures() {
		return this.pictures;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

}
