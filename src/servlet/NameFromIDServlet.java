package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoException;

import services.AddCommentServices;
import services.NameFromIDServices;

/**
 * Servlet implémentant la class NameFromIDServlet
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class NameFromIDServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * Méthode qui recueille les informations entrées par l'utilisateur sur le formulaire.
	 * @param request Interface pour fournir des informations pour les servlets HTTP demandés.
	 * @param reponse Interface HTTP pour fournir des fonctionnalitÃ©s spÃ©cifiques Ã  l'envoi d'une rÃ©ponse.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Recupere la hashmap de l'URL et on ouvre  la reponse
		Map<String,String[]> map = request.getParameterMap();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();


		// Creation service 
		NameFromIDServices cus = new NameFromIDServices(map);
		
		// Recuperation des JSONs et des tests
		JSONObject success = null;
		try {
			success=cus.isSuccess();
			out.print(success);

		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
