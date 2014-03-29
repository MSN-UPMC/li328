package bd;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * La class SearchWithoutQueryBD
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class SearchWithoutQueryBD {

	/**
	 * Methode qui permet d'avoir les commentaires
	 * @return Retourne une liste de JSONObject contenant des commentaires.
	 * @throws UnknownHostException
	 * @throws MongoException
	 */
	public List<JSONObject> getComments() throws UnknownHostException, MongoException {
		// TODO Auto-generated method stub

		List<JSONObject> listeComments = new ArrayList<JSONObject>();
		
		Mongo mongoClient = new Mongo();
		DB db = mongoClient.getDB("social");
		DBCollection coll = db.getCollection("comments");	
		BasicDBObject sortRequest = new BasicDBObject("date", -1);
		DBCursor cursor = coll.find().sort(sortRequest);
		JSONObject obj ;


		try {
			while(cursor.hasNext()) {
				obj= new JSONObject(cursor.next().toMap()) ;
				listeComments.add(obj);
			}
		} finally {
			cursor.close();
		}
		

		mongoClient.close();
		
		return listeComments;
	}




}
