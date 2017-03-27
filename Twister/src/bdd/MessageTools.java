package bdd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.lang3.math.NumberUtils;

import com.mongodb.BasicDBList;
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
	
	public static JSONObject newMessage(String key, String text){
		if (key == null || text == null){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}
		try {
			if (! AuthTools.checkSession(key)){
				return ErrorJSON.serviceRefused("User is not logged in", 2);
			}
			else {
				Mongo m = new Mongo(DBStatic.mongo_host, DBStatic.mongo_port);
				DB db = m.getDB(DBStatic.mongo_db);
				DBCollection collection = db.getCollection("messages");
				
				BasicDBObject message = new BasicDBObject();
				int author_id = AuthTools.getIdUserSession(key);
				String author_username = AuthTools.getLoginUser(author_id);
				
				// auteur
				BasicDBObject author = new BasicDBObject();						
				author.put("id", author_id);
				author.put("username", author_username);				
				message.put("author", author);
				
				// incrémentation id message
				DBCollection counter = db.getCollection("message_counter");
				BasicDBObject query = new BasicDBObject("_id", "msg_id");
				DBCursor cursor = counter.find(query);
				if (cursor.hasNext()){
					Double current_id = (Double) cursor.next().get("num");
					message.put("id", current_id);
				}
				BasicDBObject update = new BasicDBObject();
				update.put("$inc", new BasicDBObject("num", 1));
				counter.update(new BasicDBObject("_id", "msg_id"), update);

				message.put("text", text);
				message.put("date", new Date().getTime());
				message.put("comments", new BasicDBList());
				collection.insert(message);
				
				m.close();
				JSONObject res = new JSONObject(message.toString());
				
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
			if (key == null || id_message == null || text == null){
				return ErrorJSON.serviceRefused("Wrong arguments", -1);
			}
			if (! NumberUtils.isCreatable(id_message)){
				return ErrorJSON.serviceRefused("id_message non integer", -1);
			}
			try {
				if (! AuthTools.checkSession(key)){
					return ErrorJSON.serviceRefused("User is not logged in", 2);
				}
				else {
					Mongo m = new Mongo(DBStatic.mongo_host, DBStatic.mongo_port);
					DB db = m.getDB(DBStatic.mongo_db);
					DBCollection message_coll = db.getCollection("messages");
					
					BasicDBObject comment = new BasicDBObject();
					int author_id = AuthTools.getIdUserSession(key);
					String author_username = AuthTools.getLoginUser(author_id);
					
					// auteur
					BasicDBObject author = new BasicDBObject();						
					author.put("id", author_id);
					author.put("username", author_username);				
					comment.put("author", author);
					
					// incrémentation id commentaire
					DBCollection counter = db.getCollection("comment_counter");
					BasicDBObject query = new BasicDBObject("_id", "com_id");
					DBCursor cursor = counter.find(query);
					if (cursor.hasNext()){
						Double current_id = (Double) cursor.next().get("num");
						comment.put("id", current_id);
					}
					BasicDBObject update = new BasicDBObject();
					update.put("$inc", new BasicDBObject("num", 1));
					counter.update(new BasicDBObject("_id", "com_id"), update);

					comment.put("text", text);
					comment.put("date", new Date().getTime());
					
					// récupération du message correspondant
					int id_m = Integer.parseInt(id_message);
					BasicDBObject update_message = new BasicDBObject();
					update_message.put("$push", new BasicDBObject("comments", comment));
					message_coll.update(new BasicDBObject("id", id_m), update_message);
					
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
	
	
	public static JSONObject getMessages(String key){
		JSONObject finalQuery = new JSONObject();
		JSONArray messages = new JSONArray();
		
		if (key == null){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}
		
		try {
			if (! AuthTools.checkSession(key)){
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
	public static JSONObject getMessages(String key, String query, String from, String id_max, String id_min, String nb){
		JSONObject finalQuery = new JSONObject();
		JSONArray messages = new JSONArray();
		
		if (key == null || ! NumberUtils.isCreatable(from) || ! NumberUtils.isCreatable(id_max)
				|| ! NumberUtils.isCreatable(id_min) || ! NumberUtils.isCreatable(nb)){
			return ErrorJSON.serviceRefused("Wrong arguments", -1);
		}
		try {
			if (! AuthTools.checkSession(key)){
				return ErrorJSON.serviceRefused("User is not logged in", 2);
			}
			
			// connexion database
			Mongo m = new Mongo(DBStatic.mongo_host, DBStatic.mongo_port);
			DB db = m.getDB(DBStatic.mongo_db);
			DBCollection collection = db.getCollection("messages");
			
			// initialisation de la liste d'amis
			List<Integer> friendsID = new ArrayList<Integer>();
			JSONObject friendsQuery = FollowTools.listFollows(key);
			JSONArray friends = friendsQuery.getJSONArray("follows");
			// on ajoute l'id de l'utilisateur courant
			int own_id = Integer.parseInt(from);
			friendsID.add(own_id);
			for (int i = 0; i < friends.length(); i++){
				friendsID.add((Integer)friends.getJSONObject(i).get("id"));
			}
			
			// initialisation de la requete sur MongoDB
			// on combine les conditions avec $and
			List<BasicDBObject> and = new ArrayList<BasicDBObject>();
			
			// liste des amis
			BasicDBObject friendsFilter = new BasicDBObject();
			friendsFilter.put("author.id", new BasicDBObject ("$in", friendsID));
			and.add(friendsFilter);

			// recherche du motif
			if (query != null){
				BasicDBObject like = new BasicDBObject();
				like.put("text", Pattern.compile(query));
				and.add(like);
			}
			
			// id_min, id_max
			int id_min2 = Integer.parseInt(id_min);
			int id_max2 = Integer.parseInt(id_max);
			if (id_min2 > 0){
				BasicDBObject idMinLimit = new BasicDBObject();
				idMinLimit.put("id", new BasicDBObject("$gt", id_min2));
				and.add(idMinLimit);
			}
			if (id_max2 > 0){
				BasicDBObject idMaxLimit = new BasicDBObject();
				idMaxLimit.put("id", new BasicDBObject("$lte", id_max2));
				and.add(idMaxLimit);
			}
			
			BasicDBObject queryToDo = new BasicDBObject();
			queryToDo.put("$and", and);
			System.out.println(queryToDo);
			
			int nb2 = Integer.parseInt(nb);
			DBCursor cursor;
			if (nb2 > 0){
				cursor = collection.find(queryToDo).sort(new BasicDBObject("date", -1)).limit(nb2);
			}
			else {
				cursor = collection.find(queryToDo).sort(new BasicDBObject("date", -1));
			}
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
