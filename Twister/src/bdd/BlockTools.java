package bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import services.AuthTools;
import services.Database;
import services.ErrorJSON;

public class BlockTools {
	
	public static boolean isBlocked(int id1, int id_block) throws SQLException{
		boolean exists = false;
		Connection conn = Database.getMySQLConnection();
		Statement st = conn.createStatement();
		
		String query = "SELECT id_to FROM Blocked WHERE id_from = " + id1
						+ " and id_to = " + id_block;
		ResultSet rs = st.executeQuery(query);
		
		exists = rs.next();
		rs.close(); st.close(); conn.close();
		return exists;
	}
	
	public static JSONObject blockUser(String key, int id_block){
		JSONObject res = new JSONObject();
		
		// vérifier que l'utilisateur est connecté
		if (key == null || id_block < 0){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}	
		try {
			if (! AuthTools.userExists(id_block)){
				return ErrorJSON.serviceRefused("User doesn't exist", 1);
			}
			else {
				int id_user = AuthTools.getIdUserSession(key);
				
				// login1 non connecté
				if (! AuthTools.checkSession(key)){
					return ErrorJSON.serviceRefused("User is not logged in", 2);
				}
				if (isBlocked(id_user, id_block)){
					return ErrorJSON.serviceRefused("Already blocked", 3);
				}
				else {
					Connection conn = Database.getMySQLConnection();
					
					// id auto-increment
					// schema : (id_from, id_to, timestamp)
					String query = "INSERT INTO Blocked VALUES (?, ?, null)";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setInt(1, id_user);
					pst.setInt(2, id_block);
					
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
	
	public static JSONObject unblockUser(String key, int id_block){
		JSONObject res = new JSONObject();
		
		// vérifier que l'utilisateur est connecté
		if (key == null || id_block < 0){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}	
		try {
			if (! AuthTools.userExists(id_block)){
				return ErrorJSON.serviceRefused("User doesn't exist", 1);
			}
			else {
				int id_user = AuthTools.getIdUserSession(key);
				
				// login1 non connecté
				if (! AuthTools.checkSession(key)){
					return ErrorJSON.serviceRefused("User is not logged in", 2);
				}
				if (! isBlocked(id_user, id_block)){
					return ErrorJSON.serviceRefused("Not blocked", 3);
				}
				else {
					Connection conn = Database.getMySQLConnection();
					
					// id auto-increment
					// schema : (id_from, id_to, timestamp)
					String query = "DELETE FROM Blocked WHERE id_from = ? AND"
							+ " id_to = ?";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setInt(1, id_user);
					pst.setInt(2, id_block);
					
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
}
