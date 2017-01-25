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
public class LoginServlet extends HttpServlet {
 
	 /**
	 * Default constructor.
	 */
	 public LoginServlet() {
		 
	 }
 
	 /**
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
	 
		/* gérer cas si + de 2 arguments */
		String login = request.getParameter("login");
		String pwd = request.getParameter("mdp");
		
		JSONObject user = new JSONObject();
		user = UserTools.login(login, pwd);
		 
	 	response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
		out.println(user);
	 }
	 

}