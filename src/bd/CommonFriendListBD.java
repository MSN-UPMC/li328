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
 * La class CommonFriendListBD
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class CommonFriendListBD {

	
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
	 * Méthode qui verifie si les deux utilisateurs ont des amis en commun.
	 * @param idA Le id du premier utilisateur.
	 * @param idB Le id du second utilisateur.
	 * @return Retourne vrai si les deux utilisateurs ont au moins un amis en commun, false sinon.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean commonFriendList(int idA1, int idA2) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		boolean isOk=false;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT u.ID FROM user u, friends f1, friends f2 where f1.idFriendA=? and f2.idFriendA=? and f1.idFriendB=u.ID and f2.idFriendB=u.ID;");
		preparedStatement.setInt( 1, idA1);
		preparedStatement.setInt( 2, idA2);

		
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
	 * Méthode qui donne la liste des amis en commun.
	 * @param idA1 Le id du premier utilisateur.
	 * @param idA2 Le id du second utilisateur.
	 * @return Retourne un JSONObject contenant la liste des amis en commun des deux utilisateurs.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws JSONException
	 */
	public JSONObject getCommonFriendList(int idA1, int idA2) throws SQLException, ClassNotFoundException, JSONException{
		Connection connexion=null;
		PreparedStatement preparedStatement=null;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT u.ID FROM user u, friends f1, friends f2 where f1.idFriendA=? and f2.idFriendA=? and f1.idFriendB=u.ID and f2.idFriendB=u.ID;");
		preparedStatement.setInt( 1, idA1);
		preparedStatement.setInt( 2, idA2);

		ResultSet resultat = preparedStatement.executeQuery();

		JSONObject obj = new JSONObject();
		List<Integer> liste = new ArrayList<Integer>();
		while(resultat.next()){
			liste.add(resultat.getInt(1));
		}
		obj.put("idCommonFriend", liste);
		if(connexion!=null){
			connexion.close();
		}
		if(preparedStatement!=null){
			preparedStatement.close();
		}


		return obj;		
	}

	
}
