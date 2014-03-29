package bd;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

import services.factory.HashFactory;
import services.tools.DatabaseTool;

/**
 * La class AddCommentBD
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class AddCommentBD {

	/**
	 * Méthode qui vérifie la présence de la clé de session dans la base de donnée.
	 * @param key La clé de session.
	 * @return Retourne true si la clé de session est présente dans la base de donnée et si la session est active, false sinon. 
	 * @throws SQLException
	 * @throws ClassNotFoundException
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
	 * Méthode qui permet d'obtenir le first name de l'utilisateur login.
	 * @param login Le login de l'utilisateur.
	 * @return Retourne le first name de l'utilisateur login.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private String getFirstName(String login) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		String name=null;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT firstName FROM user where login=? ;");
		preparedStatement.setString( 1, login);

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
	 * Méthode qui permet d'obtenir le last name de l'utilisateur login.
	 * @param login Le login de l'utilisateur.
	 * @return Retourne le last name de l'utilisateur login.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private String getName(String login) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		String name=null;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT name FROM user where login=? ;");
		preparedStatement.setString( 1, login);

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
	 * Méthode qui permet d'obtenir le login de l'utilisateur.
	 * @param key La clé de session.
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
	 * Méthode qui permet de faire une insertion des commentaires.
	 * @param key La clé de session.
	 * @param text Le texte à commenter.
	 * @return Retourne true si l'insertion s'est bien passé, false si login est null.
	 * @throws UnknownHostException
	 * @throws MongoException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean insertComment(String key, String text) throws UnknownHostException, MongoException, SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		String name,firstName;
		int idLogin;

		Mongo mongoClient = new Mongo();
		DB db = mongoClient.getDB("social");
		DBCollection coll = db.getCollection("comments");


		String login = getLogin(key);

		if(login==null) return false;
		else{
			name=getName(login);
			firstName=getFirstName(login);
			idLogin=getIdLogin(login);
		}


		BasicDBObject doc = new BasicDBObject("idLogin", idLogin);
		doc.append("name", name);
		doc.append("firstName", firstName);
		doc.append("comment", text);
		doc.append("date", new Date());

		coll.insert(doc);

		mongoClient.close();
		
		return true;
	}

	
	/**
	 * Méthode qui permet d'obtenir le id de l'utilisateur login.
	 * @param login Le login de l'utilisateur.
	 * @return Retourne le id de l'utilisateur.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private int getIdLogin(String login) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		int idLog=0;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT ID FROM user where login=? ;");
		preparedStatement.setString( 1, login);

		ResultSet resultat = preparedStatement.executeQuery();


		if(resultat.next()){
			idLog=resultat.getInt(1);
		}


		if(connexion!=null){
			connexion.close();
		}
		if(preparedStatement!=null){
			preparedStatement.close();
		}


		return idLog;
	}

}
