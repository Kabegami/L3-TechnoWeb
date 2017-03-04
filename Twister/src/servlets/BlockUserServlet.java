package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bdd.BlockTools;
 
/**
 * 
 * @api {get} /block/add Bloquer un utilisateur
 * @apiVersion 0.1.0
 * @apiName BlockUser
 * @apiGroup Block
 * 
 * @apiParam  {String} key Clé de session de l'utilisateur courant
 * @apiParam  {int} id_block id de l'utilisateur à bloquer
 * 
 * @apiSuccessExample {json} Succès:
 * 			{}
 * 
 * @apiError (ErrorJSON) -1 Mauvais arguments
 * @apiError (ErrorJSON) 1 Utilisateur non existant
 * @apiError (ErrorJSON) 2 Utilisateur non connecté
 * @apiError (ErrorJSON) 3 Utilisateurs déjà bloqué
 * 
 */

@WebServlet("/block/add")
public class BlockUserServlet extends HttpServlet {
 
	 /**
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		String user = request.getParameter("key");
		String login2 = request.getParameter("id_block");
		
		int id_block = Integer.parseInt(login2);
		JSONObject res = new JSONObject();
		
		response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
		
		res = BlockTools.blockUser(user, id_block);
		out.println(res);

	 }
	 

}
