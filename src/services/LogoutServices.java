package services;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import bd.LogoutBD;

import services.tools.JSONTools;

/**
 * Cette classe permet la déconnexion d'un utilisateur
 * @author M. Kamalraj, N. Mag-Stellon
 *
 */
public class LogoutServices {
	
	private String key;
	private Map<String, String[]> map;
	/**
	 * Constructeur de la fonction LogoutServices.
	 * @param map La map stockant les informations du formulaire.
	 */	
	public LogoutServices(Map<String, String[]> map) {
		this.map = map;

		if(checkParam()) {
			key = map.get("key")[0];

		}
	}

	/**
	 * Methode de verification des paramètres.
	 * @return Retourne true si les paramètres sont corrects, false sinon. 
	 */	
	private boolean checkParam(){
		if(map.isEmpty()) return false;
		if(!map.containsKey("key")) return false;

		return true;
	}
	
	/**
	 * Méthode qui retourne un JSONObject à l'erreur associée.
	 * @return Retourne un JSONObject erreur.
	 * @throws JSONException.
	 */	
	private JSONObject getJSONParam() throws JSONException{
		if(map.isEmpty()) return JSONTools.error("No variables", -1, 400);
		
		return JSONTools.error("Key not defined", -1, 400);

		
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
	public JSONObject isSuccess() throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException, ClassNotFoundException, SQLException{

		//Teste de la map si elle est vide et des parametres.
		if(!checkParam()) return getJSONParam();

		
		// Teste la bd
		LogoutBD lbd = new LogoutBD();
		if(!lbd.checkKey(key)) return JSONTools.error("You're not login", -1, 400);
		else{
			if(!lbd.updateEndTime(key) || !lbd.deleteKey(key)){
				return JSONTools.error("Logout fails", 10000, 400);
			}
		}

		
		//Retourne ok si les teste precedent sont ok
		return JSONTools.ok(200);

	}
	
	

}
