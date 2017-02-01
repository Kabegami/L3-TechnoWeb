package services;

import java.util.UUID;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bdd.BDException;

public class Password {
	
	public static String generateRandomKey(){
			
			UUID key = UUID.randomUUID();
			return key.toString();
	}
}
