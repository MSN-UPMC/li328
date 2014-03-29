package services;

import java.sql.SQLException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import bd.AddFriendBD;

import services.tools.JSONTools;
/**
 * Service implémentant la class AddFriendServices
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class AddFriendServices {

	
	private int idA;
	private int idB;
	Map<String, String[]> map;
	/**
	 * Constructeur de la fonction AddFriendServices.
	 * @param map La map stockant les informations du formulaire.
	 */
	public AddFriendServices(Map<String, String[]> map) {
		// TODO Auto-generated constructor stub
	
		super();
		this.map = map;
		
		if(checkParam()){
			idA = Integer.parseInt(map.get("idA")[0]);
			idB = Integer.parseInt(map.get("idB")[0]);
		}
	}
	/**
	 * Méthode qui vérifie si les deux utilisateurs ne sont pas identiques.
	 * @return Retourne true si les deux utilisateurs sont différents, false sinon.
	 */
	private boolean checkSameParam(){
		if(idA==idB){
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
		if(!map.containsKey("idA")) return false;
		if(!map.containsKey("idB")) return false;
		
		return true;
	}
	/**
	 * Méthode qui retourne un JSONObject à l'erreur associée.
	 * @return Retourne un JSONObject erreur.
	 * @throws JSONException.
	 */
	private JSONObject getJSONParam() throws JSONException{
		if(map.isEmpty()) return JSONTools.error("No variables", -1, 400);
		if(!map.containsKey("idA")) return JSONTools.error("idA not defined", -1, 400);

		return JSONTools.error("idB not defined", -1, 400);
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
		if(!checkParam()) return getJSONParam();
		if(!checkSameParam()) return JSONTools.error("idA and idB are the same user", -1, 400);
		
		//teste BD
		AddFriendBD afbd = new AddFriendBD();
		if(!afbd.checkId(idA) || !afbd.checkId(idB))	return JSONTools.error("id not exist in BD", -1, 400);
		if(afbd.checkFriend(idA,idB)) return JSONTools.error("idA and idB are already friend",-1, 400);
		if(!afbd.addFriend(idA,idB)) return JSONTools.error("Friends are not insered",-1, 400);
		
		return JSONTools.ok(200);
		
	}

}
