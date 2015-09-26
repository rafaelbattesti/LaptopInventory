package com.rafaelbattesti.dataaccess;

// Import the related packages
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.rafaelbattesti.business.Record;


/**
 * This class is responsible for handling DB connection, queries and logging errors.
 * @author rafaelbattesti - 991382266
 *
 */
public class DataAccess {
	
	// JDBC 
	private final String JDBC_URL       = "jdbc:mysql://localhost/Laptopdb";
	private final String JDBC_DRIVER    = "com.mysql.jdbc.Driver";
	
	// Database credentials
	private final String DB_USER        = "root";
	private final String DB_PASS        = "1111";
	
	// Err messages
	private final String ERR_DRIVER     = "JDBC Driver not found";
	private final String ERR_CONNECT    = "Could not connect to database";
	private final String ERR_DISCONNECT = "Could not disconnect from database";
	private final String ERR_PREPARE    = "Could not prepare statements for SQL";
	private final String ERR_INSERT     = "Could not insert new record into database";
	
	// Holds a connection object created by the connect() method
	private Connection conn;
	
	// Holds errors
	private String log;
	
	// Hold Prepared Statements
	private PreparedStatement insertStatement;
	
	/**
	 * This prepares all statements after connection.
	 */
	private void prepareAllStatements () {
		
		// Resets the log attribute
		log = null;
		
		String insertSql = "INSERT INTO LaptopBorrowerInfo (student_id, first_name, model, hdd, memory, year) ";
		insertSql += "VALUES (?, ?, ?, ?, ?, ?);";
		
		try {
			insertStatement = conn.prepareStatement(insertSql);
		} catch (SQLException e) {
			log = logErr(e, ERR_PREPARE);
		}
	}
	
	/**
	 * Connects to database
	 */
	public void connect() {
		
		// Resets the log attribute
		log = null;
		
		try {
			
			// Load driver
			Class.forName(JDBC_DRIVER);
			
			// Create connection with DriverManager
			conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			
		} catch (ClassNotFoundException e) {
			log = logErr(e, ERR_DRIVER);
		} catch (SQLException e) {
			log = logErr(e, ERR_CONNECT);
		}
		
		// Prepare all statements
		prepareAllStatements();
	}
	
	/**
	 * Disconnects from database
	 */
	public void disconnect() {

		// Resets the log attribute
		log = null;
		
		try {
			
			// Closes connection if one exists
			if (conn != null) {
				conn.close();
			}
			
		} catch (SQLException e) {	
			log = logErr(e, ERR_DISCONNECT);
		}

	}
	
	public void insertRecord (Record record) {
		
		log = null;
		
		try {
			
			// Substitutes placeholders in the prepared statement
			insertStatement.setString(1, record.getStudentId());
			insertStatement.setString(2, record.getFirstName());
			insertStatement.setString(3, record.getModel());
			insertStatement.setInt(4, record.getHdd());
			insertStatement.setInt(5, record.getMemory());
			insertStatement.setInt(6, record.getYear());
			
			// Execute the statement
			insertStatement.executeUpdate();
			
		} catch (SQLException e) {

			log = logErr(e, ERR_INSERT);
		}
		
	}
	
	/**
	 * Assigns log message to object's attribute
	 * @param e
	 * @param errMessage
	 * @return log message
	 */
	private String logErr (Exception e, String errMessage) {
		
		// Sets the date of the error
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String date = df.format(new Date());
		
		// Logs main SQLException attributes for debugging or only the errMessage
		if (e instanceof SQLException) {
			SQLException ex = (SQLException) e;
			return date + ":::" + errMessage + ":::" + ex.getSQLState() + ":::" + ex.getErrorCode();
		} else {
			return date + ":::" + errMessage;
		}
	}
	
	/**
	 * @return log attribute
	 */
	public String getLog () {
		return log;
	}
	
}
