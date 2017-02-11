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

public class FriendTools {
	
	public static boolean alreadyFriends(int id1, int id2) throws SQLException{
		boolean exists = false;
		Connection conn = Database.getMySQLConnection();
		Statement st = conn.createStatement();
		
		String query = "SELECT id_to FROM Friends WHERE id_from = " + id1
						+ " and id_to = " + id2;
		ResultSet rs = st.executeQuery(query);
		
		exists = rs.next();
		rs.close(); st.close(); conn.close();
		return exists;
	}

	public static JSONObject addFriend(String login1, String login2){
		JSONObject res = new JSONObject();
		
		// vérifier que l'utilisateur est connecté
		if (login1 == null || login2 == null){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}	
		try {
			if (! AuthTools.userExists(login1) || !AuthTools.userExists(login2)){
				return ErrorJSON.serviceRefused("User doesn't exist", 1);
			}
			else {
				int id1 = AuthTools.getIdUser(login1);
				int id2 = AuthTools.getIdUser(login2);
				
				// login1 non connecté
				if (! AuthTools.hasSession(id1)){
					return ErrorJSON.serviceRefused("User is not logged in", 2);
				}
				if (alreadyFriends(id1, id2)){
					return ErrorJSON.serviceRefused("Already friends", 3);
				}
				else {
					Connection conn = Database.getMySQLConnection();
					
					// id auto-increment
					// schema : (id_from, id_to, timestamp)
					String query = "INSERT INTO Friends VALUES (?, ?, null)";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setInt(1, id1);
					pst.setInt(2, id2);
					
					pst.executeUpdate();
					pst.close(); conn.close();
				}
			}
		} catch (SQLException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	public static JSONObject removeFriend(String login1, String login2){
		JSONObject res = new JSONObject();
		
		// vérifier que l'utilisateur est connecté
		if (login1 == null || login2 == null){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}	
		try {
			if (! AuthTools.userExists(login1) || !AuthTools.userExists(login2)){
				return ErrorJSON.serviceRefused("User doesn't exist", 1);
			}
			else {
				int id1 = AuthTools.getIdUser(login1);
				int id2 = AuthTools.getIdUser(login2);
				
				// login1 non connecté
				if (! AuthTools.hasSession(id1)){
					return ErrorJSON.serviceRefused("User is not logged in", 2);
				}
				if (! alreadyFriends(id1, id2)){
					return ErrorJSON.serviceRefused("Not friends", 3);
				}
				else {
					Connection conn = Database.getMySQLConnection();
					
					// id auto-increment
					// schema : (id_from, id_to, timestamp)
					String query = "DELETE FROM Friends WHERE id_from = ? AND"
							+ " id_to = ?";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setInt(1, id1);
					pst.setInt(2, id2);
					
					pst.executeUpdate();
					pst.close(); conn.close();
				}
			}
		} catch (SQLException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	public static JSONObject listFriends(int id){
		JSONObject finalQuery = new JSONObject();
		JSONArray friends = new JSONArray();

		if (id == 0){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}	
		try {
			if (! AuthTools.userExists(id)){
				return ErrorJSON.serviceRefused("User doesn't exist", 1);
			}
			else {
				// user non connecté
				if (! AuthTools.hasSession(id)){
					return ErrorJSON.serviceRefused("User is not logged in", 2);
				}
				else {
					Connection conn = Database.getMySQLConnection();
					Statement st = conn.createStatement();
					
					String query = "SELECT id_to FROM Friends WHERE id_from = " + id;
					ResultSet rs = st.executeQuery(query);
					while (rs.next()){
						JSONObject res = new JSONObject();
						int id_to = rs.getInt("id_to");
						res.put("id", id_to);
						res.put("username", AuthTools.getLoginUser(id_to));
						friends.put(res);
					}
					finalQuery.put("friends", friends);
				}
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return finalQuery;
	}
	
	
}
