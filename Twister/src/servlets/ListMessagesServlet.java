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

import bdd.MessageTools;
 
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


 */
@WebServlet("/message/list")
public class ListMessagesServlet extends HttpServlet {

	/**
	 * Default constructor.
	 */
	 public ListMessagesServlet() {
		 
	 }
 
	 /**
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		JSONObject res = new JSONObject();
		response.setContentType( "application/json" );
		 //response.sendRedirect("/Gr2_VU/index.html");
		String user = request.getSession().getAttribute("user").toString();
		res = MessageTools.listMessages(user);
		PrintWriter out = response.getWriter ();
		out.println(res);
			 
	 }
}