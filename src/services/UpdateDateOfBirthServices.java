package services;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import services.tools.JSONTools;
import services.tools.RegexTools;
import bd.FriendsDateOfBirthBD;
import bd.UpdateDateOfBirthBD;

public class UpdateDateOfBirthServices {


	private int id;
	private String date;
	Map<String, String[]> map;
	
	public UpdateDateOfBirthServices(Map<String, String[]> map) {
		// TODO Auto-generated constructor stub
	
		super();
		this.map = map;
		
		if(checkParam()){
			id = Integer.parseInt(map.get("id")[0]);
			date = map.get("date")[0];
		}
	}
	

	private boolean checkParam(){
		if(map.isEmpty()) return false;
		if(!map.containsKey("id")) return false;
		if(!map.containsKey("date")) return false;

		return true;
	}
	
	
	private boolean checkDateOfBirth() {
		if(!RegexTools.verifDateOfBirth(date)) return false;
		return true;
	}
	
	private JSONObject getJSONupdateDateOfBirth() throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException{

		JSONObject obj = new JSONObject();
		obj.put("ID", id);
		obj.put("dateOfBirth", date);
		
		return obj;
	}
	
	private JSONObject getJSONParam() throws JSONException{
		if(map.isEmpty()) return JSONTools.error("No variables", -1, 400);
		if(!map.containsKey("date")) return JSONTools.error("date not defined", -1, 400);
		return JSONTools.error("id not defined", -1, 400);
	}	
	
	public JSONObject isSuccess() throws JSONException, SQLException, ClassNotFoundException, UnsupportedEncodingException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		//Teste de la map si elle est vide et des parametres.
		if(!checkParam()) return getJSONParam();
		
		//teste la date de naissance
		if(!checkDateOfBirth()) return JSONTools.error("dateOfBirth have a wrong syntax", -1, 400);
		
		//teste BD
		UpdateDateOfBirthBD udobbd = new UpdateDateOfBirthBD();
		if(!udobbd.checkId(id))	return JSONTools.error("id not exist in BD", -1, 400);
		if(!udobbd.updateDateOfBirth(id, date)){
			return JSONTools.error("Informations are not insered", 10000, 400);
		}
		
		
		return getJSONupdateDateOfBirth();
		
	}

	
	
	
}
