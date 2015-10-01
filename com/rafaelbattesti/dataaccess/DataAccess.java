package com.rafaelbattesti.dataaccess;

// Import the related packages
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.rafaelbattesti.business.Record;


/**
 * This class is responsible for handling DB connection, queries and logging errors.
 * @author rafaelbattesti - 991382266
 *
 */
public class DataAccess {
	
	// JDBC 
	private static final String JDBC_URL          = "jdbc:mysql://localhost/Laptopdb";
	private static final String JDBC_DRIVER       = "com.mysql.jdbc.Driver";
	
	// Database credentials
	private static final String DB_USER           = "root";
	private static final String DB_PASS           = "1111";
	
	// Error messages
	private static final String ERR_DRIVER        = "JDBC Driver not found";
	private static final String ERR_CONNECT       = "Could not connect to database";
	private static final String ERR_DISCONNECT    = "Could not disconnect from database";
	private static final String ERR_PREPARE       = "Could not prepare statements for SQL";
	private static final String ERR_INSERT        = "Could not insert new record into database";
	private static final String ERR_DELETE        = "Could not delete from the database";
	private static final String ERR_RETR_ID       = "Could not retrieve record by id";
	private static final String ERR_RETR_NAME     = "Could not retireve record by name";
	private static final String ERR_CLOSE_RESULT  = "Could not close the result resource";
	private static final String ERR_DUPE_RECORD   = "User already exists!";
	
	// Success messages
	private static final String SUC_INSERT        = "Record successfully inserted!";
	private static final String SUC_DELETE        = "Record successfully deleted!";
	private static final String SUC_NOT_FOUND     = "Record not found!";
	
	// Record fields
	private static final String ID                = "student_id";
	private static final String NAME              = "first_name";
	private static final String MODEL             = "model";
	private static final String HDD               = "hdd";
	private static final String MEMORY            = "memory";
	private static final String YEAR              = "year";
	
	// Other constants
	private static final String DATE_FORMAT       = "yyyy/MM/dd HH:mm:ss";
	private static final int    CODE_DUPE_RECORD  = 1062;
	
	// Holds a connection object created by the connect() method
	private Connection conn;
	
	// Holds errors
	private String log;
	
	// Holds messages to the upper layer
	private String message;
	
	// Hold Prepared Statements
	private PreparedStatement insertPrep;
	private PreparedStatement deletePrep;
	private PreparedStatement retrieveIdPrep;
	private PreparedStatement retrieveNamePrep;
	
	
	/**
	 * Opens a connection when instantiating
	 */
	public DataAccess() {
		connect();
	}
	
	/**
	 * This prepares all statements after connection.
	 */
	private void prepareAllStatements () {
		// Resets the log attribute
		log = null;
		String insertSql = "INSERT INTO LaptopBorrowerInfo (student_id, first_name, model, hdd, memory, year) ";
		insertSql += "VALUES (?, ?, ?, ?, ?, ?);";
		String deleteSql = "DELETE FROM LaptopBorrowerInfo WHERE student_id = ? AND first_name = ?;";
		String retrieveIdSql = "SELECT * FROM LaptopBorrowerInfo WHERE student_id = ?;";
		String retrieveNameSql = "SELECT * FROM LaptopBorrowerInfo WHERE first_name = ?;";
		try {
			insertPrep = conn.prepareStatement(insertSql);
			deletePrep = conn.prepareStatement(deleteSql);
			retrieveIdPrep = conn.prepareStatement(retrieveIdSql);
			retrieveNamePrep = conn.prepareStatement(retrieveNameSql);
		} catch (SQLException e) {
			log = logErr(e, ERR_PREPARE);
		}
	}
	
	/**
	 * Connects to database.
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
		if (conn != null) {
			prepareAllStatements();	
		}
	}
	
	/**
	 * Disconnects from database.
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
	
	/**
	 * Inserts a new record into the database.
	 * @param record
	 */
	public void insertRecord (Record record) {
		// Resets the log attribute
		log = null;
		// Resets the message
		message = null;
		// Holds the state of the operation
		int affectedRows = 0;
		try {
			// Substitutes placeholders in the prepared statement
			insertPrep.setString(1, record.getStudentId());
			insertPrep.setString(2, record.getFirstName());
			insertPrep.setString(3, record.getModel());
			
			// Assigns proper values to Database (NULL)
			if (!record.getHdd().isEmpty()) {
				insertPrep.setInt(4, Integer.parseInt(record.getHdd()));
			} else {
				insertPrep.setNull(4, Types.NULL);
			}
			if (!record.getMemory().isEmpty()) {
				insertPrep.setInt(5, Integer.parseInt(record.getMemory()));
			} else {
				insertPrep.setNull(5, Types.NULL);
			}
			if (!record.getYear().isEmpty()) {
				insertPrep.setInt(6, Integer.parseInt(record.getYear()));
			} else {
				insertPrep.setInt(6, 2015);
			}

			// Execute the statement
			affectedRows = insertPrep.executeUpdate();
			// Adds success message
			if (affectedRows != 0) {
				message = SUC_INSERT;	
			}
		} catch (SQLException e) {
			log = logErr(e, ERR_INSERT);
			if (e.getErrorCode() == CODE_DUPE_RECORD) {
				message = ERR_DUPE_RECORD;
			}
		}
		
	}
	
