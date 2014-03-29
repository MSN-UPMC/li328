package services.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;
/**
 * La class MysqlTools
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class MysqlTools {
	/**
	 * Methode qui permet de savoir si la session est active.
	 * @param key La clé de session.
	 * @return Retourne true si la session est active, false sinon.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static boolean checkSession(String key) throws SQLException, ClassNotFoundException{
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		boolean isOk=false;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT * FROM session where keySession=? AND isConnected=1 AND endTime>=NOW();");
		preparedStatement.setString( 1, key);
		ResultSet resultat = preparedStatement.executeQuery();


		if(resultat.next()) isOk=true; 
		else{
			preparedStatement = connexion.prepareStatement( "UPDATE session set isConnected=-1 where keySession=?;");
			preparedStatement.setString( 1, key);
			resultat = preparedStatement.executeQuery();
		}


		if(connexion!=null){
			connexion.close();
		}
		if(preparedStatement!=null){
			preparedStatement.close();
		}


		return isOk;
	}

}
