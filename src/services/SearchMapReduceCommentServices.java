package services;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoException;

import bd.SearchMapReduceCommentBD;
import bd.SearchWithoutQueryFriendBD;

import services.tools.JSONTools;

public class SearchMapReduceCommentServices {
	
	private Map<String, String[]> map;
	private String key;
	private String word;


	public SearchMapReduceCommentServices(Map<String, String[]> map) {
		// TODO Auto-generated constructor stub
		super();
		this.map = map;

		if(checkParam()) {
			key = map.get("key")[0];
			word = map.get("word")[0];

		}
	}
	
	private boolean checkParam() {
		if(map.isEmpty()) return false;
		if(!map.containsKey("key")) return false;
		if(!map.containsKey("word")) return false;


		return true;
	}
	
	private JSONObject getJSONParam() throws JSONException {
		// TODO Auto-generated method stub
		if(map.isEmpty()) return JSONTools.error("No variables", -1, 400);
		if(!map.containsKey("word")) return JSONTools.error("No word", -1, 400);


		return JSONTools.error("key not defined", -1, 400);
	}

	public List<JSONObject> isSuccess() throws JSONException, SQLException, ClassNotFoundException, UnknownHostException, MongoException {
		// TODO Auto-generated method stub

		//Teste de la map si elle est vide et des parametres.
		if(!checkParam()){
			List<JSONObject> l = new ArrayList<JSONObject>();
			l.add(getJSONParam());
			return l;
		}
		
		// test sql
		SearchMapReduceCommentBD swqfbd = new SearchMapReduceCommentBD();
		if(!swqfbd.checkKey(key)) {
			List<JSONObject> l = new ArrayList<JSONObject>();
			l.add(JSONTools.error("key not found or expired in the BD", -1, 400));
			return l;
		}
	
		return swqfbd.getComments(word);

	}

}
