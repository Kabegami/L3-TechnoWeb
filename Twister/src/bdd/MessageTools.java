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
import java.util.regex.Pattern;

import services.DBStatic;
import services.ErrorJSON;
import services.AuthTools;

public class MessageTools {
	
	public static JSONObject newMessage(String key, String message){
		if (key == null || message == null){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}
		
		try {
			if (! AuthTools.hasSession(key)){
				return ErrorJSON.serviceRefused("User is not logged in", 2);
			}
			else {
				Mongo m = new Mongo(DBStatic.mongo_host, DBStatic.mongo_port);
				DB db = m.getDB(DBStatic.mongo_db);
				DBCollection collection = db.getCollection("comments");
				
				BasicDBObject comment = new BasicDBObject();
				int author_id = AuthTools.getIdUserSession(key);
				String author_username = AuthTools.getLoginUser(author_id);
				
				comment.put("author_id", author_id);
				comment.put("author_username", author_username);
				comment.put("text", message);
				comment.put("date", new Date());
				collection.insert(comment);
				
				m.close();
				JSONObject res = new JSONObject(comment.toString());
				
				AuthTools.updateSession(key);

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
	
	public static JSONObject listMessages(String key){
		JSONObject finalQuery = new JSONObject();
		JSONArray messages = new JSONArray();
		
		if (key == null){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}
		
		try {
			if (! AuthTools.hasSession(key)){
				return ErrorJSON.serviceRefused("User is not logged in", 2);
			}
			else {
				int id = AuthTools.getIdUserSession(key);
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
				AuthTools.updateSession(key);
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
	public static JSONObject search(String key, String query){
		JSONObject finalQuery = new JSONObject();
		JSONArray messages = new JSONArray();
		
		if (key == null || query == null){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}
		
		try {
			if (! AuthTools.hasSession(key)){
				return ErrorJSON.serviceRefused("User is not logged in", 2);
			}
			Mongo m = new Mongo(DBStatic.mongo_host, DBStatic.mongo_port);
			DB db = m.getDB(DBStatic.mongo_db);
			DBCollection collection = db.getCollection("comments");
			
			// initialisation de la liste d'amis
			List<Integer> friendsID = new ArrayList<Integer>();
			JSONObject friendsQuery = FriendTools.listFriends(key);
			JSONArray friends = friendsQuery.getJSONArray("friends");
			for (int i = 0; i < friends.length(); i++){
				friendsID.add((Integer)friends.getJSONObject(i).get("id"));
			}
			
			// initialisation de la requete sur MongoDB
			// liste des amis
			BasicDBObject friendsFilter = new BasicDBObject();
			friendsFilter.put("author_id", new BasicDBObject ("$in", friendsID));
			
			// recherche du motif
			BasicDBObject like = new BasicDBObject();
			like.put("text", Pattern.compile(query));
			
			// on combine ces conditions avec $and
			BasicDBObject queryToDo = new BasicDBObject();
			List<BasicDBObject> and = new ArrayList<BasicDBObject>();
			and.add(friendsFilter);
			and.add(like);
			queryToDo.put("$and", and);
			
			
			DBCursor cursor = collection.find(queryToDo).sort(new BasicDBObject("date", -1));
			while (cursor.hasNext()){
				messages.put(new JSONObject(cursor.next().toString()));
			}
			m.close();
			finalQuery.put("messages", messages);
			AuthTools.updateSession(key);

			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return finalQuery;
	}
}
