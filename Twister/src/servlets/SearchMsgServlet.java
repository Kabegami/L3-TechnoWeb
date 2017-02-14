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
 * @api {post} /message/search Chercher un message dans ceux des amis
 * @apiVersion 0.1.0
 * @apiName SearchMsg
 * @apiGroup Message
 * 
 * @apiParam  {String} key Clé de session de l'utilisateur courant
 * @apiParam  {String} query Motif de recherche
 * 
 * @apiSuccessExample {json} Succès:
 * 			{}
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
			String key = request.getSession().getAttribute("key").toString();
			res = MessageTools.search(key, query);
			try {
				out.println(res.toString(4));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	 }

	 

}