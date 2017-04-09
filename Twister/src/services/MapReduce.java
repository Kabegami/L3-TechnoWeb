 package services;

import java.io.Console;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.bson.BasicBSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.Mongo;
import com.mongodb.MapReduceCommand.OutputType;
import com.mongodb.MapReduceOutput;

public class MapReduce {
	private static String map = ""
			+ "function map(){"
				+ "var text = this.text;"
				+ "var id = this.id;"
				+ "var words = text.match(/\\w+/g);"
				+ "var tf = {};"
				+ "for (var i = 0; i < words.length; i++){"
					+ "if (tf[words[i]] == undefined){"
						+ "tf[words[i]] = 1;"
					+ "}"
					+ "else {"
						+ "tf[words[i]] += 1;"
					+ "}"
				+ "}"
				+ "for (w in tf){"
					+ "var ret = {};"
					+ "ret[id] = tf[w];"
					+ "emit(w, ret);"
				+ "}"
			+ "}";
	private static String reduce = ""
			+ "function reduce(key, values){"
		    + "var ret = {};"
		    + "for (var i = 0; i < values.length; i++){"
		        + "for (var d in values[i]){"
		            + "ret[d] = values[i][d];"
		        + "}"
		    + "}"
		    + "return ret;"
		+ "}";
	private static String finalize = ""
			+ "function finalize(key, values){"
		    + "var df = Object.keys(values).length;"
		    + "for (d in values){"
		        + "values[d] = values[d] - Math.log(N/df);"
		    + "}"
		    + "return values;"
		+ "}";
	private static String out = "output";
	private static OutputType outputType = OutputType.REPLACE;
	
	public static MapReduceOutput callMapReduce(DBCollection coll){
		MapReduceCommand cmd = new MapReduceCommand(coll, map, reduce, out,
												outputType, null);
		cmd.setFinalize(finalize);
		BasicDBObject total = new BasicDBObject();
		total.put("N", coll.count());
		cmd.setScope(total);
		return coll.mapReduce(cmd);
	}
	
	public static List<DBObject> getMessagesQuery(DBCollection index,
			DBCollection documents, String query){
		List<DBObject> res = new ArrayList<DBObject>();
		String[] q = query.split(" ");
		HashSet<String> words = new HashSet<String>();
		for (String s : q){
			words.add(s);
		}
		
		// LinkedHashMap pour garder l'ordre
		Map<String, Double> score = new LinkedHashMap<String, Double>();
		for (String s : words){
			BasicDBObject obj = new BasicDBObject();
			obj.put("_id", s);
			DBCursor cursor = index.find(obj);
			if (cursor.hasNext()){
				DBObject o = cursor.next();
				BasicBSONObject res2 = (BasicBSONObject)o.get("value");
				score.putAll(res2.toMap());
			}
		}
		
		// tri par valeurs décroissantes
		List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(score.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>(){
			public int compare(Map.Entry<String, Double> e1, Map.Entry<String, Double> e2){
				return -e1.getValue().compareTo(e2.getValue());
			}
		});
		
		Map<Integer, Double> score2 = new LinkedHashMap<Integer, Double>();
		for (Map.Entry<String, Double> e : list){
			score2.put(Integer.parseInt(e.getKey()), e.getValue());
		}
		System.out.println(score2);
		Set<Integer> idMessages = score2.keySet();
		System.out.println(idMessages);
		
		for (Integer id : idMessages){
			DBCursor cursor = documents.find(new BasicDBObject("id", id));
			if (cursor.hasNext()){
				res.add(cursor.next());
			}
		}
		return res;
	}
	
	public static void main(String args[]){
		try {
			Mongo m = new Mongo(DBStatic.mongo_host, DBStatic.mongo_port);
			DB db = m.getDB(DBStatic.mongo_db);
			DBCollection collection = db.getCollection("messages");
			MapReduceOutput out = callMapReduce(collection);
			for (DBObject o : out.results()){
				System.out.println(o.toString());
			}
		} catch (UnknownHostException e){
			e.printStackTrace();
		}
	}
}
