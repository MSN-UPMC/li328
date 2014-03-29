package services.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * La class RegexTools
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class RegexTools {
	
	/**
	 * Methode qui verifie le format d'une adresse mail.
	 * @param email L'adresse mail.
	 * @return Retourne true si la syntaxe est correcte, false sinon.
	 */
	public static boolean verifMail(String email){
		
		Pattern pattern = Pattern.compile("[^@]+@[^\\.]+\\.(.*)");
		Matcher matcher = pattern.matcher(email);
		
		return matcher.find();
	}
	/**
	 * Methode qui verifie le format d'une date.
	 * @param dateOfBirth La date.
	 * @return Retourne true si la syntaxe est correcte, false sinon.
	 */
	public static boolean verifDateOfBirth(String dateOfBirth){
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
		try {     
			format.parse(dateOfBirth);
	        return true;
		}
		catch(ParseException e){
			return false;
		}
	}
}
