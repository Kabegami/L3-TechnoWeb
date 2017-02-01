package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import org.json.JSONObject;

import services.ServicesTools;

public class UserTest {
	public static void main(String[] args){
		JSONObject obj = new JSONObject();
		
		/* test createUser */
		boolean exists;
		exists = ServicesTools.userExists("toto");
		System.out.println(exists);
	
		
	}
}
