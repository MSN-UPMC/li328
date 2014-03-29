package bd;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import services.tools.DatabaseTool;

public class SearchMapReduceCommentBD {

	public boolean checkKey(String key) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Connection connexion=null;
		PreparedStatement preparedStatement=null;
		boolean isOk=false;

		connexion = DatabaseTool.getMySQLConnection();

		preparedStatement = connexion.prepareStatement( "SELECT * FROM session where keySession=? AND isConnected=1;");
		preparedStatement.setString( 1, key);

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



	public List<JSONObject> getComments(String word) throws UnknownHostException, MongoException, SQLException, ClassNotFoundException, JSONException {
		// TODO Auto-generated method stub
		List<JSONObject> listeComments = new ArrayList<JSONObject>();

		Mongo mongoClient = new Mongo();
		DB db = mongoClient.getDB("social");
		DBCollection coll = db.getCollection("comments");	

		String m="function wordMap(){"+
				"var text = this.comment;"+
				"var words = text.match(/\\w+/g);"+
				"var tf = new Array();"+
				"for( var i=0; i< words.length ; i++ ){"+
				"if( tf[words[i]] == null){"+
				"tf[words[i]]=1;"+
				"}"+
				"else{"+	
				"tf[words[i]]++;"+
				"}"+
				"}"+
				"for( var i=0; i< words.length ; i++ ){"+
				"emit(this._id, { word : words[i], tf : tf[words[i]] } )}};";

		String r="function wordReduce(key, values){"+
				"return ( { \"tfs\" : values } )};";

		MapReduceOutput out = coll.mapReduce(m,r,null,MapReduceCommand.OutputType.INLINE,null);

		String[] tabWords = word.split(" ");
		SortedMap<String,String> map = new TreeMap<String,String>();


		for ( DBObject obj : out.results()){

			JSONObject jsonObj = new JSONObject(obj.toMap());


			String idObj = jsonObj.get("_id").toString();
			JSONObject jsonValue = new JSONObject(jsonObj.get("value").toString());
			JSONArray tfsTab = new JSONArray(jsonValue.get("tfs").toString());


			for(int i=0; i<tfsTab.length();i++){
				JSONObject tfsObj = tfsTab.getJSONObject(i);
				for(String w : tabWords){
					if(tfsObj.get("word").equals(w)){
						String nbtf = tfsObj.get("tf").toString();
						map.put(idObj,nbtf);
					}
				}
			}



		}

		mongoClient.close();


		Iterator iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			JSONObject objToAdd = new JSONObject();
			objToAdd.put("_id",key.toString());
			listeComments.add(objToAdd);
		}


		return listeComments;
	}


}
