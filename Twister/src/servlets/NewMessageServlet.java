package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import bdd.MessageTools;
import services.AuthTools;
import services.ErrorJSON;
 
/**
 * 
 * @api {post} /message/new Nouveau message
 * @apiVersion 0.1.0
 * @apiName NewMessage
 * @apiGroup Message
 * 
 * @apiParam  {String} key Clé de session de l'utilisateur courant
 * @apiParam  {String} message Message
 * 
 * @apiSuccessExample {json} Succès:
 * 			{}
 * 
 * @apiError (ErrorJSON) -1 Mauvais arguments
 * @apiError (ErrorJSON) 2 Utilisateur non connecté
 * 
 */

@WebServlet("/message/new")
public class NewMessageServlet extends HttpServlet {
	 protected void doPost(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		JSONObject res = new JSONObject();
		response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();

		String key = request.getParameter("key");
		String text = request.getParameter("text");
		
		/*
		if (message == null){
			JSONObject err = ErrorJSON.serviceRefused("Missing parameter", -1);
			out.println(err);
		}
		else {*/
		res = MessageTools.newMessage(key, text);
		out.println(res);
		
	 }
	 
	 protected void doGet(HttpServletRequest request,
			 HttpServletResponse response) throws ServletException, IOException {
				doPost(request, response);
	 }
	 

}