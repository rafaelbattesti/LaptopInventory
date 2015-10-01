package com.rafaelbattesti.business;

/**
 * This class models a lending record.
 * @author rafaelbattesti - 991382266
 *
 */
public class Record {
	
	// Attributes
	private String studentId, firstName, model, hdd, memory, year;
	
	// Constructor
	public Record (String newStudentId, String newFirstName, String newModel, String newHdd, String newMemory, String newYear) {
		studentId = newStudentId;
		firstName = newFirstName;
		model     = newModel;
		hdd       = newHdd;
		memory    = newMemory;
		year      = newYear;
	}
	
	// Getters
	public String getStudentId() { return studentId; }
	public String getFirstName() { return firstName; }
	public String getModel() { return model; }
	public String getHdd() { return hdd; }
	public String getMemory() { return memory; }
	public String getYear() { return year; }
	
	// Setters
	public void setStudentId(String newStudentId) {
		studentId = newStudentId;
	}
	public void setFirstName(String newFirstName) {
		firstName = newFirstName;
	}
	public void setModel(String newModel) {
		model = newModel;
	}
	public void setHdd(String newHdd) {
		hdd = newHdd;
	}
	public void setMemory(String newMemory) {
		memory = newMemory;
	}
	public void setYear(String newYear) {
		year = newYear;
	}
	
}
