package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bdd.FriendTools;
 
public class AddFriendServlet extends HttpServlet {
 
	 /**
	 * Default constructor.
	 */
	 public AddFriendServlet() {
		 
	 }
 
	 /**
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		String login1 = request.getParameter("login1");
		String login2 = request.getParameter("login2");
		
		JSONObject res = new JSONObject();
		
		response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
		
		res = FriendTools.addFriend(login1, login2);
		out.println(res);

	 }
	 

}
