package services;

import java.sql.SQLException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import services.tools.JSONTools;
import bd.UserListBD;

public class UserListServices {


	public UserListServices() {
		// TODO Auto-generated constructor stub
	}
	

	
	public JSONObject isSuccess() throws JSONException, SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		//Teste de la map si elle est vide et des parametres.

		
		//teste BD
		UserListBD ulbd = new UserListBD();
		
		if(!ulbd.userList()){
			return JSONTools.error("no user in data base", -1, 400);
		}
		
		return ulbd.getUserList();
		
	}

	
}
