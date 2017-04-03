package test;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.sql.SQLException;
import bdd.FollowTools;
import services.AuthTools;

public class FollowToolsTest {

	@Test
	public void testAddFollow(){
		String key = AuthTools.getKey("toto");

		JSONObject res = new JSONObject();
		JSONObject inverse = new JSONObject();
		
		String expected1 = "{}";
		//String expected2 = "{error_code:2,message:\"User is not logged in\"}";
		
		res = FollowTools.addFollow(key, 4);
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
	public void testListFollows(){
		String key = AuthTools.getKey("toto");
		JSONObject res = FollowTools.listFollows(key);
		//System.out.println(res);
		
		try {
			System.out.println(res.get("follows"));
			JSONArray follows = res.getJSONArray("follows");
			for (int i = 0; i < follows.length(); i++){
				System.out.println(follows.getJSONObject(i).get("id"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testListSubscribers(){
		JSONObject res = FollowTools.listSubscribers(2);
		//System.out.println(res);
		
		try {
			System.out.println(res.get("subscribers"));
			JSONArray subscribers = res.getJSONArray("subscribers");
			for (int i = 0; i < subscribers.length(); i++){
				System.out.println(subscribers.getJSONObject(i).get("id"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testStopFollow(){
		String key = AuthTools.getKey("toto");

		JSONObject res = new JSONObject();
		JSONObject inverse = new JSONObject();
		
		String expected1 = "{}";
		
		res = FollowTools.stopFollow(key, 4);
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
