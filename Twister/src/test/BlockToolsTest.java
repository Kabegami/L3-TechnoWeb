package test;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import bdd.BlockTools;
import bdd.UserTools;

import java.sql.SQLException;
import services.AuthTools;

public class BlockToolsTest {
	
	@Test
	public void testBlockUser(){
		String key = AuthTools.getKey("toto");
		BlockTools.blockUser(key, 9);
	}
}
