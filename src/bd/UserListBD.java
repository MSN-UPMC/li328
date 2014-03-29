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

public class UserListBD {

	public JSONObject getUserList() throws SQLException, ClassNotFoundException, JSONException {
		Connection connexion=null;
		PreparedStatement preparedStatement=null;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT u.ID FROM user u;");

		ResultSet resultat = preparedStatement.executeQuery();

		JSONObject obj = new JSONObject();
		List<Integer> liste = new ArrayList<Integer>();
		while(resultat.next()){
			liste.add(resultat.getInt(1));
		}
		obj.put("user", liste);
		if(connexion!=null){
			connexion.close();
		}
		if(preparedStatement!=null){
			preparedStatement.close();
		}


		return obj;		
	}

	public boolean userList() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		boolean isOk=false;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT u.ID FROM user u;");

		
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
}
