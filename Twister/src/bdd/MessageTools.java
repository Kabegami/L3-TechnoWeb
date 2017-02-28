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
				DBCollection collection = db.getCollection("messages");
				
				BasicDBObject comment = new BasicDBObject();
				int author_id = AuthTools.getIdUserSession(key);
				String author_username = AuthTools.getLoginUser(author_id);
				
				BasicDBObject author = new BasicDBObject();						
				author.put("id", author_id);
				author.put("username", author_username);
				
				comment.put("author", author);
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
	
	public static JSONObject newComment(String key, String id_message, String text){
		JSONObject res = new JSONObject();
		
		// todo
		
		return res;
	}
	
	
	public static JSONObject getMessages(String key){
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
				DBCollection collection = db.getCollection("messages");

				// recherche par id de l'auteur
				BasicDBObject query = new BasicDBObject();
				query.put("author.id", id);
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
	
	/**
	 * 
	 * @param key clé de session
	 * @param query mots clé
	 * @param from id de l'utilisateur concerné (-1 si page principale et qu'on veut tout avoir)
	 * @param id_max -1 si pas de limite
	 * @param id_min -1 si pas de limite
	 * @param nb nombre de messages à retourner (-1 si pas de limite)
	 * @return
	 */
	public static JSONObject getMessages(String key, String query, int from, int id_max, int id_min, int nb){
		JSONObject finalQuery = new JSONObject();
		JSONArray messages = new JSONArray();
		
		if (key == null || query == null){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}
		
		try {
			if (! AuthTools.hasSession(key)){
				return ErrorJSON.serviceRefused("User is not logged in", 2);
			}
			
			// connexion database
			Mongo m = new Mongo(DBStatic.mongo_host, DBStatic.mongo_port);
			DB db = m.getDB(DBStatic.mongo_db);
			DBCollection collection = db.getCollection("messages");
			
			// initialisation de la liste d'amis
			List<Integer> friendsID = new ArrayList<Integer>();
			JSONObject friendsQuery = FollowTools.listFollows(key);
			JSONArray friends = friendsQuery.getJSONArray("friends");
			for (int i = 0; i < friends.length(); i++){
				friendsID.add((Integer)friends.getJSONObject(i).get("id"));
			}
			
			// initialisation de la requete sur MongoDB
			// liste des amis
			BasicDBObject friendsFilter = new BasicDBObject();
			friendsFilter.put("author.id", new BasicDBObject ("$in", friendsID));
			
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
