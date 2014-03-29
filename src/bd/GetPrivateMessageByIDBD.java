package bd;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import services.tools.DatabaseTool;

public class GetPrivateMessageByIDBD {
	
	
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

	public List<JSONObject> getPrivateMsg(String key, String id) throws UnknownHostException, MongoException {
		// TODO Auto-generated method stub
		
		List<JSONObject> listeComments = new ArrayList<JSONObject>();

		
		Mongo mongoClient = new Mongo();
		DB db = mongoClient.getDB("social");
		DBCollection coll = db.getCollection("privatemessage");

		BasicDBObject request = new BasicDBObject("idExp", id);
		DBCursor cursor = coll.find(request);
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
