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
 * La class UpdateDateOfBirthBD
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class UpdateDateOfBirthBD {

	/**
	 * Méthode qui vérifie la présence de l'utilisateur dans la base de donnée.
	 * @param id Le id de l'utilisateur.
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
	 * Methode qui fait la mise a jour de la date de naissance de l'utilisateur.
	 * @param id Le id de l'utilisateur.
	 * @param date La date de naissance de l'utilisateur.
	 * @return Retourne true si la mise a jour a bien ete faite, false sinon.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws JSONException
	 */
	public boolean updateDateOfBirth(int id, String date) throws SQLException, ClassNotFoundException, JSONException {
		// TODO Auto-generated method stub
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		boolean ok=false;

		connexion = DatabaseTool.getMySQLConnection();
		preparedStatement = connexion.prepareStatement( "UPDATE user set dateOfBirth=? where ID=?;" );
		preparedStatement.setString( 1, date);
		preparedStatement.setInt( 2, id );



		int statut = preparedStatement.executeUpdate();
		if(statut==0) ok=false;
		if(statut==1) ok=true;
		
		return ok;
	
	}
	
	
}