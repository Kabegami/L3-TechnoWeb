package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bdd.UserTools;

/**
 * 
 * @api {get} /auth/logout Déconnexion
 * @apiVersion 0.1.0
 * @apiName Logout
 * @apiGroup Authentification
 * 
 * 
 * @apiParam  {String} login Login de l'utilisateur
 * 
 * @apiSuccessExample {json} Succès:
 * 			{}
 * 
 * @apiError (ErrorJSON) -1 Mauvais arguments
 * @apiError (ErrorJSON) 1 Utilisateur non existant
 * @apiError (ErrorJSON) 2 Utilisateur non connecté

 */
@WebServlet("/auth/logout")
public class LogoutServlet extends HttpServlet {

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
    }

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		/* gérer cas si + de 2 arguments */
		String login = request.getParameter("login");
					
		JSONObject user = new JSONObject();
		user = UserTools.logout(login);
					
		response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();

		out.println(user);
	
	}

}
