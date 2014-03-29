package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import services.tools.DatabaseTool;

/**
 * La class NameFromIDBD
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class NameFromIDBD {

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
	 * Methode qui donne le nom de l'utilisateur via son id.
	 * @param id Le id de l'utilisateur
	 * @return Retourne true si l'operation a bien ete effectue, false sinon.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws JSONException
	 */
	public JSONObject getNameFromId(int id) throws SQLException, ClassNotFoundException, JSONException {
		// TODO Auto-generated method stub
		
		Connection connexion=null;
		PreparedStatement preparedStatement=null;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT name,firstName FROM user where ID=?;");
		preparedStatement.setInt( 1, id);

		ResultSet resultat = preparedStatement.executeQuery();
		
		JSONObject obj = new JSONObject();

		
		if(resultat.next()){
		
		obj.put("name", resultat.getString(1));
		obj.put("firstName", resultat.getString(2));
		
		}

		if(connexion!=null){
			connexion.close();
		}
		if(preparedStatement!=null){
			preparedStatement.close();
		}


		return obj;		
		
		
	}

}
