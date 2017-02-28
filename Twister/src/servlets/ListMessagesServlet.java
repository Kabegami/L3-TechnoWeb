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
 *  @apiParam  {String} key Clé de session de l'utilisateur courant
 *  
 * @apiSuccessExample {json} Succès:
 * 			{"messages":[{"text":"test","author_username":"toto","_id":{"$oid":"589af353e4b02b8b69b540be"},"author_id":2,"date":{"$date":"2017-02-08T10:30:43.411Z"}},
 * 			{"text":"deuxieme message","author_username":"toto","_id":{"$oid":"589afd29e4b0c2f81a2b7eb6"},"author_id":2,"date":{"$date":"2017-02-08T11:12:41.561Z"}}]}
 * 
 * @apiError (ErrorJSON) -1 Mauvais arguments
 * @apiError (ErrorJSON) 2 Utilisateur non connecté
 */
@WebServlet("/message/list")
public class ListMessagesServlet extends HttpServlet {

	/**
	 * Default constructor.
	 */
	 public ListMessagesServlet() {
		 
	 }
 
	 /**
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		JSONObject res = new JSONObject();
		response.setContentType( "application/json" );

		String user = request.getParameter("key");
		res = MessageTools.getMessages(user);
		PrintWriter out = response.getWriter ();
		try {
			out.println(res.toString(4));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 
	 }
}