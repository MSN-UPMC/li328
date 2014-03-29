package services;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import services.tools.JSONTools;

import com.mongodb.MongoException;

import bd.SearchWithoutQueryBD;
import bd.SearchWithoutQueryFriendBD;
/**
 * Service implémentant la class SearchWithoutQueryFriendServices
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class SearchWithoutQueryFriendServices {

	private Map<String, String[]> map;
	private String key;
	/**
	 * Constructeur de la fonction SearchWithoutQueryFriendServices.
	 * @param map La map stockant les informations du formulaire.
	 */	
	public SearchWithoutQueryFriendServices(Map<String, String[]> map) {
		// TODO Auto-generated constructor stub
		super();
		this.map = map;

		if(checkParam()) {
			key = map.get("key")[0];
		}
	}
	/**
	 * Methode de verification des paramètres.
	 * @return Retourne true si les paramètres sont corrects, false sinon. 
	 */	
	private boolean checkParam() {
		if(map.isEmpty()) return false;
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

		return JSONTools.error("key not defined", -1, 400);
	}


	/**
	* Methode qui teste les paramètres et renvoie un JSONObject. 
	 * @return JSONObject Retourne soit une liste de JSONObject success lors d'un succès, soit une liste JSONObject erreur si une erreur est détectée.
	 * @throws JSONException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws UnknownHostException
	 * @throws MongoException
	 */
	public List<JSONObject> isSuccess() throws UnknownHostException, MongoException, JSONException, SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		//Teste de la map si elle est vide et des parametres.
		if(!checkParam()){
			List<JSONObject> l = new ArrayList<JSONObject>();
			l.add(getJSONParam());
			return l;
		}

		// test sql
		SearchWithoutQueryFriendBD swqfbd = new SearchWithoutQueryFriendBD();
		if(!swqfbd.checkKey(key)) {
			List<JSONObject> l = new ArrayList<JSONObject>();
			l.add(JSONTools.error("key not found or expired in the BD", -1, 400));
			return l;
		}


		return swqfbd.getComments(key);
	}

}
