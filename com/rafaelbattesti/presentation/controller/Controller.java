package com.rafaelbattesti.presentation.controller;

import java.util.Date;
import java.util.ResourceBundle;

import com.rafaelbattesti.business.Record;
import com.rafaelbattesti.dataaccess.DataAccess;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class Controller implements Initializable {
	
	@FXML
	private Button _addRecordBtn, _addResetBtn, _delRecordBtn, _delResetBtn, _searchNameBtn, _searchIdBtn;
	@FXML
	private TextField _addStudentIdFld, _addNameFld, _addLaptopModelFld, _addHddFld, _addMemoryFld, _addYearFld, 
	_delStudentIdFld, _delNameFld, _searchNameFld, _searchStudentIdFld;
	@FXML
	private TextArea _addRecordStatusFld, _delRecordStatusFld;
	
	private DataAccess _db;
	private String _statusMessageAdd = "";
	private String _statusMessageDel = "";
	
	// Constants
	private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Initialize the database
		_db = new DataAccess();
		
		// Make status areas read only
		_addRecordStatusFld.setDisable(true);
		_delRecordStatusFld.setDisable(true);
		
		// Resets the fields on the Add Record tab
		_addResetBtn.setOnAction((event) -> {
			resetAddFields();
		});
		
		// Resets the fields on the Delete Record tab
		_delResetBtn.setOnAction((event) -> {
			_delStudentIdFld.clear();
			_delNameFld.clear();
		});
		
		// Add record
		_addRecordBtn.setOnAction((event) -> {
			
			String student_id = _addStudentIdFld.getText();
			String first_name = _addNameFld.getText();
			String model      = _addLaptopModelFld.getText();
			String hdd        = _addHddFld.getText();
			String memory     = _addMemoryFld.getText();
			String year       = _addYearFld.getText();
			
			Record record = new Record(student_id, first_name, model);
			
			record.setHdd(hdd);
			record.setMemory(memory);
			record.setYear(year);
			
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			String date = df.format(new Date());
			
			_db.insertRecord(record);
			_statusMessageAdd += "[" + date + "]\n>> " + _db.getMessage() + "\n";
			printMessage(_statusMessageAdd, _addRecordStatusFld);
			resetAddFields();
		});
		
		// Delete record
		_delRecordBtn.setOnAction((event) -> {
			
			String student_id = _delStudentIdFld.getText();
			String first_name = _delNameFld.getText();
			
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			String date = df.format(new Date());
			
			_db.deleteRecord(student_id, first_name);
			_statusMessageAdd += "[" + date + "]\n>> " + _db.getMessage() + "\n";
			printMessage(_statusMessageAdd, _delRecordStatusFld);
			resetDelFields();
			
		});
	}
	
	/**
	 * Resets all fields in insert record
	 */
	public void resetAddFields () {
		_addStudentIdFld.clear();
		_addNameFld.clear();
		_addLaptopModelFld.clear();
		_addHddFld.clear();
		_addMemoryFld.clear();
		_addYearFld.clear();
	}
	
	public void resetDelFields () {
		_delStudentIdFld.clear();
		_delNameFld.clear();
	}
	
	/**
	 * Prints a message at the status field
	 * @param message
	 */
	public void printMessage (String message, TextArea statusLabel) {
		if (message != null) {
			statusLabel.setText(message);
		}
	}
	
	/**
	 * Validates the input at the 
	 * @param regex
	 * @param field
	 * @param message
	 * @return
	 */
	public String validateInput(String regex, TextField field, String message) {
		//TODO: field validation.
		if (!field.getText().matches(regex)) {
			_addRecordStatusFld.setText("Not a valid input.");
		}
		return null;
	}
}
