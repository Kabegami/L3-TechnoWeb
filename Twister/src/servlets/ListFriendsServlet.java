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
import services.AuthTools;
 
/**
 * 
 * @api {get} /friend/list Liste les amis
 * @apiVersion 0.1.0
 * @apiName ListFriends
 * @apiGroup Friends
 * 
 * 
 * @apiParam  {String} login Login de l'utilisateur courant
 * 
 * @apiSuccessExample {json} Succès:
 * 			{"friends":[{"id":3,"username":"jean"},{"id":4,"username":"raoul"}]}
 * 
 * @apiError (ErrorJSON) -1 Mauvais arguments
 * @apiError (ErrorJSON) 1 Utilisateur non existant
 * @apiError (ErrorJSON) 2 Utilisateur non connecté
 * 
 */

@WebServlet("/friend/list")
public class ListFriendsServlet extends HttpServlet {
 
	 /**
	 * Default constructor.
	 */
	 public ListFriendsServlet() {
		 
	 }
 
	 /**
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("login");
		
		JSONObject res = new JSONObject();
		
		response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
		
		res = FriendTools.listFriends(login);
		out.println(res);

	 }
	 

}