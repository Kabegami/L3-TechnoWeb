package services;

import java.util.UUID;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bdd.BDException;

public class Password {
	
	public static String generateRandomKey(int size){
		try {
			
			
			// Vérifie si la clé est déjà dans la base
			Connection conn = Database.getMySQLConnection();
			String query = "SELECT key FROM Session";
			Statement st = conn.createStatement();
			
			// récupère les clés de sessions actives
			st.executeQuery(query);
			ResultSet rs = st.getResultSet();
			
			while (rs.next()){
				
			}

			rs.close(); st.close(); conn.close();
			return id;
		} catch (SQLException e) {
			throw new BDException("erreur bd");
		}
	}
}
