package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bdd.UserTools;
 

/**
 * 
 * @api {post} /usr/create Créer un nouvel utilisateur
 * @apiVersion 0.1.0
 * @apiName NewUser
 * @apiGroup User
 * 
 * 
 * @apiParam  {String} login Login de l'utilisateur
 * @apiParam  {String} pwd Mot de passe de l'utilisateur
 * @apiParam  {String} lname Nom 
 * @apiParam  {String} fname Prénom
 * 
 * @apiSuccessExample {json} Succès:
 * 			{}
 * @apiError (ErrorJSON) -1 Mauvais arguments

 * @apiError (ErrorJSON) 1 Utilisateur déjà existant
 * 
 */
@WebServlet("/user/create")
public class NewUserServlet extends HttpServlet {
 
	 public NewUserServlet() {
		 
	 }
 
	 /**
	 * This method will handle all POST request.
	 */
	 protected void doPost(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("login");
		String pwd = request.getParameter("pwd");
		String lname = request.getParameter("lname");
		String fname = request.getParameter("fname");
		
		JSONObject user = new JSONObject();
		
		response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
		
		user = UserTools.createUser(login, pwd, lname, fname);
		out.println(user);

	 }
	 

}