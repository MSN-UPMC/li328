package services;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoException;

import bd.AddCommentBD;
import bd.AddLikeCommentBD;

import services.tools.JSONTools;

public class AddLikeCommentServices {
	
	private Map<String,String[]> map;
	private String idCom;
	private String idUser;
	private String key;

		
	public AddLikeCommentServices(Map<String, String[]> map) {
		// TODO Auto-generated constructor stub
		this.map = map;

		if(checkParam()) {
			idCom = map.get("idCom")[0];
			idUser = map.get("idUser")[0];
			key = map.get("key")[0];


		}
	}
	
	
	private boolean checkParam(){
		if(map.isEmpty()) return false;
		if(!map.containsKey("idCom")) return false;
		if(!map.containsKey("idUser")) return false;
		if(!map.containsKey("key")) return false;


		return true;
	}

	private JSONObject getJSONParam() throws JSONException{
		if(map.isEmpty()) return JSONTools.error("No variables", -1, 400);
		if(!map.containsKey("idUser")) return JSONTools.error("idUser not defined", -1, 400);
		if(!map.containsKey("key")) return JSONTools.error("key not defined", -1, 400);


		return JSONTools.error("idCom not defined", -1, 400);
	}

	public JSONObject isSuccess() throws JSONException, SQLException, ClassNotFoundException, UnknownHostException, MongoException {
		// TODO Auto-generated method stub

		//Teste de la map si elle est vide et des parametres.
		if(!checkParam()) return getJSONParam();

		AddLikeCommentBD acbd = new AddLikeCommentBD();
		if(!acbd.checkKey(key)) return JSONTools.error("key not found or expired in the BD", -1, 400);
		
		if(!acbd.insertLike(key,idUser,idCom)) return JSONTools.error("Informations are not insered", 10000, 400);
	
		return JSONTools.ok(200);

	}

}
