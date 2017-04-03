package bdd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import services.Database;
import services.ErrorJSON;
import services.AuthTools;

public class FollowTools {
	
	public static boolean alreadyFollows(int id1, int id2) throws SQLException{
		boolean exists = false;
		Connection conn = Database.getMySQLConnection();
		Statement st = conn.createStatement();
		
		String query = "SELECT id_to FROM Followers WHERE id_from = " + id1
						+ " and id_to = " + id2;
		ResultSet rs = st.executeQuery(query);
		
		exists = rs.next();
		rs.close(); st.close(); conn.close();
		return exists;
	}

	public static JSONObject addFollow(String key, int id_friend){
		JSONObject res = new JSONObject();
		
		// vérifier que l'utilisateur est connecté
		if (key == null || id_friend < 0){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}	
		try {
			if (! AuthTools.userExists(id_friend)){
				return ErrorJSON.serviceRefused("User doesn't exist", 1);
			}
			else {
				int id_user = AuthTools.getIdUserSession(key);
				
				// login1 non connecté
				if (! AuthTools.checkSession(key)){
					return ErrorJSON.serviceRefused("User is not logged in", 2);
				}
				if (alreadyFollows(id_user, id_friend)){
					return ErrorJSON.serviceRefused("Already follows", 3);
				}
				else {
					Connection conn = Database.getMySQLConnection();
					
					// id auto-increment
					// schema : (id_from, id_to, timestamp)
					String query = "INSERT INTO Followers VALUES (?, ?, null)";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setInt(1, id_user);
					pst.setInt(2, id_friend);
					
					pst.executeUpdate();
					pst.close(); conn.close();
				}
				AuthTools.updateSession(key);
			}
		} catch (SQLException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	public static JSONObject stopFollow(String key, int id_friend){
		JSONObject res = new JSONObject();
		
		// vérifier que l'utilisateur est connecté
		if (key == null || id_friend < 0){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}	
		try {
			if (! AuthTools.userExists(id_friend)){
				return ErrorJSON.serviceRefused("User doesn't exist", 1);
			}
			else {
				int id_user = AuthTools.getIdUserSession(key);
				
				// login1 non connecté
				if (! AuthTools.checkSession(key)){
					return ErrorJSON.serviceRefused("User is not logged in", 2);
				}
				if (! alreadyFollows(id_user, id_friend)){
					return ErrorJSON.serviceRefused("Not following", 3);
				}
				else {
					Connection conn = Database.getMySQLConnection();
					
					// id auto-increment
					// schema : (id_from, id_to, timestamp)
					String query = "DELETE FROM Followers WHERE id_from = ? AND"
							+ " id_to = ?";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setInt(1, id_user);
					pst.setInt(2, id_friend);
					
					pst.executeUpdate();
					pst.close(); conn.close();
				}
				AuthTools.updateSession(key);
			}
		} catch (SQLException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	
	public static JSONObject listFollows(String key){
		JSONObject finalQuery = new JSONObject();
		JSONArray follows = new JSONArray();

		if (key == null){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}	
		try {
				// user non connecté
			if (! AuthTools.checkSession(key)){
				return ErrorJSON.serviceRefused("User is not logged in", 2);
			}
			else {
				Connection conn = Database.getMySQLConnection();
				Statement st = conn.createStatement();
				
				int id = AuthTools.getIdUserSession(key);
				String query = "SELECT id_to FROM Followers WHERE id_from = " + id;
				ResultSet rs = st.executeQuery(query);
				while (rs.next()){
					JSONObject res = new JSONObject();
					int id_to = rs.getInt("id_to");
					res.put("id", id_to);
					res.put("username", AuthTools.getLoginUser(id_to));
					follows.put(res);
				}
			}
			finalQuery.put("follows", follows);
			AuthTools.updateSession(key);
		}
		catch (SQLException e){
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return finalQuery;
	}
	
	
	/* version pour rechercher n'importe quel utilisateur */
	public static JSONObject listFollows(int id){
		JSONObject finalQuery = new JSONObject();
		JSONArray follows = new JSONArray();

		if (id < 0){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}	
		try {
			Connection conn = Database.getMySQLConnection();
			Statement st = conn.createStatement();
				
			String query = "SELECT id_to FROM Followers WHERE id_from = " + id;
			ResultSet rs = st.executeQuery(query);
			while (rs.next()){
				JSONObject res = new JSONObject();
				int id_to = rs.getInt("id_to");
				res.put("id", id_to);
				res.put("username", AuthTools.getLoginUser(id_to));
				follows.put(res);
			}
			finalQuery.put("follows", follows);
		}
		catch (SQLException e){
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return finalQuery;
	}
	
	public static JSONObject listSubscribers(int id){
		JSONObject ret = new JSONObject();
		if (id < 0){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}
		try {
			JSONArray subscribers = new JSONArray();
				
			Connection conn = Database.getMySQLConnection();
			Statement st = conn.createStatement();
		
			String query = "SELECT id_from FROM Followers WHERE id_to = " + id;
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				int id_from = rs.getInt("id_from");
				String username = AuthTools.getLoginUser(id_from);
				JSONObject user = new JSONObject();
				user.put("id",  id_from);
				user.put("login",  username);
				subscribers.put(user);
			}
			ret.put("subscribers", subscribers);
		}
		catch (SQLException e){
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	
}
