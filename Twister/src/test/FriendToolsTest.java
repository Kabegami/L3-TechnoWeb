package test;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.sql.SQLException;
import bdd.FriendTools;

public class FriendToolsTest {

	@Test
	public void testAddFriend(){
		JSONObject res = new JSONObject();
		JSONObject inverse = new JSONObject();
		
		String expected1 = "{}";
		String expected2 = "{error_code:2,message:\"User is not logged in\"}";
		
		res = FriendTools.addFriend("toto", "jean");
		inverse = FriendTools.addFriend("jean", "toto");
		System.out.println(inverse);
		try {
			//JSONAssert.assertEquals(expected1, res, true);
			JSONAssert.assertEquals(expected2, inverse, false);
		} catch (JSONException e) {
			e.printStackTrace();
			fail();
		}
	}
}
