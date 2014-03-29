package services;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoException;

import services.tools.JSONTools;

import bd.GetPrivateMessageByIDBD;
import bd.WritePrivateMessageByIDBD;

public class WritePrivateMessageByIDServices {
	
	private Map<String, String[]> map;
	private String key,idExp,idDest,msg;


	public WritePrivateMessageByIDServices(Map<String, String[]> map) {
		// TODO Auto-generated constructor stub
		
		super();
		this.map = map;

		if(checkParam()) {
			idExp = map.get("idExp")[0];
			idDest = map.get("idDest")[0];
			key = map.get("key")[0];
			msg = map.get("msg")[0];

		}
	}

	
	
	private boolean checkParam() {
		if(map.isEmpty()) return false;
		if(!map.containsKey("idDest")) return false;
		if(!map.containsKey("key")) return false;
		if(!map.containsKey("idExp")) return false;
		if(!map.containsKey("msg")) return false;



		return true;
	}

	
	public JSONObject isSuccess() throws JSONException, UnknownHostException, MongoException, SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		WritePrivateMessageByIDBD acbd = new WritePrivateMessageByIDBD();
		
		if(!acbd.checkKey(key)) return JSONTools.error("key not found or expired in the BD", -1, 400);

		
		//Retourne ok si les teste precedent sont ok
		if(!acbd.writePrivateMsg(msg, idExp,idDest)) return JSONTools.error("Informations are not insered", 10000, 400);


	
			return JSONTools.ok(200);
	}

}
