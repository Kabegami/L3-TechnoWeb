package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import bdd.UserTools;
import services.AuthTools;

/**
 * 
 * @api {get} /auth/logout Déconnexion
 * @apiVersion 0.1.0
 * @apiName Logout
 * @apiGroup Authentification
 * 
 * 
 * @apiParam  {String} key Clé de session de l'utilisateur courant
 * 
 * @apiSuccessExample {json} Succès:
 * 			{}
 * 
 * @apiError (ErrorJSON) -1 Mauvais arguments
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
		//String login = request.getParameter("login");
		String key = request.getParameter("key");
					
		JSONObject res = new JSONObject();
		res = UserTools.logout(key);
		if (! res.has("error_code")){
			request.getSession().invalidate();
			response.sendRedirect("../index.html");
			
		}
		else{	
			response.setContentType( "application/json" );
			PrintWriter out = response.getWriter ();
	
			out.println(res);
		}
	}

}
