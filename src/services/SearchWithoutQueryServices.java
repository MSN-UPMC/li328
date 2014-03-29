package services;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import services.tools.JSONTools;

import bd.SearchWithoutQueryBD;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * Service implémentant la class SearchWithoutQueryServices
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class SearchWithoutQueryServices {

	/**
	 * Methode qui retourne une liste de JSONObject
	 * @return JSONObject Retourne soit une liste de JSONObject success lors d'un succès, soit une liste JSONObject erreur si une erreur est détectée.
	 * @throws UnknownHostException
	 * @throws MongoException
	 * @throws JSONException
	 */
	public List<JSONObject> isSuccess() throws UnknownHostException, MongoException, JSONException {
		// TODO Auto-generated method stub
		
		
		SearchWithoutQueryBD swqbd = new SearchWithoutQueryBD();
		
		
		return swqbd.getComments();
	}

}
