package services;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoException;

import bd.AddCommentBD;
import bd.GetPrivateMessageByIDBD;

import services.tools.JSONTools;

public class GetPrivateMessageByIDServices {
	
	private Map<String, String[]> map;
	private String key,id;

	public GetPrivateMessageByIDServices(Map<String, String[]> map) {
		// TODO Auto-generated constructor stub
		
		super();
		this.map = map;

		if(checkParam()) {
			id = map.get("id")[0];
			key = map.get("key")[0];
		}
	}

	
	private boolean checkParam() {
		if(map.isEmpty()) return false;
		if(!map.containsKey("id")) return false;
		if(!map.containsKey("key")) return false;

		return true;
	}


//	private List<JSONObject> getJSONParam() throws JSONException {
//		// TODO Auto-generated method stub
////		if(map.isEmpty()) return JSONTools.error("No variables", -1, 400);
////		if(!map.containsKey("key")) return JSONTools.error("key not defined", -1, 400);
////		List<JSONObject> l = new ArrayList<JSONObject>();
////		l.add(JSONTools.error("id not defined", -1, 400));
//
////		return l;
//	}
	
	
	public List<JSONObject> isSuccess() throws JSONException, SQLException, ClassNotFoundException, UnknownHostException, MongoException {
		// TODO Auto-generated method stub
		
		
		
		GetPrivateMessageByIDBD acbd = new GetPrivateMessageByIDBD();
		
		//Retourne ok si les teste precedent sont ok
		return acbd.getPrivateMsg(key, id);
		
	}
	
	

}
