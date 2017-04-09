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
	public void testGetMessagesUser(){
		String key = AuthTools.getKey("toto");
		String from = String.valueOf(2);
		String id_max = String.valueOf(-1);
		String id_min = String.valueOf(-1);
		String nb = String.valueOf(10);

		JSONObject res = MessageTools.getMessagesUser(key, from, id_max, id_min, nb) ;
		//String expected = "{author_id:2, author_username:\"toto\", }";
		try {
			System.out.println(res.toString(4));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSearchMessages(){
		String key = AuthTools.getKey("toto");
		String from = String.valueOf(2);
		String id_max = String.valueOf(-1);
		String id_min = String.valueOf(-1);
		String nb = String.valueOf(10);
		String query = "message";
		
		JSONObject res = MessageTools.getMessages(key, query, from, id_max, id_min, nb);
		try {
			System.out.println(res.toString(4));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
