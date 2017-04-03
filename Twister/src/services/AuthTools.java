package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;


public class AuthTools {
	
	/* --------------------------------------------------------------
	*	Checkers
	* -------------------------------------------------------------- */


	public static boolean userExists(String login) throws SQLException{
		boolean exists = false;
		Connection conn = Database.getMySQLConnection();
		Statement st = conn.createStatement();

		String query = "SELECT id FROM Users WHERE login = \'" + login +"\'";
		st.executeQuery(query);
		ResultSet rs = st.getResultSet();
			
		// vérifie s'il y a une ligne dans le résultat
		exists = rs.next();
		rs.close(); st.close(); conn.close();
		return exists;
	}
	
	public static boolean userExists(int id) throws SQLException{
		boolean exists = false;
		Connection conn = Database.getMySQLConnection();
		Statement st = conn.createStatement();

		String query = "SELECT login FROM Users WHERE id = " + id;
		st.executeQuery(query);
		ResultSet rs = st.getResultSet();
			
		// vérifie s'il y a une ligne dans le résultat
		exists = rs.next();
		rs.close(); st.close(); conn.close();
		return exists;
	}
	
	public static boolean checkPassword(String login, String pwd) throws SQLException{
		boolean pwd_ok = false;
		
		// hash du password
		String pwdHashed = DigestUtils.sha1Hex(pwd);

		Connection conn = Database.getMySQLConnection();
		Statement st = conn.createStatement();
		
		String query = "SELECT mdp FROM Users WHERE login = \'" + login +"\'";
		st.executeQuery(query);
		ResultSet rs = st.getResultSet();
		
		
		if (rs.next()){
			String pwdDB = rs.getString("mdp");
			pwd_ok = pwdHashed.equals(pwdDB);
		}
		rs.close(); st.close(); conn.close();
		
		return pwd_ok;
	 
	}
	
	
	/* --------------------------------------------------------------
	*	Getters
	* -------------------------------------------------------------- */
	
	
	public static int getIdUser(String login) throws SQLException{
		int id = -1; 
		Connection conn = Database.getMySQLConnection();
		Statement st = conn.createStatement();
		
		String query = "SELECT id FROM Users WHERE login = \'" + login +"\'";
		st.executeQuery(query);
		ResultSet rs = st.getResultSet();
		//id = rs.getInt("id");
		if(rs.next()){
			id = rs.getInt("id");
		}

		rs.close(); st.close(); conn.close();
		return id;
	}
	
	// récupérer l'id de l'utilisateur connecté
	public static int getIdUserSession(String key) throws SQLException{
		int id = -1; 
		Connection conn = Database.getMySQLConnection();
		Statement st = conn.createStatement();
		
		String query = "SELECT id FROM Sessions WHERE session_key = \'" + key +"\'";
		st.executeQuery(query);
		ResultSet rs = st.getResultSet();
		//id = rs.getInt("id");
		if(rs.next()){
			id = rs.getInt("id");
		}

		rs.close(); st.close(); conn.close();
		return id;
	}
	
	public static String getLoginUser(int id) throws SQLException{
		String login = "";
		Connection conn = Database.getMySQLConnection();
		Statement st = conn.createStatement();
		
		String query = "SELECT login FROM Users WHERE id = " + id;
		st.executeQuery(query);
		ResultSet rs = st.getResultSet();
		//id = rs.getInt("id");
		if(rs.next()){
			login = rs.getString("login");
		}

		rs.close(); st.close(); conn.close();
		return login;
	}
	
