package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import services.tools.JSONTools;
import bd.DeleteFriendBD;
import bd.FriendListBD;
/**
 * Service implémentant la class FriendListServices
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class FriendListServices {

	private int idA;
	private Map<String, String[]> map;
	

	/**
	 * Constructeur de la fonction FriendListServices.
	 * @param map La map stockant les informations du formulaire.
	 */		
	public FriendListServices(Map<String, String[]> map) {
		// TODO Auto-generated constructor stub
	
		super();
		this.map = map;
		
		if(checkParam()){
			idA = Integer.parseInt(map.get("idA")[0]);
		}
	}
	
	/**
	 * Methode de verification des paramètres.
	 * @return Retourne true si les paramètres sont corrects, false sinon. 
	 */	
	private boolean checkParam(){
		if(map.isEmpty()) return false;
		if(!map.containsKey("idA")) return false;
		return true;
	}
	/**
	 * Méthode qui retourne un JSONObject à l'erreur associée.
	 * @return Retourne un JSONObject erreur.
	 * @throws JSONException.
	 */	
	private JSONObject getJSONParam() throws JSONException{
		if(map.isEmpty()) return JSONTools.error("No variables", -1, 400);
		return JSONTools.error("idA not defined", -1, 400);
	}
	/**
	* Methode qui teste les paramètres et renvoie un JSONObject. 
	 * @return JSONObject Retourne soit un JSONObject success lors d'un succès, soit un JSONObject erreur si une erreur est détectée.
	 * @throws JSONException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public JSONObject isSuccess() throws JSONException, SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		//Teste de la map si elle est vide et des parametres.
		
		if(!checkParam()){
			return getJSONParam();
		}
		
		//teste BD
		FriendListBD flbd = new FriendListBD();
		if(!flbd.checkId(idA)){
			return JSONTools.error("id not exist in BD", -1, 400);
		}
		if(!flbd.friendList(idA)){
			return JSONTools.error("You have not friend", -1, 400);
		}
		
		return flbd.getFriendList(idA);
		
	}

	
}