	/**
	 * Deletes a single record from the database if user gets id and name correct.
	 * @param student_id
	 * @param first_name
	 */
	public void deleteRecord (String student_id, String first_name) {
		// Resets the log attribute
		log = null;
		// Resets the message
		message = null;
		// Holds the state of the operation
		int affectedRows = 0;
		try {
			// Substitutes placeholder in the prepared statement
			deletePrep.setString(1, student_id);
			deletePrep.setString(2, first_name);
			// Execute the prepared statement
			affectedRows = deletePrep.executeUpdate();
			// Adds success message
			if (affectedRows != 0) {
				message = SUC_DELETE;	
			} else {
				message = SUC_NOT_FOUND;
			}
		} catch (SQLException e) {
			log = logErr(e, ERR_DELETE);
		}
	}
	
	/**
	 * Retrieves a single record by student id (Primary Key)
	 * @param student_id
	 * @return record
	 */
	public ArrayList<Record> retrieveById (String student_id) {
		// Resets the log attribute
		log = null;
		// ResultSet holds the results of the query
		ResultSet result = null;
		// ArrayList To hold the returning record
		ArrayList<Record> recordList = new ArrayList<>();
		try {
			// Substitutes placeholder in the prepared statement
			retrieveIdPrep.setString(1, student_id);
			// Executes the prepared statement
			result = retrieveIdPrep.executeQuery();
			// Creates a new record to return to the upper layer
			while (result.next()) {
				Record record = new Record (
						result.getString(ID),
						result.getString(NAME),
						result.getString(MODEL)
						);
				record.setHdd(Integer.toString(result.getInt(HDD)));
				record.setMemory(Integer.toString(result.getInt(MEMORY)));
				record.setYear(Integer.toString(result.getInt(YEAR)));
				
				recordList.add(record);
			}
		} catch (SQLException e) {
			log = logErr(e, ERR_RETR_ID);
		} finally {
			// Attempts to close resource if it is not null
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					log = logErr(e, ERR_CLOSE_RESULT);
				}
			}
		}
		// Returns the record
		return recordList;
	}
	
	public ArrayList<Record> retrieveByName (String first_name) {
		
		// Resets the log
		log = null;
		
		// ResultSet holds the results
		ResultSet result = null;
		
		// ArrayList holds the list of records
		ArrayList<Record> recordList = new ArrayList<>();
		
		try {
			// Substitutes placeholder in the prepared statement
			retrieveNamePrep.setString(1, first_name);
			// Executes the prepared statement
			result = retrieveNamePrep.executeQuery();
			// Creates a new record to return to the upper layer
			while (result.next()) {
				Record record = new Record (
						result.getString(ID),
						result.getString(NAME),
						result.getString(MODEL)
						);
				record.setHdd(Integer.toString(result.getInt(HDD)));
				record.setMemory(Integer.toString(result.getInt(MEMORY)));
				record.setYear(Integer.toString(result.getInt(YEAR)));
				
				// Add each record to the list
				recordList.add(record);
			}
			
		} catch (SQLException e) {
			log = logErr(e, ERR_RETR_NAME);
		} finally {
			//Attempts to close the resource
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					log = logErr(e, ERR_CLOSE_RESULT);
				}
			}
		}
		// Returns the list of results
		return recordList;
	}
	
	/**
	 * Assigns log message to object's attribute.
	 * @param e
	 * @param errMessage
	 * @return log message
	 */
	private String logErr (Exception e, String errMessage) {
		// Sets the date of the error
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
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
	 * Accesses the log attribute of the DataAccess object.
	 * @return log message
	 */
	public String getLog () {
		return log;
	}
	
	/**
	 * Accesses the message attribute of the DataAccess object
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @param newMessage
	 */
	public void setMessage(String newMessage) {
		message = newMessage;
	}
	
} //End class
