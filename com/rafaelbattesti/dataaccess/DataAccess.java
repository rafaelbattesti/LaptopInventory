package com.rafaelbattesti.dataaccess;

import com.rafaelbattesti.business.Record;

import java.sql.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;


/**
 * This class is responsible for handling DB connection, queries and logging errors.
 * @author rafaelbattesti - 991382266
 *
 */
public class DataAccess {
	
	/**
	 * URL to connect to database. This can be change with the host domain name or IP address.
	 * I will demonstrate this in class for the extra points, as mobile.sheridanc.on.ca does not accept
	 * remote connections, only connections from the localhost.
	 */
	private static final String JDBC_URL          = "jdbc:mysql://localhost/Laptopdb";
	
	/**
	 * Location of the JDBC MySQL driver.
	 */
	private static final String JDBC_DRIVER       = "com.mysql.jdbc.Driver";
	
	/**
	 * Database username.
	 */
	private static final String DB_USER           = "root";
	
	/**
	 * Database password.
	 */
	private static final String DB_PASS           = "1111";
	
	/**
	 * Error message for driver not found.
	 */
	private static final String ERR_DRIVER        = "JDBC Driver not found";
	
	/**
	 * Error message for connection.
	 */
	private static final String ERR_CONNECT       = "Could not connect to database";
	
	/**
	 * Error message for disconnection.
	 */
	private static final String ERR_DISCONNECT    = "Could not disconnect from database";
	
	/**
	 * Error message for prepared statements.
	 */
	private static final String ERR_PREPARE       = "Could not prepare statements for SQL";
	
	/**
	 * Error message for add record.
	 */
	private static final String ERR_INSERT        = "Could not insert new record into database";
	
	/**
	 * Error message for delete record.
	 */
	private static final String ERR_DELETE        = "Could not delete from the database";
	
	/**
	 * Error message for search record by ID.
	 */
	private static final String ERR_RETR_ID       = "Could not retrieve record by id";
	
	/**
	 * Error message for search record by name.
	 */
	private static final String ERR_RETR_NAME     = "Could not retireve record by name";
	
	/**
	 * Error message for closing resources.
	 */
	private static final String ERR_CLOSE_RESULT  = "Could not close the result resource";
	
	/**
	 * Error message for adding duplicate user.
	 */
	private static final String ERR_DUPE_RECORD   = "User already exists!";
	
	/**
	 * Success message for add record.
	 */
	private static final String SUC_INSERT        = "Record successfully inserted!";
	
	/**
	 * Success message for delete record.
	 */
	private static final String SUC_DELETE        = "Record successfully deleted!";
	
	/**
	 * Success message for record not found.
	 */
	private static final String SUC_NOT_FOUND     = "Record not found!";
	
	/**
	 * student_id field
	 */
	private static final String ID                = "student_id";
	
	/**
	 * first_name field
	 */
	private static final String NAME              = "first_name";
	
	/**
	 * model field
	 */
	private static final String MODEL             = "model";
	
	/**
	 * hdd field.
	 */
	private static final String HDD               = "hdd";
	
	/**
	 * memory field
	 */
	private static final String MEMORY            = "memory";
	
	/**
	 * year field
	 */
	private static final String YEAR              = "year";
	
	/**
	 * Date format for logging.
	 */
	private static final String DATE_FORMAT       = "yyyy/MM/dd HH:mm:ss";
	
	/**
	 * Date format for year (current year)
	 */
	private static final String DATE_FORMAT_YEAR  = "yyyy";
	
	/**
	 * User duplicate error code.
	 */
	private static final int    CODE_DUPE_RECORD  = 1062;
	
	/**
	 * Database connection object.
	 */
	private Connection _conn;
	
	/**
	 * Error log.
	 */
	private String _log;
	
	/**
	 * Message for upper layers.
	 */
	private String _message;
	
	/**
	 * Insert prepared statement.
	 */
	private PreparedStatement _insertPrep;
	
	/**
	 * Delete prepared statement.
	 */
	private PreparedStatement _deletePrep;
	
	/**
	 * Select by ID prepared statement.
	 */
	private PreparedStatement _retrieveIdPrep;
	