	public static String getKey(String login) {
		String key = null;
		try {
			Connection conn = Database.getMySQLConnection();
			Statement st = conn.createStatement();
			
			int id = getIdUser(login);
			String query = "SELECT session_key FROM Sessions WHERE id = \"" + id+ "\"" ;
			ResultSet rs = st.executeQuery(query);
			
			if (rs.next()){
				key = rs.getString("session_key");
			}
			rs.close(); st.close(); conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return key;
	}
	
	public static String getKey(int id) {
		String key = null;
		try {
			Connection conn = Database.getMySQLConnection();
			Statement st = conn.createStatement();
			
			String query = "SELECT session_key FROM Sessions WHERE id = \"" + id+ "\"" ;
			ResultSet rs = st.executeQuery(query);
			
			if (rs.next()){
				key = rs.getString("session_key");
			}
			rs.close(); st.close(); conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return key;
	}
	
	public static Timestamp getRegistrationDate(int id){
		try {
			Connection conn = Database.getMySQLConnection();
			Statement st = conn.createStatement();
			String query = "SELECT reg_date FROM Users WHERE id = " + id;
			ResultSet rs = st.executeQuery(query);
			if(rs.next()){
				Timestamp ts = rs.getTimestamp("reg_date");
				return ts;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/* --------------------------------------------------------------
	*	Session
	* -------------------------------------------------------------- */
	
	
	/** retourne une clé de session */
	public static String insertSession(int id, boolean admin) throws SQLException{
		
		// true si la clé de session existe déjà
		boolean keyExists;
		String key = "";
		String query;
		
		Connection conn = Database.getMySQLConnection();
		Statement st = conn.createStatement();
		
		do {
			key = Password.generateRandomKey();
			//System.out.println(key);
			query = "SELECT session_key FROM Sessions WHERE session_key = \"" + key + "\"";
			st.executeQuery(query);
			ResultSet rs = st.getResultSet();
			
			// vérifie si la clé existe
			keyExists = rs.next();
			rs.close();
		} while (keyExists);
			
		// on crée une nouvelle session active
		query = "INSERT INTO Sessions VALUES (\"" + key + "\" ," + id + " , NOW(), " + admin + ")";
		st.executeUpdate(query);
		st.close(); conn.close();
		 
		return key;
	}

	public static boolean hasSession(int id) throws SQLException{
		boolean exists = false;
		Connection conn = Database.getMySQLConnection();
		Statement st = conn.createStatement();
		
		String query = "SELECT id FROM Sessions WHERE id = " + id;
		ResultSet rs = st.executeQuery(query);
		
		exists = rs.next();
		rs.close(); st.close(); conn.close();
		return exists;
	}
	
	public static boolean hasSession(String key) throws SQLException{
		boolean exists = false;
		Connection conn = Database.getMySQLConnection();
		Statement st = conn.createStatement();
		
		String query = "SELECT id FROM Sessions WHERE session_key = \"" + key + "\"" ;
		ResultSet rs = st.executeQuery(query);
		
		exists = rs.next();
		rs.close(); st.close(); conn.close();
		return exists;
	}

	public static boolean removeSession(int id) throws SQLException{
		int success;
		Connection conn = Database.getMySQLConnection();
		Statement st = conn.createStatement();
		
		String query = "DELETE FROM Sessions WHERE id = " + id;
		success = st.executeUpdate(query);
		//System.out.println(success);
		st.close(); conn.close();
		return success == 1;
	}
	
	public static boolean removeSession(String key) throws SQLException{
		int success;
		Connection conn = Database.getMySQLConnection();
		Statement st = conn.createStatement();
		
		String query = "DELETE FROM Sessions WHERE session_key = \"" + key + "\"" ;
		success = st.executeUpdate(query);
		//System.out.println(success);
		st.close(); conn.close();
		return success == 1;
	}
	
	
	/* --------------------------------------------------------------
	*	Session checkers (Timeout)
	* -------------------------------------------------------------- */
	
	
	public static void updateSession(String key) throws SQLException{
		Connection conn = Database.getMySQLConnection();
		Statement st = conn.createStatement();
		
		String query = "UPDATE Sessions SET timestamp = NOW() WHERE session_key = \"" + key + "\"";
		st.executeUpdate(query);
		st.close(); conn.close();
	}
	
	public static boolean checkSession(String key) throws SQLException{
		boolean check = hasSession(key);
		
		// utilisateur non connecté
		if (! check){
			return false;
		}
		
		Connection conn = Database.getMySQLConnection();
		Statement st = conn.createStatement();
		String query = "SELECT timestamp FROM Sessions WHERE session_key = \"" + key + "\"" ;
		ResultSet rs = st.executeQuery(query);
		
		// durée de session inactive : 30 minutes
		long max_duration = TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES);
		if (rs.next()){
			Timestamp timestamp = rs.getTimestamp("timestamp");
			Date date = timestamp;
			Date now = new Date();
			if (now.getTime() - date.getTime() > max_duration){
				removeSession(key);
				check = false;
			}
		}
		st.close(); conn.close();
		return check;
		

	}
	
	
}
