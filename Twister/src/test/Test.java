package test;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

import services.DBStatic;
import services.Database;

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
		
		try {
			/*
			m = new Mongo(DBStatic.mongo_host, DBStatic.mongo_port);
			DB db = m.getDB(DBStatic.mongo_db);
			DBCollection collection = db.getCollection("messages");
			DBCollection counter = db.getCollection("message_counter");
			
			BasicDBObject query = new BasicDBObject("_id", "msg_id");
			DBCursor cursor = counter.find(query);
			
			while(cursor.hasNext()){
				Double current_id = (Double) cursor.next().get("num");
				System.out.println(current_id);
			}*/
				Connection conn = Database.getMySQLConnection();
				Statement st = conn.createStatement();
				String query = "SELECT reg_date FROM Users";
				ResultSet rs = st.executeQuery(query);
				while(rs.next()){
					Timestamp ts = rs.getTimestamp("reg_date");
					System.out.println(ts);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
}
