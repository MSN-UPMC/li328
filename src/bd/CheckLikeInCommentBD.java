package bd;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import services.tools.DatabaseTool;

public class CheckLikeInCommentBD {

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

	public boolean checkLike(String key, String idUser, String idCom) throws UnknownHostException, MongoException {
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
			if(cursor.hasNext()) return true;
			else return false;
		}
		finally {
			cursor.close();
			mongoClient.close();
		}

	}
	
	
}
