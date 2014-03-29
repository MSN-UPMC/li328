package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import services.GetPrivateMessageByIDServices;
import services.WritePrivateMessageByIDServices;

import com.mongodb.MongoException;

/**
 * Servlet implementation class WritePrivateMessageByIDServlet
 */
public class WritePrivateMessageByIDServlet extends HttpServlet {
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


		// Creation service CreateUser
		WritePrivateMessageByIDServices cus = new WritePrivateMessageByIDServices(map);

		// Recuperation des JSONs et des tests
		JSONObject  success = null;


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
