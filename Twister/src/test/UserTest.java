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
		int id = 0;
		id = ServicesTools.getIdUser("toto");

		System.out.println(id);
	
		
	}
}
