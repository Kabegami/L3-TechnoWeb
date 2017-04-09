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

import bdd.UserTools;
import services.MailRecovery;
 

/**
 * 
 * @api {post} /usr/pass Récupérer mot de passe par mail
 * @apiVersion 0.1.0
 * @apiName RecoverPassword
 * @apiGroup User
 * 
 * 
 * @apiParam  {String} mail Adresse e-mail
 * 
 * @apiSuccessExample {json} Succès:
 * 			{}
 * @apiError (ErrorJSON) -1 Mauvais arguments
 * @apiError (ErrorJSON) 1 Adresse email non trouvée
 * 
 */

@WebServlet("/user/pass")
public class RecoverPasswordServlet extends HttpServlet {
 
	 /**
	 * This method will handle all POST request.
	 */
	 protected void doPost(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		String mail = request.getParameter("mail");
		
		JSONObject res = new JSONObject();
		
		response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
		res = MailRecovery.sendPassword(mail);
		out.println(res.toString());


	 }
	 
	 protected void doGet(HttpServletRequest request,
			 HttpServletResponse response) throws ServletException, IOException {
		 doPost(request, response);
	 }
	 
}