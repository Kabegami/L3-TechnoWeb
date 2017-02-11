package bdd;

import org.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import services.Database;
import services.ErrorJSON;
import services.AuthTools;

public class UserTools {
	
	
	public static JSONObject createUser(String login, String pwd, String lname,
			String fname){
		
		/* arguments nuls */
		if (login == null || pwd == null || lname == null || fname == null){
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
				String query = "INSERT INTO Users VALUES (null, ?, ?, ?, ?)";
				PreparedStatement pst = conn.prepareStatement(query);
				pst.setString(1, login);
				pst.setString(2, pwdHashed);
				pst.setString(3, lname);
				pst.setString(4, fname);
				
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
			else {
				if (! AuthTools.checkPassword(login, pwd)){
					return ErrorJSON.serviceRefused("Incorrect password", 2);
				}
				else {
					int id = AuthTools.getIdUser(login);
	
					/* vérifier si admin ou pas */
					String key = AuthTools.insertSession(id, false);
					ret.put("key", key);
				}
			}
		} catch (SQLException e){
			e.printStackTrace();
		} catch (Exception e){
			/* dont JSONException */
			e.printStackTrace();
		}	
		return ret;
	}
	
	public static JSONObject logout(String login){
		JSONObject ret = new JSONObject();
		
		/* arguments nuls */
		if (login == null){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}	
		
		try {
			if (! AuthTools.userExists(login)){
					return ErrorJSON.serviceRefused("User doesn't exist", 1);
				}
				else {
					int id = AuthTools.getIdUser(login);
					// on vérifie si la session existe
					if (! AuthTools.hasSession(id)){
						return ErrorJSON.serviceRefused("User is not logged in", 2);
					}
					else {
						AuthTools.removeSession(id);
					}
				}
		} catch (SQLException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return ret;
	}

}
