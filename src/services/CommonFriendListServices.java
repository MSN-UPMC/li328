package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import services.tools.JSONTools;
import bd.CommonFriendListBD;
import bd.FriendListBD;
/**
 * Service implémentant la class AddFriendServices
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class CommonFriendListServices {

	private int idA1;
	private int idA2;
	private Map<String, String[]> map;
	
	/**
	 * Constructeur de la fonction CommonFriendListServices.
	 * @param map La map stockant les informations du formulaire.
	 */
	public CommonFriendListServices(Map<String, String[]> map) {
		// TODO Auto-generated constructor stub
	
		super();
		this.map = map;
		
		if(checkParam()){
			idA1 = Integer.parseInt(map.get("idA1")[0]);
			idA2 = Integer.parseInt(map.get("idA2")[0]);

		}
	}
	/**
	 * Méthode qui vérifie si les deux utilisateurs ne sont pas identiques.
	 * @return Retourne true si les deux utilisateurs sont différents, false sinon.
	 */
	private boolean checkSameParam(){
		if(idA1==idA2){
			return false;
		}
		else{
			return true;
		}		
	}
	/**
	 * Methode de verification des paramètres.
	 * @return Retourne true si les paramètres sont corrects, false sinon. 
	 */
	private boolean checkParam(){
		if(map.isEmpty()) return false;
		if(!map.containsKey("idA1")) return false;
		if(!map.containsKey("idA2")) return false;

		return true;
	}
	/**
	 * Méthode qui retourne un JSONObject à l'erreur associée.
	 * @return Retourne un JSONObject erreur.
	 * @throws JSONException.
	 */	
	private JSONObject getJSONParam() throws JSONException{
		if(map.isEmpty()) return JSONTools.error("No variables", -1, 400);
		if(!map.containsKey("idA1")) return JSONTools.error("idA1 not defined", -1, 400);
		return JSONTools.error("idA2 not defined", -1, 400);
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
		
		List<JSONObject> l = new ArrayList<JSONObject>();
		if(!checkParam()){
			return getJSONParam();
		}
		if(!checkSameParam()){ 
			return JSONTools.error("idA1 and idA2 are the same user", -1, 400);
		}

		
		//teste BD
		CommonFriendListBD flbd = new CommonFriendListBD();
		if(!flbd.checkId(idA1)){
			return JSONTools.error("id not exist in BD", -1, 400);
		}
		if(!flbd.checkId(idA2)){
			return JSONTools.error("id not exist in BD", -1, 400);
		}
		if(!flbd.commonFriendList(idA1, idA2)){
			return JSONTools.error("idA1 and idA2 have not common friend", -1, 400);
		}

		
		return flbd.getCommonFriendList(idA1, idA2);
		
	}

}
