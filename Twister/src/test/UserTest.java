package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import org.json.JSONObject;

import bdd.BDException;
import services.ServicesTools;

public class UserTest {
	public static void main(String[] args){
		JSONObject obj = new JSONObject();
		
		/* test createUser */
		boolean id = false;
		try {
			id = ServicesTools.userExists("toto");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(id);
	
		
	}
}