	/**
	 * Select by name prepared statement.
	 */
	private PreparedStatement _retrieveNamePrep;
	
	
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
		_log = null;
		String insertSql = "INSERT INTO LaptopBorrowerInfo (student_id, first_name, model, hdd, memory, year) ";
		insertSql += "VALUES (?, ?, ?, ?, ?, ?);";
		String deleteSql = "DELETE FROM LaptopBorrowerInfo WHERE student_id = ? AND first_name = ?;";
		String retrieveIdSql = "SELECT * FROM LaptopBorrowerInfo WHERE student_id = ?;";
		String retrieveNameSql = "SELECT * FROM LaptopBorrowerInfo WHERE first_name = ?;";
		try {
			_insertPrep = _conn.prepareStatement(insertSql);
			_deletePrep = _conn.prepareStatement(deleteSql);
			_retrieveIdPrep = _conn.prepareStatement(retrieveIdSql);
			_retrieveNamePrep = _conn.prepareStatement(retrieveNameSql);
		} catch (SQLException e) {
			_log = logErr(e, ERR_PREPARE);
		}
	}
	
	/**
	 * Connects to database and prepares all statements.
	 */
	public void connect() {
		_log = null;
		try {
			Class.forName(JDBC_DRIVER);
			_conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
		} catch (ClassNotFoundException e) {
			_log = logErr(e, ERR_DRIVER);
		} catch (SQLException e) {
			_log = logErr(e, ERR_CONNECT);
		}
		// Prepare all statements
		if (_conn != null) {
			prepareAllStatements();	
		}
	}
	
	/**
	 * Disconnects from database.
	 */
	public void disconnect() {
		_log = null;
		try {
			if (_conn != null) {
				_conn.close();
			}
		} catch (SQLException e) {	
			_log = logErr(e, ERR_DISCONNECT);
		}
	}
	
	/**
	 * Inserts a new record into the database.
	 * @param record A record object.
	 */
	public void insertRecord (Record record) {
		_log = null;
		_message = null;
		int affectedRows = 0;
		try {
			_insertPrep.setString(1, record.getStudentId());
			_insertPrep.setString(2, record.getFirstName());
			_insertPrep.setString(3, record.getModel());
			
			// Assigns proper values to Database (NULL or current year)
			if (!record.getHdd().isEmpty()) {
				_insertPrep.setInt(4, Integer.parseInt(record.getHdd()));
			} else {
				_insertPrep.setNull(4, Types.NULL);
			}
			if (!record.getMemory().isEmpty()) {
				_insertPrep.setInt(5, Integer.parseInt(record.getMemory()));
			} else {
				_insertPrep.setNull(5, Types.NULL);
			}
			if (!record.getYear().isEmpty()) {
				_insertPrep.setInt(6, Integer.parseInt(record.getYear()));
			} else {
				DateFormat df = new SimpleDateFormat(DATE_FORMAT_YEAR);
				String date = df.format(new Date());
				_insertPrep.setInt(6, Integer.parseInt(date));
			}
			
			affectedRows = _insertPrep.executeUpdate();
			if (affectedRows != 0) {
				_message = SUC_INSERT;	
			}
		} catch (SQLException e) {
			_log = logErr(e, ERR_INSERT);
			if (e.getErrorCode() == CODE_DUPE_RECORD) {
				_message = ERR_DUPE_RECORD;
			}
		}
		
	}
	
	/**
	 * Deletes a single record from the database if user gets id and name correct.
	 * @param student_id from the controller
	 * @param first_name from the controller
	 */
	public void deleteRecord (String student_id, String first_name) {
		_log = null;
		_message = null;
		int affectedRows = 0;
		try {
			_deletePrep.setString(1, student_id);
			_deletePrep.setString(2, first_name);
			affectedRows = _deletePrep.executeUpdate();
			if (affectedRows != 0) {
				_message = SUC_DELETE;	
			} else {
				_message = SUC_NOT_FOUND;
			}
		} catch (SQLException e) {
			_log = logErr(e, ERR_DELETE);
		}
	}
	
	/**
	 * Retrieves a single record by student id (Primary Key)
	 * @param student_id from controller
	 * @return recordList to the controller
	 */
	public ArrayList<Record> retrieveById (String student_id) {
		_log = null;
		ResultSet result = null;
		ArrayList<Record> recordList = new ArrayList<>();
		try {
			_retrieveIdPrep.setString(1, student_id);
			result = _retrieveIdPrep.executeQuery();
			while (result.next()) {
				Record record = new Record (
						result.getString(ID),
						result.getString(NAME),
						result.getString(MODEL),
						Integer.toString(result.getInt(HDD)),
						Integer.toString(result.getInt(MEMORY)),
						Integer.toString(result.getInt(YEAR))
						);
				recordList.add(record);
			}
		} catch (SQLException e) {
			_log = logErr(e, ERR_RETR_ID);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					_log = logErr(e, ERR_CLOSE_RESULT);
				}
			}
		}
		return recordList;
	}
	
	/**
	 * 
	 * @param first_name from the controller
	 * @return recordList to the controller
	 */
	public ArrayList<Record> retrieveByName (String first_name) {
		_log = null;
		ResultSet result = null;
		ArrayList<Record> recordList = new ArrayList<>();
		try {
			_retrieveNamePrep.setString(1, first_name);
			result = _retrieveNamePrep.executeQuery();
			while (result.next()) {
				Record record = new Record (
						result.getString(ID),
						result.getString(NAME),
						result.getString(MODEL),
						Integer.toString(result.getInt(HDD)),
						Integer.toString(result.getInt(MEMORY)),
						Integer.toString(result.getInt(YEAR))
						);
				recordList.add(record);
			}
			
		} catch (SQLException e) {
			_log = logErr(e, ERR_RETR_NAME);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					_log = logErr(e, ERR_CLOSE_RESULT);
				}
			}
		}
		return recordList;
	}
	
	/**
	 * Assigns log message to object's attribute.
	 * @param e can be SQL or Class not found
	 * @param errMessage to log
	 * @return log message 
	 */
	private String logErr (Exception e, String errMessage) {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		String date = df.format(new Date());
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
		return _log;
	}
	
	/**
	 * Accesses the message attribute of the DataAccess object
	 * @return message to the controller
	 */
	public String getMessage() {
		return _message;
	}
	
	/**
	 * @param newMessage to set
	 */
	public void setMessage(String newMessage) {
		_message = newMessage;
	}
	
}
