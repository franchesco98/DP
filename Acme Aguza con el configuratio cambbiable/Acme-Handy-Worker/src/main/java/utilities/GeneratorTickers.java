/*
 * HashPassword.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package utilities;

import java.security.SecureRandom;
import java.util.Date;

public class GeneratorTickers {

	public static String ticker() {

		//fecha
		final String yy = "" + (new Date().getYear() - 100);
		String mm = "" + (new Date().getMonth() + 1);
		String dd = "" + new Date().getDate();
		if (mm.length() < 2)
			mm = "0" + mm;
		if (dd.length() < 2)
			dd = "0" + dd;

		//cadena alfanumerica

		String code = "";

		String numberRandon = SecureRandom.getSeed(6).toString();
		numberRandon = (String) numberRandon.subSequence(numberRandon.length() - 6, numberRandon.length());
		numberRandon.replace("@", "6");
		code = yy + mm + dd + "-" + numberRandon.toUpperCase();

		return code;
	}

}
