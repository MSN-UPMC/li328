package bd;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import services.tools.DatabaseTool;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class WritePrivateMessageByIDBD {

	public boolean writePrivateMsg(String msg,String idExp, String idDest) throws UnknownHostException, MongoException {
		// TODO Auto-generated method stub
		
		Mongo mongoClient = new Mongo();
		DB db = mongoClient.getDB("social");
		DBCollection coll = db.getCollection("privatemessage");
		

		BasicDBObject doc = new BasicDBObject("idExp", idExp);
		doc.append("idDest", idDest);
		doc.append("msg", msg);
		doc.append("date", new Date());
		

		coll.insert(doc);

		mongoClient.close();
		
		return true;
		
		
		
	}

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


}
