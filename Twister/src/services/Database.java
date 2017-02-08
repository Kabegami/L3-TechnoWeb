package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Database {
	private static DataSource dataSource;
	private static Database database;
	
	public Database(String jndiname) throws SQLException {
		try {
			dataSource = (DataSource) new InitialContext().lookup("java:comp/env" + jndiname);
		} catch (NamingException e) {
			throw new SQLException(jndiname + " is missing in JNDI : " + e.getMessage());
		}
	}
	
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	public static Connection getMySQLConnection() throws SQLException {
		Connection conn = null;
		if (DBStatic.mysql_pooling == false){
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://" + DBStatic.mysql_host + "/" +
					DBStatic.mysql_db + "?user=" + DBStatic.mysql_username + "&password=" + DBStatic.mysql_password);
			
		}
		else {
			if (database == null){
				database = new Database("jdbc/db");
				conn = database.getConnection();
			}
		}
		return conn;
	}
}
