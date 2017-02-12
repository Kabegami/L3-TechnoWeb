package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bdd.MessageTools;
import services.AuthTools;
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

@WebServlet("/message/new")
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
		
		//String idS = request.getParameter("id");
		String message = request.getParameter("message");
		
		response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
		
		if (message == null){
			JSONObject err = ErrorJSON.serviceRefused("Missing parameter", -1);
			out.println(err);
		}
		else {
			String login = request.getSession().getAttribute("user").toString();
			JSONObject res = new JSONObject();
			
			res = MessageTools.newMessage(login, message);
			out.println(res);
		}
	 }
	 

}