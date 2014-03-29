package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import bd.CreateUserBD;

import services.CreateUserServices;
import services.JSONServices;
import services.tools.JSONTools;
import services.tools.RegexTools;

/**
 * Servlet implementant la class CreateUserServlet
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * Méthode qui recueille les informations entrées par l'utilisateur sur le formulaire.
	 * @param request Interface pour fournir des informations pour les servlets HTTP demandé.
	 * @param reponse Interface HTTP pour fournir des fonctionnalités spécifiques à l'envoi d'une réponse.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// Recupere la hashmap de l'URL et on ouvre  la reponse
		Map<String,String[]> map = request.getParameterMap();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();


		// Creation service CreateUser
		CreateUserServices cus = new CreateUserServices(map);

		// Recuperation des JSONs et des tests
		JSONObject success = null;
		try {
			success=cus.isSuccess();
			out.print(success);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print("JSONException :\n"+e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print("ClassNotFoundException :\n"+e.toString());

		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print("SQLException :\n"+e.toString());

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print("NoSuchAlgorithmException :\n"+e.toString());

		}

	}
}
