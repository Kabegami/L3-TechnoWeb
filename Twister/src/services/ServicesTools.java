package services;

import java.sql.SQLException;

public class ServicesTools {

	public static boolean userExists(String login) throws SQLException{
		//boolean exists = true;
		boolean exists = false;
		/* à compléter */
		
		return exists;
	}
	
	public static boolean checkPassword(String login, String pwd) 
			throws SQLException{
		boolean pwd_ok = true;
		//boolean pwd_ok = false;
		
		return pwd_ok;
	}
	
	public static int getIdUser(String login) throws SQLException{
		int id = 0; /* accéder à l'id dans la BDD */
		return id;
	}
	
	/** retourne une clé de session */
	public static String insertSession(int id, boolean admin)
			throws SQLException{
		String key = "135468eaf4e6fe8z4";
		
		/* build the key */
		
		return key;
	}
	
}
