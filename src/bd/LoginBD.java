package bd;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import services.factory.HashFactory;
import services.tools.DatabaseTool;

/**
 * La class LoginBD
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class LoginBD {


	/**
	 * Methode qui verifie le login et le mot de passe
	 * @param email L'email de l'utilisateur.
	 * @param password Le mot de passe de l'utilisateur.
	 * @return Retourne true si l'utilisateur existe dans la base de donnee, false sinon.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean checkLoginAndPassword(String email, String password) throws SQLException, ClassNotFoundException, UnsupportedEncodingException, NoSuchAlgorithmException{
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		boolean isOk=false;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT * FROM User where login=? AND passwd=AES_ENCRYPT(?,?);");
		preparedStatement.setString( 1, email );
		preparedStatement.setString( 2, password );
		preparedStatement.setString( 3, HashFactory.hashMd5Statique(password) );


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
	 * Methode qui creee une session
	 * @param email L'email de l'utilisateur.
	 * @param duration La duree de la session.
	 * @param key Le cle de session.
	 * @return Retourne true si la session a bient ete cree, false sinon. 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean createLogin(String email,int duration, String key) throws SQLException, ClassNotFoundException, UnsupportedEncodingException, NoSuchAlgorithmException{
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		boolean ok=false;

		connexion = DatabaseTool.getMySQLConnection();
		preparedStatement = connexion.prepareStatement( "INSERT INTO session (login,keySession,startTime,endTime,isConnected) VALUES(?,?,NOW(),DATE_ADD(NOW(),INTERVAL ? HOUR),1);" );
		preparedStatement.setString( 1, email );
		preparedStatement.setString( 2, key);
		preparedStatement.setInt( 3, duration );



		int statut = preparedStatement.executeUpdate();
		if(statut==0) ok=false;
		if(statut==1) ok=true;
		
		return ok;

		
	}


}
