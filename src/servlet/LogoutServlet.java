package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import services.JSONServices;
import services.LoginServices;
import services.LogoutServices;
import services.factory.HashFactory;
import services.tools.JSONTools;
import services.tools.RegexTools;

/**
 * Servlet implémentant la class LogoutServlet
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class LogoutServlet extends HttpServlet{
	
	/**
	 * Méthode qui recueille les informations entrées par l'utilisateur sur le formulaire.
	 * @param request Interface pour fournir des informations pour les servlets HTTP demandés.
	 * @param reponse Interface HTTP pour fournir des fonctionnalitÃ©s spÃ©cifiques Ã  l'envoi d'une rÃ©ponse.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recupere la hashmap de l'URL et on ouvre  la reponse
		Map<String,String[]> map = request.getParameterMap();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();


		// creation service Login
		LogoutServices ls = new LogoutServices(map);


		// Recuperation des JSONs et des tests
		JSONObject success = null;
		try {
			success=ls.isSuccess();
			out.print(success);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print("JSONException :\n"+e.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print("UnsupportedEncodingException :\n"+e.toString());

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print("NoSuchAlgorithmException :\n"+e.toString());
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