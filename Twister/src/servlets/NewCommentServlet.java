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

/**
 * 
 * @api {post} /comment/new Nouveau commentaire
 * @apiVersion 0.1.0
 * @apiName NewComment
 * @apiGroup Comment
 * 
 * @apiParam  {String} key Clé de session de l'utilisateur courant
 * @apiParam  {int} id_message id du message commenté
 * @apiParam  {String} text Texte du comentaire
 * 
 * @apiSuccessExample {json} Succès:
 * 			{"id":2,"author":{"id":2,"username":"toto"},"text":"comment","date":1491315119198}
 * 
 * @apiError (ErrorJSON) -1 Mauvais arguments
 * @apiError (ErrorJSON) -11 Mauvais arguments
 * @apiError (ErrorJSON) 2 Utilisateur non connecté
 * 
 */

@WebServlet("/comment/new")
public class NewCommentServlet extends HttpServlet {
	 protected void doPost(HttpServletRequest request,
			 HttpServletResponse response) throws ServletException, IOException {
				JSONObject res = new JSONObject();
				response.setContentType( "application/json" );
				PrintWriter out = response.getWriter ();

				String key = request.getParameter("key");
				String id_message = request.getParameter("id_message");
				String text = request.getParameter("text");
				
				/*
				if (message == null){
					JSONObject err = ErrorJSON.serviceRefused("Missing parameter", -1);
					out.println(err);
				}
				else {*/
				res = MessageTools.newComment(key, id_message, text);
				out.println(res);
				
			 }
			 
			 protected void doGet(HttpServletRequest request,
					 HttpServletResponse response) throws ServletException, IOException {
						doPost(request, response);
			 }
			 
}
