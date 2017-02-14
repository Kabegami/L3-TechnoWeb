package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import bdd.UserTools;
import services.AuthTools;
 
/**
 * 
 * @api {post} /auth/login Connexion
 * @apiVersion 0.1.0
 * @apiName Login
 * @apiGroup Authentification
 * 
 * 
 * @apiParam  {String} login Login de l'utilisateur
 * @apiParam  {String} pwd Mot de passe de l'utilisateur
 * 
 * @apiSuccessExample {json} Succès:
 * 			{"key" : "110e8400-e29b-11d4-a716-446655440000"}
 * 
 * @apiError (ErrorJSON) -1 Mauvais arguments
 * @apiError (ErrorJSON) 1 Utilisateur non existant
 * @apiError (ErrorJSON) 2 Mot de passe incorrect
 * @apiError (ErrorJSON) 3 Utilisteur déjà connecté


 */
@WebServlet("/auth/login")
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
		
		if (! user.has("error_code")){
			HttpSession session = request.getSession();
			session.setAttribute("user", login);
			try {
				session.setAttribute("key", user.get("key"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			response.sendRedirect("../welcome.jsp");
			
		}
		else{
		
		 	response.setContentType( "application/json" );
		 	//response.sendRedirect("/Gr2_VU/index.html");
		 	
			PrintWriter out = response.getWriter ();
			out.println(user);
		}
			 
	 }
}