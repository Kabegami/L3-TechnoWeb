package test;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.sql.SQLException;
import bdd.FriendTools;
import services.AuthTools;

public class FriendToolsTest {

	@Test
	public void testAddFriend(){
		String key = AuthTools.getKey("toto");

		JSONObject res = new JSONObject();
		JSONObject inverse = new JSONObject();
		
		String expected1 = "{}";
		//String expected2 = "{error_code:2,message:\"User is not logged in\"}";
		
		res = FriendTools.addFriend(key, 4);
		//inverse = FriendTools.addFriend("jean", "toto");
		//System.out.println(inverse);
		try {
			JSONAssert.assertEquals(expected1, res, true);
			//JSONAssert.assertEquals(expected2, inverse, false);
		} catch (JSONException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testListFriends(){
		String key = AuthTools.getKey("toto");
		JSONObject res = FriendTools.listFriends(key);
		//System.out.println(res);
		
		try {
			System.out.println(res.get("friends"));
			JSONArray friends = res.getJSONArray("friends");
			for (int i = 0; i < friends.length(); i++){
				System.out.println(friends.getJSONObject(i).get("id"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testRemoveFriend(){
		String key = AuthTools.getKey("toto");

		JSONObject res = new JSONObject();
		JSONObject inverse = new JSONObject();
		
		String expected1 = "{}";
		
		res = FriendTools.removeFriend(key, 4);
		//inverse = FriendTools.addFriend("jean", "toto");
		//System.out.println(inverse);
		try {
			JSONAssert.assertEquals(expected1, res, true);
			//JSONAssert.assertEquals(expected2, inverse, false);
		} catch (JSONException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
}
