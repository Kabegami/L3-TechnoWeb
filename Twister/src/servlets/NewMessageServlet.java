package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bdd.MessageTools;
import services.ErrorJSON;
 
/**
 * 
 * @api {post} /message/new Nouveau message
 * @apiVersion 0.1.0
 * @apiName CreateMessage
 * @apiGroup Message
 * 
 * 
 * @apiParam  {String} login Login de l'utilisateur courant
 * 
 * @apiSuccessExample {json} Succès:
 * 			{}
 * 
 * @apiError (ErrorJSON) -1 Mauvais arguments
 * @apiError (ErrorJSON) 1 Utilisateur non existant
 * @apiError (ErrorJSON) 2 Utilisateur non connecté
 * 
 */

public class NewMessageServlet extends HttpServlet {
 
	 /**
	 * Default constructor.
	 */
	 public NewMessageServlet() {
		 
	 }
 
	 /**
	 * This method will handle all GET request.
	 */
	 protected void doPost(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		String idS = request.getParameter("id");
		String message = request.getParameter("message");
		
		response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
		
		if (idS == null || message == null){
			JSONObject err = ErrorJSON.serviceRefused("Missing parameter", -1);
			out.println(err);
		}
		else {
			int id = Integer.parseInt(idS);
			JSONObject res = new JSONObject();
			
			res = MessageTools.newMessage(id, message);
			out.println(res);
		}
	 }
	 

}