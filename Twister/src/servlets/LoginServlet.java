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
 * 			{
    "follows": [
        {
            "id": 3,
            "username": "jean"
        },
        {
            "id": 4,
            "username": "raoul"
        }
    ],
    "id": 2,
    "login": "toto",
    "key": "f48c863b-6b1f-44c9-8006-287e06efc6ad"
}
 * 
 * @apiError (ErrorJSON) -1 Mauvais arguments
 * @apiError (ErrorJSON) 1 Utilisateur non existant
 * @apiError (ErrorJSON) 2 Mot de passe incorrect
 * @apiError (ErrorJSON) 3 Utilisteur déjà connecté


 */
@WebServlet(name="loginServlet", urlPatterns="/auth/login")
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
		 	
		PrintWriter out = response.getWriter ();
		try {
			out.println(user.toString(4));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		 
	 }
	 
	 protected void doGet(HttpServletRequest request,
			 HttpServletResponse response) throws ServletException, IOException {
		 doPost(request, response);
	 }
	 
}