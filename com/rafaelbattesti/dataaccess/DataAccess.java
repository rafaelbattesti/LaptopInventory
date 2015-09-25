package com.rafaelbattesti.dataaccess;

// Import the related packages
import java.sql.*;

/**
 * This class is responsible for handling DB connection and queries
 * @author rafaelbattesti
 *
 */
public class DataAccess {
	
	// JDBC 
	private final String JDBC_URL = "jdbc:mysql://localhost/Laptopdb";
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	// Database credentials
	private final String DB_USER = "root";
	private final String DB_PASS = "1111";
	
	// Holds a connection object created by the connect() method
	private Connection conn;
	
	/**
	 * Connects to database
	 */
	public void connect() {
		
		try {
			
			// Load driver
			Class.forName(JDBC_DRIVER);
			
			// Create connection with DriverManager
			conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			
		} catch (ClassNotFoundException e) {
			
			// TODO Log error properly - Could not find Driver class
			e.printStackTrace();
			
		} catch (SQLException e) {
			
			// TODO Log error properly - Could not connect to DB
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Disconnects from database
	 */
	public void disconnect() {

		try {
			
			// Closes connection if one exists
			if (conn != null) {
				conn.close();
			}
			
		} catch (SQLException e) {
			
			// TODO Log error properly - Could not close connection
			e.printStackTrace();
		}

	}
}
