
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	private int		patternPN;
	private int		finderCacheTime;
	private int		numberOfResult;
	private String	spamWords;
	private String	positiveWordsE;
	private String	negativeWordsE;
	private String	positiveWordsS;
	private String	negativeWordsS;
	private String	nameSystem;
	private String	banner;
	private String	welcomeMessageE;
	private String	welcomeMessageS;
	private double	VAT;
	private int		countryCode;


	public Configuration() {
		super();
	}

	@Digits(integer = 4, fraction = 0)
	public int getPatternPN() {
		return this.patternPN;
	}

	public void setPatternPN(final int patternPN) {
		this.patternPN = patternPN;
	}

	@Min(0)
	@Max(24)
	public int getFinderCacheTime() {
		return this.finderCacheTime;
	}

	public void setFinderCacheTime(final int finderCacheTime) {
		this.finderCacheTime = finderCacheTime;
	}

	@Min(0)
	@Max(100)
	public int getNumberOfResult() {
		return this.numberOfResult;
	}

	public void setNumberOfResult(final int numberOfResult) {
		this.numberOfResult = numberOfResult;
	}

	@NotBlank
	public String getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final String spamWords) {
		this.spamWords = spamWords;
	}

	@NotBlank
	public String getPositiveWords() {
		return this.positiveWordsE;
	}

	public void setPositiveWords(final String positiveWords) {
		this.positiveWordsE = positiveWords;
	}

	@NotBlank
	public String getNameSystem() {
		return this.nameSystem;
	}

	public void setNameSystem(final String nameSystem) {
		this.nameSystem = nameSystem;
	}

	@NotBlank
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getWelcomeMessageE() {
		return this.welcomeMessageE;
	}

	public void setWelcomeMessageE(final String welcomeMessageE) {
		this.welcomeMessageE = welcomeMessageE;
	}

	@NotBlank
	public String getWelcomeMessageS() {
		return this.welcomeMessageS;
	}

	public void setWelcomeMessageS(final String welcomeMessageS) {
		this.welcomeMessageS = welcomeMessageS;
	}

	@Digits(integer = 4, fraction = 2)
	public double getVAT() {
		return this.VAT;
	}

	public void setVAT(final double vAT) {
		this.VAT = vAT;
	}

	@Min(0)
	@Max(999)
	public int getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final int countryCode) {
		this.countryCode = countryCode;
	}

	@NotBlank
	public String getNegativeWords() {
		return this.negativeWordsE;
	}

	public void setNegativeWords(final String negativeWords) {
		this.negativeWordsE = negativeWords;
	}

	@NotBlank
	public String getPositiveWordsE() {
		return this.positiveWordsE;
	}

	public void setPositiveWordsE(final String positiveWordsE) {
		this.positiveWordsE = positiveWordsE;
	}

	@NotBlank
	public String getNegativeWordsE() {
		return this.negativeWordsE;
	}

	public void setNegativeWordsE(final String negativeWordsE) {
		this.negativeWordsE = negativeWordsE;
	}

	@NotBlank
	public String getPositiveWordsS() {
		return this.positiveWordsS;
	}

	public void setPositiveWordsS(final String positiveWordsS) {
		this.positiveWordsS = positiveWordsS;
	}

	@NotBlank
	public String getNegativeWordsS() {
		return this.negativeWordsS;
	}

	public void setNegativeWordsS(final String negativeWordsS) {
		this.negativeWordsS = negativeWordsS;
	}

}
