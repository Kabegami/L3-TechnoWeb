package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import bdd.MessageTools;
import services.AuthTools;
 
/**
 * 
 * @api {get} /message/list Liste des messages
 * @apiVersion 0.1.0
 * @apiName ListMessages
 * @apiGroup Message
 * 
 * @apiParam  {String} key Clé de session de l'utilisateur courant
 * @apiParam {String} query mots clé
 * @apiParam {int} from id de l'utilisateur concerné (-1 si page principale et qu'on veut tout avoir)
 * @apiParam {int} id_max -1 si pas de limite
 * @apiParam {int} id_min -1 si pas de limite
 * @apiParam {int} nb nombre de messages à retourner (-1 si pas de limite)
 * 
 * @apiSuccessExample {json} Succès:
 * 			{"messages":[{"text":"test","author_username":"toto","_id":{"$oid":"589af353e4b02b8b69b540be"},"author_id":2,"date":{"$date":"2017-02-08T10:30:43.411Z"}},
 * 			{"text":"deuxieme message","author_username":"toto","_id":{"$oid":"589afd29e4b0c2f81a2b7eb6"},"author_id":2,"date":{"$date":"2017-02-08T11:12:41.561Z"}}]}
 * 
 * @apiError (ErrorJSON) -1 Mauvais arguments
 * @apiError (ErrorJSON) 2 Utilisateur non connecté
 */
@WebServlet("/message/user")
public class ListMessagesUserServlet extends HttpServlet {

	 protected void doPost(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		JSONObject res = new JSONObject();
		response.setContentType( "application/json" );

		String user = request.getParameter("key");
		String from = request.getParameter("from");
		String id_max = request.getParameter("id_max");
		String id_min = request.getParameter("id_min");
		String nb = request.getParameter("nb");
		res = MessageTools.getMessagesUser(user, from, id_max, id_min, nb);
		PrintWriter out = response.getWriter ();
		try {
			out.println(res.toString(4));
		} catch (JSONException e) {
			e.printStackTrace();
		}
			 
	 }
	 
	 protected void doGet(HttpServletRequest request,
			 HttpServletResponse response) throws ServletException, IOException {
		 doPost(request, response);
	 }
}