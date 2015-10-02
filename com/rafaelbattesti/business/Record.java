package com.rafaelbattesti.business;

/**
 * This class models a record for laptop borrower.
 * @author rafaelbattesti - 991382266
 *
 */
public class Record {
	
	/**
	 * Holds the record primary key ID.
	 */
	private String _studentId;
	
	/**
	 * Holds the student first name.
	 */
	private String _firstName;
	
	/**
	 * Holds the laptop model.
	 */
	private String _model;
	
	/**
	 * Holds the laptop hdd size in GB.
	 */
	private String _hdd;
	
	/**
	 * Holds the laptop memory in GB.
	 */
	private String _memory;
	
	/**
	 * Holds the laptop year of manufacture.
	 */
	private String _year;
	
	
	/**
	 * 
	 * @param newStudentId primary key
	 * @param newFirstName student name
	 * @param newModel laptop model
	 * @param newHdd laptop hdd in GB
	 * @param newMemory laptop memory in GB
	 * @param newYear laptop year of manufacture
	 */
	public Record (String newStudentId, String newFirstName, String newModel, String newHdd, String newMemory, String newYear) {
		_studentId = newStudentId;
		_firstName = newFirstName;
		_model     = newModel;
		_hdd       = newHdd;
		_memory    = newMemory;
		_year      = newYear;
	}
	
	/**
	 * Assessor to student id.
	 * @return student id
	 */
	public String getStudentId() { return _studentId; }
	
	/**
	 * Assessor to first name
	 * @return first name
	 */
	public String getFirstName() { return _firstName; }
	
	/**
	 * Assessor to model
	 * @return model
	 */
	public String getModel() { return _model; }
	
	/**
	 * Assessor to hdd
	 * @return hdd
	 */
	public String getHdd() { return _hdd; }
	
	/**
	 * Assessor to memory
	 * @return memory
	 */
	public String getMemory() { return _memory; }
	
	/**
	 * Assessor to year
	 * @return year
	 */
	public String getYear() { return _year; }
}
