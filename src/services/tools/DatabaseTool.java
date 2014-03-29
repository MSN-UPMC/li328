package services.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * La class DatabaseTool
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class DatabaseTool {

	private DataSource dataSource;

	/**
	 * Constructeur de la fonction DatabaseTool.
	 * @param jndiname
	 * @throws SQLException
	 */
	public DatabaseTool(String jndiname) throws SQLException {
		try {
			
			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/" +
					jndiname);
		} catch (NamingException e) {
			// Handle error that it’s not configured in JNDI.
			throw new SQLException(jndiname + " is missing in JNDI! : "+e.getMessage());
		}
	}
 
	/**
	 * Methode qui permet de faire une connexion.
	 * @return Retourne une connexion.
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	/**
	 * Methode qui permet de faire une connexion MySQL.
	 * @return Retourne une connexion.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Connection getMySQLConnection() throws SQLException, ClassNotFoundException
	{
		Class.forName( "com.mysql.jdbc.Driver" );
		if (DBStatic.mysql_pooling==false)
		{
			return( DriverManager.getConnection("jdbc:mysql://"+DBStatic.mysql_host
					+"/"
					+DBStatic.mysql_db, DBStatic.mysql_username,DBStatic.mysql_password));
		}
		else
		{
			DatabaseTool database=new DatabaseTool("jdbc/db");
			return(database.getConnection());
		}
	}


}
