package test;

import static org.junit.Assert.*;
import org.junit.Test;

import java.sql.SQLException;
import services.AuthTools;

public class AuthToolsTest {

	@Test
	public void testUserExists() {
		try {
			assertTrue(AuthTools.userExists("toto"));
			assertFalse(AuthTools.userExists("tata"));
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		System.out.println("Succès userExists");
	}
	
	@Test
	public void testCheckPassword(){
		try {
			assertTrue(AuthTools.checkPassword("toto", "123"));
			assertFalse(AuthTools.checkPassword("toto", "tefzmfekfek"));
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		System.out.println("Succès checkPassword");
	}
	
	@Test
	public void testGetIdUser(){
		try {
			assertEquals(AuthTools.getIdUser("toto"), 2);
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		System.out.println("Succès getIdUser");
	}
	
	@Test
	public void testInsertSession(){
		try {
			assertNotNull(AuthTools.insertSession(2, false));
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		System.out.println("Succès insertSession");
	}
	
	@Test
	public void testHasSession(){
		try {
			assertTrue(AuthTools.hasSession(2));
			assertFalse(AuthTools.hasSession(1));
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		System.out.println("Succès hasSession");
	}
	
	@Test
	public void testRemoveSession(){
		try {
			assertTrue(AuthTools.removeSession(2));
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		System.out.println("Succès removeSession");
	}

	
	@Test
	public void testCheckSession(){
		String key = AuthTools.getKey("toto");
		try {
			//AuthTools.updateSession(key);
			AuthTools.checkSession(key);
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
