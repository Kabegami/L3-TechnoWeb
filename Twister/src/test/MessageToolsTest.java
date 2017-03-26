package test;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

import bdd.MessageTools;
import bdd.UserTools;
import services.AuthTools;
import services.DBStatic;

public class MessageToolsTest {

	@Test
	public void testCreateMessage(){
		/*String key = AuthTools.getKey("toto");
		JSONObject obj = MessageTools.newMessage(key, "deuxieme message");
		//String expected = "{author_id:2, author_username:\"toto\", }";
		System.out.println(obj);*/
		
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
	public void testGetMessages(){
		String key = AuthTools.getKey("toto");
		JSONObject res = MessageTools.getMessages(key);
		//String expected = "{author_id:2, author_username:\"toto\", }";
		System.out.println(res);
	}
	
	@Test
	public void testSearchMessages(){
		String key = AuthTools.getKey("toto");
		JSONObject res = MessageTools.getMessages(key, "hello", -1, -1, -1, -1);
		//String expected = "{author_id:2, author_username:\"toto\", }";
		System.out.println(res);
	}
}
