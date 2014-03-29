package services;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoException;

import bd.AddCommentBD;
import bd.CreateUserBD;

import services.tools.JSONTools;

/**
 * Service implémentant la class AddCommentServices
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class AddCommentServices {
	
	private Map<String, String[]> map;
	private String key,text;

	/**
	 * Constructeur de la fonction AddCommentServices.
	 * @param map La map stockant les informations du formulaire.
	 */
	public AddCommentServices(Map<String, String[]> map) {
		// TODO Auto-generated constructor stub
		super();
		this.map = map;

		if(checkParam()) {
			text = map.get("text")[0];
			key = map.get("key")[0];
		}
	}

	/**
	 * Methode de verification des paramètres.
	 * @return Retourne true si les paramètres sont corrects, false sinon. 
	 */
	private boolean checkParam() {
		if(map.isEmpty()) return false;
		if(!map.containsKey("text")) return false;
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

		return JSONTools.error("text not defined", -1, 400);
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
		AddCommentBD acbd = new AddCommentBD();
		if(!acbd.checkKey(key)) return JSONTools.error("key not found or expired in the BD", -1, 400);
		else{
			if(!acbd.insertComment(key, text)){
				return JSONTools.error("Informations are not insered", 10000, 400);
			}
		}
		//Retourne ok si les teste precedent sont ok
		return JSONTools.ok(200);
	}
}
