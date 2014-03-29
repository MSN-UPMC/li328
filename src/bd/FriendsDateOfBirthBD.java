package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import services.tools.DatabaseTool;


/**
 * La class FriendsDateOfBirthBD
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class FriendsDateOfBirthBD {

	/**
	 * Méthode qui vérifie la présence de l'utilisateur dans la base de donnée.
	 * @param idA Le id de l'utilisateur.
	 * @return Retourne vrai si l'utilisateur est présent dans la base de donnée, false sinon.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean checkId(int idA) throws SQLException, ClassNotFoundException {
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		boolean isOk=false;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT * FROM user where ID=?;");
		preparedStatement.setInt( 1, idA );

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
	 * Méthode qui liste la date d'anniversaire des amis de l'utilisateur.
	 * @param id Le id de l'utilisateur.
	 * @return Retourne un JSONObject contenant la liste des dates d'anniversaire de l'utilisateur.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws JSONException
	 */
	public JSONObject getListDateOfBirth(int id) throws SQLException, ClassNotFoundException, JSONException {
		// TODO Auto-generated method stub
		
		Connection connexion=null;
		PreparedStatement preparedStatement=null;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT u.ID FROM friends f, user u where f.idFriendA=? and f.idFriendB=u.ID and MONTH(u.dateOfBirth)=MONTH(NOW()) and DAY(u.dateOfBirth)=DAY(NOW());");
		preparedStatement.setInt( 1, id);

		ResultSet resultat = preparedStatement.executeQuery();

		JSONObject obj = new JSONObject();
		List<Integer> liste = new ArrayList<Integer>();
		while(resultat.next()){
			liste.add(resultat.getInt(1));
		}
		obj.put("Date of birth's list of friend", liste);
		if(connexion!=null){
			connexion.close();
		}
		if(preparedStatement!=null){
			preparedStatement.close();
		}


		return obj;	
	}
	
}
