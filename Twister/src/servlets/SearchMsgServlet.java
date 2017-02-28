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
 * @api {get} /message/search Chercher un message dans ceux des amis
 * @apiVersion 0.1.0
 * @apiName SearchMsg
 * @apiGroup Message
 * 
 * @apiParam  {String} key Clé de session de l'utilisateur courant
 * @apiParam  {String} query Motif de recherche
 * 
 * @apiSuccessExample {json} Succès:
 * 			{"messages": [
    {
        "text": "second message",
        "author_username": "raoul",
        "_id": {"$oid": "58a35b83e4b052853c124fbc"},
        "author_id": 4,
        "date": {"$date": "2017-02-14T19:33:23.748Z"}
    },
    {
        "text": "secondmessage",
        "author_username": "jean",
        "_id": {"$oid": "58a35b46e4b052853c124fba"},
        "author_id": 3,
        "date": {"$date": "2017-02-14T19:32:22.377Z"}
    }
]}
 * 
 * @apiError (ErrorJSON) -1 Mauvais arguments
 * @apiError (ErrorJSON) 2 Utilisateur non connecté
 * 
 */

@WebServlet("/message/search")
public class SearchMsgServlet extends HttpServlet {
 
	 /**
	 * Default constructor.
	 */
	 public SearchMsgServlet() {
		 
	 }
 
	 /**
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		JSONObject res = new JSONObject();
		response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();

		//String idS = request.getParameter("id");
		String query = request.getParameter("query");

		if (query == null){
			JSONObject err = ErrorJSON.serviceRefused("Missing parameter", -1);
			out.println(err);
		}
		else {
			String key = request.getParameter("key");
			res = MessageTools.getMessages(key, query, -1, -1, -1, -1);
			try {
				out.println(res.toString(4));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	 }

	 

}