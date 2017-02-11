package services;

import org.json.JSONObject;
import org.json.JSONException;


public class ErrorJSON {
	
	
	public static JSONObject serviceRefused(String message, int errorCode){
		JSONObject ret = new JSONObject();
		try{
			ret.put("message", message);
			ret.put("error_code", new Integer(errorCode));
		} catch (JSONException e){
			
		}
		return ret;
	}

	public static JSONObject serviceAccepted(){
		return new JSONObject();
	}
}
