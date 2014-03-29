package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import services.tools.DatabaseTool;

/**
 * La class DeleteFriendBD
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class DeleteFriendBD {

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
	 * Méthode qui vérifie si deux utilisateurs sont des amis.
	 * @param idA Le id du premier utilisateur.
	 * @param idB Le id du second utilisateur.
	 * @return Retourne vrai si les deux utilisateurs sont des amis, false sinon.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean checkFriend(int idA, int idB) throws SQLException, ClassNotFoundException {
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		boolean isOk=false;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT * FROM friends where idFriendA=? AND idFriendB=?;");
		preparedStatement.setInt( 1, idA );
		preparedStatement.setInt( 2, idB );

		
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
	 * Méthode qui effectue l'ajout d'amis.
	 * @param idA Le id du premier utilisateur.
	 * @param idB Le id du second utilisateur.
	 * @return Retourne true si la suppression a bien été effectué, false sinon.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */	
	public boolean deleteFriend(int idA, int idB) throws SQLException, ClassNotFoundException {
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		boolean ok=false;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "DELETE FROM friends where idFriendA=? AND idFriendB=?;");
		preparedStatement.setInt( 1, idA );
		preparedStatement.setInt( 2, idB );

		
		int statut = preparedStatement.executeUpdate();

		if(statut==0) ok=false;
		if(statut==1) ok=true;
		
		return ok;
	}
	
}
