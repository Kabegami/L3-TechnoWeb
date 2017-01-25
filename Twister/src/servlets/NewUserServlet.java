package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bdd.UserTools;
 
/**
 * Servlet implementation class NewUserServlet
 */
public class NewUserServlet extends HttpServlet {
 
	 /**
	 * Default constructor.
	 */
	 public NewUserServlet() {
		 
	 }
 
	 /**
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
	 
		String login = request.getParameter("login");
		String pwd = request.getParameter("mdp");
		String name = request.getParameter("nom");
		String pname = request.getParameter("prenom");
		
		JSONObject user = new JSONObject();
		user = UserTools.createUser(login, pwd, name, pname);
		 
	 	response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
		out.println(user);
	 }
	 

}