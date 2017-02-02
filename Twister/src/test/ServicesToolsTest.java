package test;

import static org.junit.Assert.*;
import org.junit.Test;

import java.sql.SQLException;
import services.ServicesTools;

public class ServicesToolsTest {

	@Test
	public void testUserExists() {
		try {
			assertTrue(ServicesTools.userExists("toto"));
			assertFalse(ServicesTools.userExists("tata"));
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		System.out.println("Succès userExists");
	}
	
	@Test
	public void testCheckPassword(){
		try {
			assertTrue(ServicesTools.checkPassword("toto", "123"));
			assertFalse(ServicesTools.checkPassword("toto", "tefzmfekfek"));
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		System.out.println("Succès checkPassword");
	}
	
	@Test
	public void testGetIdUser(){
		try {
			assertEquals(ServicesTools.getIdUser("toto"), 1);
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		System.out.println("Succès getIdUser");
	}
	
	@Test
	public void testInsertSession(){
		try {
			assertNotNull(ServicesTools.insertSession(2, false));
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		System.out.println("Succès insertSession");
	}
	
	@Test
	public void testRemoveSession(){
		try {
			assertTrue(ServicesTools.removeSession(2));
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		System.out.println("Succès removeSession");
	}
	
}
