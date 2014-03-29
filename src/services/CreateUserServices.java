package services;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import bd.CreateUserBD;

import services.tools.JSONTools;
import services.tools.RegexTools;
/**
 * Service implémentant la class CreateUserServices.
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class CreateUserServices {


	private String eMail ;
	private String name ;
	private String firstName ;
	private String password ;
	private Map<String,String[]> map;

	/**
	 * Constructeur de la fonction CreateUserServices.
	 * @param map La map stockant les informations du formulaire.
	 */
	public CreateUserServices(Map<String, String[]> map) {
		super();
		this.map = map;

		if(checkParam()) {
			eMail = map.get("eMail")[0];
			name = map.get("name")[0];
			firstName = map.get("firstName")[0];
			password = map.get("password")[0];
		}
	}
	/**
	 * Methode de verification des paramètres.
	 * @return Retourne true si les paramètres sont corrects, false sinon. 
	 */
	private boolean checkParam(){
		if(map.isEmpty()) return false;
		if(!map.containsKey("eMail")) return false;
		if(!map.containsKey("name")) return false;
		if(!map.containsKey("firstName")) return false;
		if(!map.containsKey("password"))return false;

		return true;
	}

	/**
	 * Méthode qui vérifie  le format d'une adresse mail.
	 * @return Retourne true si le format est correct, false sinon.
	 */
	private boolean checkMail() {
		if(!RegexTools.verifMail(eMail)) return false;
		return true;
	}

	/**
	 * Méthode qui retourne un JSONObject à l'erreur associée.
	 * @return Retourne un JSONObject erreur.
	 * @throws JSONException.
	 */
	private JSONObject getJSONParam() throws JSONException{
		if(map.isEmpty()) return JSONTools.error("No variables", -1, 400);
		if(!map.containsKey("eMail")) return JSONTools.error("eMail not defined", -1, 400);
		if(!map.containsKey("name")) return JSONTools.error("LastName not defined", -1, 400);
		if(!map.containsKey("firstName")) return JSONTools.error("fisrtName not defined", -1, 400);

		return JSONTools.error("password not defined", -1, 400);
	}

	/**
	 * Methode qui teste les paramètres et renvoie un JSONObject. 
	 * @return JSONObject Retourne soit un JSONObject success lors d'un succès, soit un JSONObject erreur si une erreur est détectée.
	 * @throws JSONException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public JSONObject isSuccess() throws JSONException, SQLException, ClassNotFoundException, UnsupportedEncodingException, NoSuchAlgorithmException {

		//Teste de la map si elle est vide et des parametres.
		if(!checkParam()) return getJSONParam();

		//teste Ladresse Mail
		if(!checkMail()) return JSONTools.error("eMail have a wrong syntax", -1, 400);
		
		//teste la date de naissance
		//if(!checkDateOfBirth()) return JSONTools.error("dateOfBirth have a wrong syntax", -1, 400);

		// test si lemail existe dans la bd
		CreateUserBD cubd = new CreateUserBD();
		if(cubd.checkLogin(eMail)) return JSONTools.error("eMail already exist in BD", -1, 400);
		else{
			if(!cubd.insertUser(eMail, password, name, firstName)){
				return JSONTools.error("Informations are not insered", 10000, 400);
			}
		}
		//Retourne ok si les teste precedent sont ok
		return JSONTools.ok(200);

	}

}


