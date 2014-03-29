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

import services.CheckFriendServices;

/**
 * Servlet implementation class AddFriendServlet
 */
public class CheckFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Recupere la hashmap de l'URL et on ouvre  la reponse
		Map<String,String[]> map = request.getParameterMap();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();


		// Creation service AddFriend
		CheckFriendServices cus = new CheckFriendServices(map);

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

		}
	}

}
