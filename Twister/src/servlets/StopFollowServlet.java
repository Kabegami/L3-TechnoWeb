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
 * @api {get} /follow/remove Supprime l'utilisateur des profils suivis
 * @apiVersion 0.1.0
 * @apiName StopFollow
 * @apiGroup Follow
 * 
 * @apiParam  {String} key Clé de session de l'utilisateur courant
 * @apiParam  {String} id_follow id à supprimer des profils suivis
 * 
 * @apiSuccessExample {json} Succès:
 * 			{}
 * 
 * @apiError (ErrorJSON) -1 Mauvais arguments
 * @apiError (ErrorJSON) 2 Utilisateur non connecté
 * @apiError (ErrorJSON) 3 Utilisateur non suivi
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
	 protected void doPost(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		String user = request.getParameter("key");
		String login2 = request.getParameter("id_follow");
			
		int id_follow = Integer.parseInt(login2);
		JSONObject res = new JSONObject();
			
		response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
			
		res = FollowTools.stopFollow(user, id_follow);
		out.println(res);

	 }
	 
	 protected void doGet(HttpServletRequest request,
			 HttpServletResponse response) throws ServletException, IOException {
		 doPost(request, response);
	 }
}
