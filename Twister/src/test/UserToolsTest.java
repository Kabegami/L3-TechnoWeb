package test;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import bdd.UserTools;

import java.sql.SQLException;
import services.AuthTools;

public class UserToolsTest {
	
	@Test
	public void testCreateUser(){
		JSONObject obj = UserTools.createUser("toto", "123", "tata", "titi");
		String expected = "{message:\"User already exists\",error_code:1}";
		System.out.println(obj);
		try {
			JSONAssert.assertEquals(expected, obj, true);
			JSONAssert.assertNotEquals("{}", obj, true);
		} catch (JSONException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testLogin(){
		JSONObject obj = UserTools.login("toto", "123");
		System.out.println(obj);
	}
	
	@Test
	public void testLogout(){
		JSONObject obj = UserTools.logout("toto");
		System.out.println(obj);
	}
	
	
}
