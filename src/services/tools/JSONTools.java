package services.tools;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Cette classe permet de generer un JSONObject en cas d'erreur ou de bonne execution
 * @author M. Kamalraj, N. Mag-Stellon
 *
 */
public class JSONTools {

	/**
	 * Cette methode permet de generer un JSONObject en cas de bonne execution
	 * @param httpCode Le code HTTP
	 * @return Un JSONObject 
	 * @throws JSONException 
	 */
	public static JSONObject ok(int httpCode) throws JSONException{
		JSONObject obj = new JSONObject();
		
		obj.put("CodeHTTP", httpCode);


		return obj;
	}

	
	/**
	 * Cette classe permet de generer un JSONOject en cas de mauvaise execution
	 * @param msg Le message d'erreur
	 * @param codeError Le code d'erreur
	 * @param httpCode Le code HTTP
	 * @return Un JSONOBject
	 * @throws JSONException 
	 */
	public static JSONObject error(String msg, int codeError, int httpCode) throws JSONException{
		JSONObject obj = new JSONObject();

		obj.put("CodeError", codeError);
		obj.put("Message", msg);
		obj.put("CodeHTTP", httpCode);

		return obj;
	}

}
	