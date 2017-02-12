package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bdd.FriendTools;
 
/**
 * 
 * @api {get} /friend/remove Retrait d'ami
 * @apiVersion 0.1.0
 * @apiName RemoveFriend
 * @apiGroup Friends
 * 
 * 
 * @apiParam  {String} login1 Login de l'utilisateur courant
 * @apiParam  {String} login2 Login de l'ami à supprimer
 * 
 * @apiSuccessExample {json} Succès:
 * 			{}
 * 
 * @apiError (ErrorJSON) -1 Mauvais arguments
 * @apiError (ErrorJSON) 1 Utilisateur non existant
 * @apiError (ErrorJSON) 2 Utilisateur non connecté
 * @apiError (ErrorJSON) 3 Utilisateurs non amis
 * 
 */
@WebServlet("/friend/remove")
public class RemoveFriendServlet extends HttpServlet {
 
	 /**
	 * Default constructor.
	 */
	 public RemoveFriendServlet() {
		 
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
		
		res = FriendTools.removeFriend(login1, login2);
		out.println(res);

	 }
	 

}
