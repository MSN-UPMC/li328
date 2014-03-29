package services;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import bd.CreateUserBD;
import bd.LoginBD;

import services.factory.HashFactory;
import services.tools.JSONTools;
import services.tools.RegexTools;

/**
 * Cette classe permet l'authentification d'un utilisateur
 * @author M. Kamalraj, N. Mag-Stellon
 *
 */
public class LoginServices {

	/**
	 * Attribut permettant de specifier le e-mail de l'utilisateur
	 */
	private String eMail;
	/**
	 * Attribut permettant de specifier le mot de passe de l'utilisateur
	 */
	private String password;
	
	
	private Map<String, String[]> map;
	
	/**
	 * Constructeur de la classe login
	 * @param eMail L'adresse mail de l'utilisateur
	 * @param password Le mot de passe de l'utilisateur
	 */

	public String key;

	public LoginServices(Map<String, String[]> map) {
		this.map = map;

		if(checkParam()) {
			eMail = map.get("eMail")[0];
			password = map.get("password")[0];
		}
	}

	private boolean checkParam(){
		if(map.isEmpty()) return false;
		if(!map.containsKey("eMail")) return false;
		if(!map.containsKey("password"))return false;

		return true;
	}
	

	private boolean checkMail() {
		if(!RegexTools.verifMail(eMail)) return false;
		return true;
	}
	
	
	private JSONObject getJSONParam() throws JSONException{
		if(map.isEmpty()) return JSONTools.error("No variables", -1, 400);
		if(!map.containsKey("eMail")) return JSONTools.error("eMail not defined", -1, 400);

		return JSONTools.error("password not defined", -1, 400);
	}
	
	
	private JSONObject getJSONMail() throws JSONException{
		return JSONTools.error("eMail have a wrong syntax", -1, 400);
	}
	
	
	private JSONObject getJSONlogin() throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException{

		JSONObject obj = new JSONObject();
		obj.put("Key", key);
		obj.put("CodeHTTP", "200");
		
		return obj;
	}
	
	public JSONObject isSuccess() throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException, ClassNotFoundException, SQLException{

		//Teste de la map si elle est vide et des parametres.
		if(!checkParam()) return getJSONParam();

		//teste Ladresse Mail
		if(!checkMail()) return getJSONMail();

		// test si lemail existe dans la bd
		LoginBD lbd = new LoginBD();
		key = HashFactory.hashMd5Variable(password);
		
		if(!lbd.checkLoginAndPassword(eMail, password)) return JSONTools.error("You're not registered", -1, 400);
		else{
			if(!lbd.createLogin(eMail, 1,key)){
				return JSONTools.error("Informations are not insered", 10000, 400);
			}
		}
		
		//Retourne ok si les teste precedent sont ok
		return getJSONlogin();

	}
	
}
