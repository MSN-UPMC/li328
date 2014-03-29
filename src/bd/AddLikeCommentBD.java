package bd;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

import services.tools.DatabaseTool;

public class AddLikeCommentBD {

	public boolean checkKey(String key) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		boolean isOk=false;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT * FROM session where keySession=? AND isConnected=1;");
		preparedStatement.setString( 1, key);

		ResultSet resultat = preparedStatement.executeQuery();


		if(resultat.next()) isOk=true; 


		if(connexion!=null){
			connexion.close();
		}
		if(preparedStatement!=null){
			preparedStatement.close();
		}


		return isOk;
	}

	public boolean insertLike(String key, String idUser, String idCom) throws UnknownHostException, MongoException, JSONException {
		// TODO Auto-generated method stub

		// Connexion a la BD
		Mongo mongoClient = new Mongo();
		DB db = mongoClient.getDB("social");
		DBCollection coll = db.getCollection("comments");

		// Verification de l'id
		BasicDBObject request = new BasicDBObject("_id", new ObjectId(idCom)).
									append("like", idUser);
		
		DBCursor cursor = coll.find(request);
		
		try {
			if(cursor.hasNext()) return false;
		}
		finally {
			cursor.close();
		}




		// Ajout de l'id dans le document 
		BasicDBObject requestAdd = new BasicDBObject("_id", new ObjectId(idCom));
		BasicDBObject updat = new BasicDBObject("$push", new BasicDBObject("like",idUser));
		coll.update(requestAdd, updat);

		mongoClient.close();

		return true;
	}

}
