package services;

import java.sql.SQLException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import bd.AddFriendBD;
import bd.CheckFriendBD;

import services.tools.JSONTools;

public class CheckFriendServices {

	
	private int idA;
	private int idB;
	Map<String, String[]> map;
	
	public CheckFriendServices(Map<String, String[]> map) {
		// TODO Auto-generated constructor stub
	
		super();
		this.map = map;

		if(checkParam()){
			idA = Integer.parseInt(map.get("idA")[0]);
			idB = Integer.parseInt(map.get("idB")[0]);
		}
	}
	
	
	private boolean checkParam(){
		if(map.isEmpty()) return false;
		if(!map.containsKey("idA")) return false;
		if(!map.containsKey("idB")) return false;
		
		return true;
	}

	
	public JSONObject isSuccess() throws JSONException, SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		//Teste de la map si elle est vide et des parametres.
		
		//teste BD
		CheckFriendBD afbd = new CheckFriendBD();
		JSONObject obj = new JSONObject();
		if(afbd.checkFriend(idA,idB)){
			//Déjà amis
			obj.put("CodeHTTP", 400);
		}
		else{
			obj.put("CodeHTTP", 200);		
		}
		
		return obj;
		
	}

}
