package services;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceCommand.OutputType;

public class MapReduce {
	private static String map = ""
			+ "function map(){"
				+ "var text = this.text;"
				+ "var id = this.id;"
				+ "var words = text.match(/\\w+/g);"
				+ "var tf = {};"
				+ "for (var i = 0; i < words.length; i++){"
					+ "if (tf[words[i]] == 0){"
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
		    + "var df = Object.keys(v).length;"
		    + "for (d in values){"
		        + "values[d] = values[d] - Math.log(N/df);"
		    + "}"
		    + "return values;"
		+ "}";
	private static String out = "";
	private static OutputType outputType = OutputType.REPLACE;
	
	public static void callMapReduce(DBCollection coll){
		MapReduceCommand cmd = new MapReduceCommand(coll, map, reduce, out,
												outputType, null);
		cmd.setFinalize(finalize);
		BasicDBObject total = new BasicDBObject();
		total.put("N", coll.count());
		cmd.setScope(total);
		coll.mapReduce(cmd);
	}
	
	public static void main(String args[]){
		System.out.println(map);
	}
}
