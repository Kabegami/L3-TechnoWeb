package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bdd.FollowTools;
import services.AuthTools;
 
/**
 * 
 * @api {get} /friend/remove Retrait d'ami
 * @apiVersion 0.1.0
 * @apiName RemoveFriend
 * @apiGroup Friends
 * 
 * @apiParam  {String} key Clé de session de l'utilisateur courant
 * @apiParam  {String} id_friend id de l'ami à supprimer
 * 
 * @apiSuccessExample {json} Succès:
 * 			{}
 * 
 * @apiError (ErrorJSON) -1 Mauvais arguments
 * @apiError (ErrorJSON) 2 Utilisateur non connecté
 * @apiError (ErrorJSON) 3 Utilisateurs non amis
 * 
 */
@WebServlet("/follow/remove")
public class StopFollowServlet extends HttpServlet {
 
	 /**
	 * Default constructor.
	 */
	 public StopFollowServlet() {
		 
	 }
 
	 /**
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		String user = request.getParameter("key");
		String login2 = request.getParameter("idfriend");
			
		int id_friend = Integer.parseInt(login2);
		JSONObject res = new JSONObject();
			
		response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
			
		res = FollowTools.stopFollow(user, id_friend);
		out.println(res);

	 }
	 

}
