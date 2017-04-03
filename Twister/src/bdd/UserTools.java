package bdd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import services.Database;
import services.ErrorJSON;
import services.AuthTools;

public class UserTools {
	
	
	public static JSONObject createUser(String login, String pwd, String lname,
			String fname, String mail){
		
		/* arguments nuls */
		if (login == null || pwd == null || lname == null || fname == null || mail == null){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}
		
		try {
			if (AuthTools.userExists(login)){
				return ErrorJSON.serviceRefused("User already exists", 1);
			}
			/* si l'utilisateur n'existe pas, on l'ajoute */
			else {
				Connection conn = Database.getMySQLConnection();
				
				// hash le password
				String pwdHashed = DigestUtils.sha1Hex(pwd);
				
				// id auto-increment
				// schema : (id, login, pwd, nom, prenom)
				String query = "INSERT INTO Users VALUES (null, ?, ?, ?, ?, ?)";
				PreparedStatement pst = conn.prepareStatement(query);
				
				pst.setString(1, login);
				pst.setString(2, pwdHashed);
				pst.setString(3, lname);
				pst.setString(4, fname);
				pst.setString(5, mail);
				
				pst.executeUpdate();
				pst.close(); conn.close();
			}
		}
		catch (Exception e){
			/* dont JSONException */
			e.printStackTrace();
		}	
		/* succès */
		JSONObject ret = new JSONObject();
		return ret;
	}
	
	public static JSONObject login(String login, String pwd){
		JSONObject ret = new JSONObject();
		
		/* arguments nuls */
		if (login == null || pwd == null){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}	
		
		try {
			/* si l'utilisateur n'existe pas, on refuse */
			if (! AuthTools.userExists(login)){
				return ErrorJSON.serviceRefused("User doesn't exist", 1);
			}
			
			/* l'utilisateur existe 
			 * on vérifie son mot de passe
			 * si correct, on lui attribue une clé de session
			 */
			if (! AuthTools.checkPassword(login, pwd)){
				return ErrorJSON.serviceRefused("Incorrect password", 2);
			}
			
			int id = AuthTools.getIdUser(login);
			if (AuthTools.hasSession(id)){
				return ErrorJSON.serviceRefused("User already logged in", 3);		
			}

			/* vérifier si admin ou pas */
			String key = AuthTools.insertSession(id, false);
			ret.put("key", key);
			ret.put("id", id);
			ret.put("login", login);
			ret.put("follows", FollowTools.listFollows(key).getJSONArray("follows"));
	
		} catch (SQLException e){
			e.printStackTrace();
			return ErrorJSON.serviceRefused("erreur SQL", 100);
		} catch (Exception e){
			/* dont JSONException */
			e.printStackTrace();
			return ErrorJSON.serviceRefused("erreur", 10000);
		}	
		return ret;
	}
	
	public static JSONObject logout(String key){
		JSONObject ret = new JSONObject();
		
		/* arguments nuls */
		if (key == null){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}	
		try {
			// on vérifie si la session existe
			if (! AuthTools.hasSession(key)){
				return ErrorJSON.serviceRefused("User is not logged in", 2);
			}
			else {
				AuthTools.removeSession(key);
			}
				
		} catch (SQLException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return ret;
	}
	
	public static JSONObject getUserInfo(String key, String user){
		JSONObject ret = new JSONObject();
		
		if (key == null || user == null){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}
		try {
			if (! AuthTools.checkSession(key)){
				return ErrorJSON.serviceRefused("User is not logged in", 2);
			}
			if (! AuthTools.userExists(user)){
				return ErrorJSON.serviceRefused("User doesn't exist", 1);
			}
			else {
				int id = AuthTools.getIdUser(user);
				ret.put("id", id);
				ret.put("login", user);
				
				Timestamp ts = AuthTools.getRegistrationDate(id);
				ret.put("registration", ts);
				
				JSONArray subscribers = FollowTools.listSubscribers(id).getJSONArray("subscribers");
				ret.put("subscribers", subscribers);
				
				JSONArray follows = FollowTools.listFollows(id).getJSONArray("follows");
				ret.put("follows", follows);
				
				AuthTools.updateSession(key);
			}
		} catch (SQLException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return ret;
	}

}
