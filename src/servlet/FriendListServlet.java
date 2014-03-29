package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import services.FriendListServices;
import services.LoginServices;

/**
 * Servlet impl�mentant la class FriendListServlet
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class FriendListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	/**
	 * M�thode qui recueille les informations entr�es par l'utilisateur sur le formulaire.
	 * @param request Interface pour fournir des informations pour les servlets HTTP demand�s.
	 * @param reponse Interface HTTP pour fournir des fonctionnalités spécifiques à l'envoi d'une réponse.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		

		// Recupere la hashmap de l'URL et on ouvre  la reponse
		Map<String,String[]> map = request.getParameterMap();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		
		// creation service Login
		FriendListServices ls = new FriendListServices(map);
		
		
		// Recuperation des JSONs et des tests
		JSONObject success = null;
		try {
			success=ls.isSuccess();
			out.print(success);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print("JSONException :\n"+e.toString());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print("ClassNotFoundException :\n"+e.toString());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print("SQLException :\n"+e.toString());
		}
	}
}
