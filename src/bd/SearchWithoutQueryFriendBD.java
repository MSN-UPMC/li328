package bd;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import services.tools.DatabaseTool;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;


/**
 * La class SearchWithoutQueryFriendBD
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class SearchWithoutQueryFriendBD {

	/**
	 * Methode qui permet d'avoir le id d'un utilisateur a partir de son login.
	 * @param login Le logine de l'utilisateur.
	 * @return Retourne le id de l'utilisateur.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private String getIdLogin(String login) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		String idLog=null;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT ID FROM user where login=? ;");
		preparedStatement.setString( 1, login);

		ResultSet resultat = preparedStatement.executeQuery();


		if(resultat.next()){
			idLog=resultat.getString(1);
		}


		if(connexion!=null){
			connexion.close();
		}
		if(preparedStatement!=null){
			preparedStatement.close();
		}


		return idLog;
	}

	/**
	 * Methode qui permet d'avoir le login d'un utilisateur a partir de sa cle de session.
	 * @param key La cle de session.
	 * @return Retourne le login de l'utilisateur.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private String getLogin(String key) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		String name=null;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT login FROM session where keySession=? AND isConnected=1;");
		preparedStatement.setString( 1, key);

		ResultSet resultat = preparedStatement.executeQuery();


		if(resultat.next()){
			name=resultat.getString(1);
		}


		if(connexion!=null){
			connexion.close();
		}
		if(preparedStatement!=null){
			preparedStatement.close();
		}


		return name;
	}

	/**
	 * Methode qui permet d'avoir les amis d'un utilisateur.
	 * @param id Le id de l'utilisateur.
	 * @return Retourne une liste de id des amis de l'utilisateur. 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Integer> getFriend(String id) throws SQLException, ClassNotFoundException{
		Connection connexion=null;
		PreparedStatement preparedStatement=null;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT idFriendB FROM friends where idFriendA=? ;");
		preparedStatement.setString( 1, id);

		ResultSet resultat = preparedStatement.executeQuery();

		List<Integer> l = new ArrayList<Integer>();
		while(resultat.next()){
			l.add(resultat.getInt(1));
		}


		if(connexion!=null){
			connexion.close();
		}
		if(preparedStatement!=null){
			preparedStatement.close();
		}


		return l;		
	}

	/**
	 * Methode qui verifie la presence de la cle de session dans la base de donnee.
	 * @param key La cle de session.
	 * @return Retourne true si la cle est presente dans la base de donnee, false sinon.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
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

	/**
	 * Methode qui permet d'avoir les commentaires.
	 * @param key La cle de session.
	 * @return Retourne une liste de JSONObject contenant des commentaires.
	 * @throws UnknownHostException
	 * @throws MongoException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<JSONObject> getComments(String key) throws UnknownHostException, MongoException, SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<JSONObject> listeComments = new ArrayList<JSONObject>();

		Mongo mongoClient = new Mongo();
		DB db = mongoClient.getDB("social");
		DBCollection coll = db.getCollection("comments");

		String login = getLogin(key);
		String id = getIdLogin(login);
		List<Integer> listeFriend = getFriend(id);

		BasicDBObject query = new BasicDBObject("idLogin",new BasicDBObject("$in", listeFriend));
		BasicDBObject sort = new BasicDBObject("date",-1);
		DBCursor cursor = coll.find(query);
		cursor.sort(sort);
		
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
