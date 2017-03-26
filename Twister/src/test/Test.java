package test;

import java.net.UnknownHostException;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

import services.DBStatic;

public class Test {
	public static void main(String[] args){
		/*
		String pwd = "123";
		String pwdHashed = DigestUtils.sha1Hex(pwd);
		String pwd2 = "dadzadazaad";
		String pwdHashed2 = DigestUtils.sha1Hex(pwd);
		System.out.println(pwdHashed + " " + pwdHashed.length());
		System.out.println(pwdHashed2 + " " + pwdHashed2.length());
		
		JSONObject obj = new JSONObject();
		*/
		
		Mongo m;
		try {
			m = new Mongo(DBStatic.mongo_host, DBStatic.mongo_port);
			DB db = m.getDB(DBStatic.mongo_db);
			DBCollection collection = db.getCollection("messages");
			DBCollection counter = db.getCollection("message_counter");
			
			BasicDBObject query = new BasicDBObject("_id", "msg_id");
			DBCursor cursor = counter.find(query);
			
			while(cursor.hasNext()){
				Double current_id = (Double) cursor.next().get("num");
				System.out.println(current_id);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


	}
}
