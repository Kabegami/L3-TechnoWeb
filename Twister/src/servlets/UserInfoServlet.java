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
import bdd.UserTools;
import services.AuthTools;
 
/**
 * 
 * @api {post} /user/info Informations utilisateur
 * @apiVersion 0.1.0
 * @apiName UserInfo
 * @apiGroup User
 * 
 * @apiParam  {String} key Clé de session de l'utilisateur courant 
 * @apiParam  {key} user Nom de l'utilisateur recherché
 *  
 * @apiSuccessExample {json} Succès:
 * 			{
    "follows": [
        {
            "id": 3,
            "username": "jean"
        },
        {
            "id": 4,
            "username": "raoul"
        }
    ],
    "id": 2,
    "login": "toto",
    "subscribers": [
        {
            "id": 3,
            "login": "jean"
        },
        {
            "id": 6,
            "login": "newtoto"
        }
    ],
    "registration": "2017-03-08 00:00:00.0"
}
 * 
 * @apiError (ErrorJSON) -1 Mauvais arguments
 * @apiError (ErrorJSON) 1 Utilisateur non existant
 * @apiError (ErrorJSON) 2 Utilisateur non connecté
 */
@WebServlet("/user/info")
public class UserInfoServlet extends HttpServlet { 

	 protected void doPost(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		JSONObject res = new JSONObject();
		response.setContentType( "application/json" );

		String key = request.getParameter("key");
		String user = request.getParameter("user");
		res = UserTools.getUserInfo(key, user);
		
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