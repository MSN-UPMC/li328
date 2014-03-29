package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import services.tools.DatabaseTool;

/**
 * La class IDFromKeySessionBD
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class IDFromKeySessionBD {

	/**
	 * Methode qui verifie la presence de la cle de session dans la base de donnee et verifie aussi si cette session est active.
	 * @param key La cle de session.
	 * @return Retourne true si la cle de session est presente dans la base de donnee, et est active, false sinon.
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
	 * Methode qui permet d'avoir le id de l'utilisateur, via la cle de session.
	 * @param key La cle de session.
	 * @return Retourne un JSONObject contenant le id de l'utilisateur.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws JSONException
	 */
	public JSONObject getID(String key) throws SQLException, ClassNotFoundException, JSONException {
		// TODO Auto-generated method stub
		Connection connexion=null;
		PreparedStatement preparedStatement=null;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT ID FROM user where login = (SELECT login FROM session  where keySession=? );");
		preparedStatement.setString( 1, key);

		ResultSet resultat = preparedStatement.executeQuery();
		
		JSONObject obj = new JSONObject();

		if(resultat.next()){			
			obj.put("ID", resultat.getInt(1));
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
