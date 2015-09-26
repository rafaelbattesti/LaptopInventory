package com.rafaelbattesti.business;

/**
 * This class models a lending record.
 * @author rafaelbattesti - 991382266
 *
 */
public class Record {
	
	// Attributes
	private String studentId, firstName, model;
	private int hdd, memory, year;
	
	// Constructor
	public Record (String newStudentId, String newFirstName, String newModel) {
		studentId = newStudentId;
		firstName = newFirstName;
		model = newModel;
	}
	
	// Getters
	public String getStudentId() { return studentId; }
	public String getFirstName() { return firstName; }
	public String getModel() { return model; }
	public int getHdd() { return hdd; }
	public int getMemory() { return memory; }
	public int getYear() { return year; }
	
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
	public void setHdd(int newHdd) {
		hdd = newHdd;
	}
	public void setMemory(int newMemory) {
		memory = newMemory;
	}
	public void setYear(int newYear) {
		year = newYear;
	}
}
