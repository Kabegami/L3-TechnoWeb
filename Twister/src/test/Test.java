package test;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Test {
	public static void main(String[] args){
		String pwd = "123";
		String pwdHashed = DigestUtils.sha1Hex(pwd);
		String pwd2 = "dadzadazaad";
		String pwdHashed2 = DigestUtils.sha1Hex(pwd);
		System.out.println(pwdHashed + " " + pwdHashed.length());
		System.out.println(pwdHashed2 + " " + pwdHashed2.length());
		
		JSONObject obj = new JSONObject();


	}
}
