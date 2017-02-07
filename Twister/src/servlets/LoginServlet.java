package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import bdd.UserTools;
import services.AuthTools;
 
/**
 * Servlet implementation class NewUserServlet
 */
public class LoginServlet extends HttpServlet {

	/**
	 * Default constructor.
	 */
	 public LoginServlet() {
		 
	 }
 
	 /**
	 * This method will handle all POST request.
	 */
	 protected void doPost(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
	 
		String login = request.getParameter("login");
		String pwd = request.getParameter("pwd");
		
		JSONObject user = new JSONObject();
		user = UserTools.login(login, pwd);
		
	 	response.setContentType( "application/json" );
	 	//response.sendRedirect("/Gr2_VU/index.html");
	 	
		PrintWriter out = response.getWriter ();
		out.println(user);
		 
	 }
}