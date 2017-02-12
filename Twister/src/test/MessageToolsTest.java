package test;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import bdd.MessageTools;
import bdd.UserTools;

public class MessageToolsTest {

	@Test
	public void testCreateMessage(){
		JSONObject obj = MessageTools.newMessage("toto", "troisieme message");
		//String expected = "{author_id:2, author_username:\"toto\", }";
		System.out.println(obj);
		/*
		try {
			JSONAssert.assertEquals(expected, obj, true);
			JSONAssert.assertNotEquals("{}", obj, true);
		} catch (JSONException e) {
			e.printStackTrace();
			fail();
		}
		*/
	}
	
	@Test
	public void testListMessages(){
		JSONObject res = MessageTools.listMessages(2);
		//String expected = "{author_id:2, author_username:\"toto\", }";
		System.out.println(res);
	}
}
