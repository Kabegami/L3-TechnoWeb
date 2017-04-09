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
		JSONObject obj = UserTools.createUser("toto", "123", "tata", "titi", "totomail");
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
	public void testCreateUser2(){
		JSONObject obj = UserTools.createUser("aaa", "123", "aaa", "aaa", "aaamail");
		try {
			System.out.println(obj.toString(4));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLogin(){
		JSONObject obj = UserTools.login("toto", "123");
		System.out.println(obj);
	}
	
	@Test
	public void testLogout(){
		String key = AuthTools.getKey("toto");
		JSONObject obj = UserTools.logout(key);
		System.out.println(obj);
	}
	
	
}
