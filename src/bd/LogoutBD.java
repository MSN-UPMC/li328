package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import services.factory.HashFactory;
import services.tools.DatabaseTool;

/**
 * La class LogoutBD
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class LogoutBD {

	/**
	 * Methode qui verifie la presence de la cle de session dans la base de donnee.
	 * @param key La cle de session.
	 * @return Retourne true si la cle est presente dans la base de donnee, false sinon.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean checkKey(String key) throws ClassNotFoundException, SQLException {
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		boolean isOk=false;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT * FROM session where keySession=? ;");
		preparedStatement.setString( 1, key );

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
	 * Methode qui permet de rendre une session inactive.
	 * @param key La cle de session.
	 * @return Retourne true si la deconnexion a bien ete faite, false sinon.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean deleteKey(String key) throws ClassNotFoundException, SQLException {
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		boolean ok=false;

		connexion = DatabaseTool.getMySQLConnection();
		preparedStatement = connexion.prepareStatement( "UPDATE session set isConnected=-1 where keySession=?;" );
		preparedStatement.setString( 1, key );


		int statut = preparedStatement.executeUpdate();
		if(statut==0) ok=false;
		if(statut==1) ok=true;
		
		return ok;
	}
	
	/**
	 * Methode qui met a jour la fin de la duree de session.
	 * @param key La cle de session.
	 * @return Retourne true si la mise a jour a bien ete faite, false sinon.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean updateEndTime(String key) throws ClassNotFoundException, SQLException {
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		boolean ok=false;

		connexion = DatabaseTool.getMySQLConnection();
		preparedStatement = connexion.prepareStatement( "UPDATE session set endTime=NOW() where keySession=?;" );
		preparedStatement.setString( 1, key );


		int statut = preparedStatement.executeUpdate();
		if(statut==0) ok=false;
		if(statut==1) ok=true;
		
		return ok;
	}

	

}
