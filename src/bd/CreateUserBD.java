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
 * La class CreateUserBD
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class CreateUserBD {


	/**
	 * Méthode qui vérifie la présence du login dans la base de donnée.
	 * @param email Le email de l'utilisateur.
	 * @return Retourne true si l'utilisateur existe déjà, false sinon.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean checkLogin(String email) throws SQLException, ClassNotFoundException  {
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		boolean isOk=false;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT * FROM user where login=?;");
		preparedStatement.setString( 1, email );

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
	 * Méthode qui fait l'insertion d'un nouveau utilisateur dans la base de donnée.
	 * @param email Le email de l'utilisateur.
	 * @param password Le mot de passe de l'utilisateur.
	 * @param nom Le nom d l'utilisateur.
	 * @param prenom Le prénom de l'utilisateur.
	 * @return Retourne true si l'insertion a bien été effectué, false sinon.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean insertUser(String email, String password, String nom, String prenom) throws SQLException, ClassNotFoundException, UnsupportedEncodingException, NoSuchAlgorithmException  {
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		boolean ok=false;

		connexion = DatabaseTool.getMySQLConnection();
		preparedStatement = connexion.prepareStatement( "INSERT INTO user (login,passwd,name,firstName,registerDate) VALUES(?,AES_ENCRYPT(?,?),?,?,NOW());");
		preparedStatement.setString( 1, email );
		preparedStatement.setString( 2, password);
		preparedStatement.setString( 3, HashFactory.hashMd5Statique(password));
		preparedStatement.setString( 4, nom );
		preparedStatement.setString( 5, prenom );

		int statut = preparedStatement.executeUpdate();
		if(statut==0) ok=false;
		if(statut==1) ok=true;
		
		return ok;

	}

}
