package test;

import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONObject;

import bdd.UserTools;

public class UserTest {
	public static void main(String[] args){
		JSONObject obj = new JSONObject();
		
		/* test createUser */
		obj = UserTools.createUser("toto", "123", "toto", "tata");
		System.out.println(obj);
		
		/* test login */
		obj = UserTools.login("toto", "123");
		System.out.println(obj);
		
	}
}
