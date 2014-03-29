package services;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import services.tools.JSONTools;
import bd.AddCommentBD;
import bd.NameFromIDBD;

import com.mongodb.MongoException;

/**
 * Service implémentant la class NameFromIDServices
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class NameFromIDServices {
	
	
	private Map<String, String[]> map;
	private String key;
	private int id;
	/**
	 * Constructeur de la fonction NameFromIDServices.
	 * @param map La map stockant les informations du formulaire.
	 */	
	public NameFromIDServices(Map<String, String[]> map) {
		// TODO Auto-generated constructor stub
		this.map = map;

		if(checkParam()) {
			key = map.get("key")[0];
			id = Integer.parseInt(map.get("id")[0]);
		}
	}
	/**
	 * Methode de verification des paramètres.
	 * @return Retourne true si les paramètres sont corrects, false sinon. 
	 */	
	private boolean checkParam() {
		if(map.isEmpty()) return false;
		if(!map.containsKey("id")) return false;
		if(!map.containsKey("key")) return false;
		return true;
	}
	
	

	/**
	 * Méthode qui retourne un JSONObject à l'erreur associée.
	 * @return Retourne un JSONObject erreur.
	 * @throws JSONException.
	 */	
	private JSONObject getJSONParam() throws JSONException {
		// TODO Auto-generated method stub
		if(map.isEmpty()) return JSONTools.error("No variables", -1, 400);
		if(!map.containsKey("key")) return JSONTools.error("key not defined", -1, 400);

		return JSONTools.error("id not defined", -1, 400);
	}
	/**
	* Methode qui teste les paramètres et renvoie un JSONObject. 
	 * @return JSONObject Retourne soit un JSONObject success lors d'un succès, soit un JSONObject erreur si une erreur est détectée.
	 * @throws JSONException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws UnknownHostException
	 * @throws MongoException
	 */
	public JSONObject isSuccess() throws JSONException, SQLException, ClassNotFoundException, UnknownHostException, MongoException {
		// TODO Auto-generated method stub
		
		//Teste de la map si elle est vide et des parametres.
		if(!checkParam()) return getJSONParam();
		
		// test si la key existe dans la bd
		NameFromIDBD acbd = new NameFromIDBD();
		if(!acbd.checkKey(key)) return JSONTools.error("key not found or expired in the BD", -1, 400);
		
		return acbd.getNameFromId(id);		
		
		
		
	}
	
	

}
