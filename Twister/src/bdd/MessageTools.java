package bdd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import services.DBStatic;
import services.ErrorJSON;
import services.AuthTools;

public class MessageTools {
	
	public static JSONObject newMessage(String login, String message){
		if (login == null || message == null){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}
		
		try {
			if (! AuthTools.userExists(login)){
				return ErrorJSON.serviceRefused("User doesn't exist", 1);
			}
			
			int id = AuthTools.getIdUser(login);
			if (! AuthTools.hasSession(id)){
				return ErrorJSON.serviceRefused("User is not logged in", 2);
			}
			else {
				Mongo m = new Mongo(DBStatic.mongo_host, DBStatic.mongo_port);
				DB db = m.getDB(DBStatic.mongo_db);
				DBCollection collection = db.getCollection("comments");
				
				BasicDBObject comment = new BasicDBObject();
				String author_login = AuthTools.getLoginUser(id);
				
				comment.put("author_id", id);
				comment.put("author_username", author_login);
				comment.put("text", message);
				comment.put("date", new Date());
				collection.insert(comment);
				
				m.close();
				JSONObject res = new JSONObject(comment.toString());
				return res;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new JSONObject();
	}
	
	public static JSONObject listMessages(String login){
		JSONObject finalQuery = new JSONObject();
		JSONArray messages = new JSONArray();
		
		if (login == null){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}
		
		try {
			if (! AuthTools.userExists(login)){
				return ErrorJSON.serviceRefused("User doesn't exist", 1);
			}
			int id = AuthTools.getIdUser(login);
			if (! AuthTools.hasSession(id)){
				return ErrorJSON.serviceRefused("User is not logged in", 2);
			}
			else {
				Mongo m = new Mongo(DBStatic.mongo_host, DBStatic.mongo_port);
				DB db = m.getDB(DBStatic.mongo_db);
				DBCollection collection = db.getCollection("comments");
				
				BasicDBObject query = new BasicDBObject();
				query.put("author_id", id);
				DBCursor cursor = collection.find(query);
				while (cursor.hasNext()){
					messages.put(new JSONObject(cursor.next().toString()));
				}
				m.close();
				finalQuery.put("messages", messages);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return finalQuery;
	}
	
	// recherche de messages parmi tous les amis
	public static JSONObject search(int user_id, String query){
		JSONObject finalQuery = new JSONObject();
		JSONArray messages = new JSONArray();
		
		if (user_id == 0 || query == null){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}
		
		try {
			if (! AuthTools.userExists(user_id)){
				return ErrorJSON.serviceRefused("User doesn't exist", 1);
			}
			if (! AuthTools.hasSession(user_id)){
				return ErrorJSON.serviceRefused("User is not logged in", 2);
			}
			Mongo m = new Mongo(DBStatic.mongo_host, DBStatic.mongo_port);
			DB db = m.getDB(DBStatic.mongo_db);
			DBCollection collection = db.getCollection("comments");
			List<Integer> friendsID = new ArrayList<Integer>();
			
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new JSONObject();
	}
}
