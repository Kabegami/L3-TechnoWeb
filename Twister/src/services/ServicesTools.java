package services;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import bdd.BDException;

public class ServicesTools {

	public static boolean userExists(String login) throws SQLException{
		boolean exists = false;
		try {
			Connection conn = Database.getMySQLConnection();
			String query = "SELECT id FROM Users WHERE login = " + login;
			Statement st = conn.createStatement();
			st.executeQuery(query);
			ResultSet rs = st.getResultSet();
			
			// vérifie s'il y a une ligne dans le résultat
			exists = rs.next();
			rs.close(); st.close(); conn.close();
		} catch (SQLException e) {
			
		}
		return exists;
	}
	
	public static boolean checkPassword(String login, String pwd) 
			throws BDException{
		boolean pwd_ok;
		
		try {
			Connection conn = Database.getMySQLConnection();
			String query = "SELECT password FROM Users WHERE login = " + login;
			Statement st = conn.createStatement();
			st.executeQuery(query);
			ResultSet rs = st.getResultSet();
			String pwdDB = rs.getString(1);
			// vérifie s'il y a une ligne dans le résultat

			rs.close(); st.close(); conn.close();
			pwd_ok = pwd.equals(pwdDB);
			return pwd_ok;
			
		} catch (SQLException e) {
			throw new BDException("erreur bd");
		}
	}
	
	public static int getIdUser(String login){
		int id = -1; 
		
		/* accéder à l'id dans la BDD */
		try {
			Connection conn = Database.getMySQLConnection();

			Statement st = conn.createStatement();
			
			String query = "SELECT * FROM Users";
			System.out.println(query);
			st.executeQuery(query);
			ResultSet rs = st.getResultSet();
			while(rs.next()){
				System.out.println(rs.getString("nom"));
			}

			rs.close(); st.close(); conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
		
	}
	
	/** retourne une clé de session */
	public static String insertSession(int id, boolean admin)
			throws BDException{
		
		boolean keyExists;
		String key = "";
		/* build the key */
		try {
			Connection conn = Database.getMySQLConnection();
			Statement st = conn.createStatement();
			
			do {
			key = Password.generateRandomKey();
			String query = "SELECT key FROM Sessions";
			st.executeQuery(query);
			ResultSet rs = st.getResultSet();
			
			// récupère l'id
			keyExists = rs.next();
			rs.close();
			} while (keyExists);
			st.close(); conn.close();
		} catch (SQLException e) {
			throw new BDException("erreur bd");
		}
		
		return key;
	}
	
	public static void removeSession(int id) throws SQLException{
		boolean success = true;
		
		/*
		 * retirer la clé associée à cet id de la BDD
		 */

	}
	
}
