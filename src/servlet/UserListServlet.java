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
import services.UserListServices;


public class UserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		

		// Recupere la hashmap de l'URL et on ouvre  la reponse
		@SuppressWarnings("unchecked")
		Map<String,String[]> map = request.getParameterMap();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		
		// creation service Login
		UserListServices ls = new UserListServices();
		
		
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
