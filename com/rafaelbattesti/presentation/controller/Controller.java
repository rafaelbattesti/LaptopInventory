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
			
			Record record = new Record(
					_addStudentIdFld.getText(), 
					_addNameFld.getText(),
					_addLaptopModelFld.getText());
			
			record.setHdd(_addHddFld.getText());
			record.setMemory(_addMemoryFld.getText());
			record.setYear(_addYearFld.getText());
			
			_db.insertRecord(record);
			
			_statusMessageAdd = statusMessageBuilder(_statusMessageAdd);
			printMessage(_statusMessageAdd, _addRecordStatusFld);
			resetAddFields();
		});
		
		// Delete record
		_delRecordBtn.setOnAction((event) -> {
			
			_db.deleteRecord(_delStudentIdFld.getText(), _delNameFld.getText());
			
			_statusMessageDel = statusMessageBuilder(_statusMessageDel);
			printMessage(_statusMessageDel, _delRecordStatusFld);
			resetDelFields();
		});
	}
	
	/**
	 * Resets all fields in insert record
	 */
	private void resetAddFields () {
		_addStudentIdFld.clear();
		_addNameFld.clear();
		_addLaptopModelFld.clear();
		_addHddFld.clear();
		_addMemoryFld.clear();
		_addYearFld.clear();
	}
	
	/**
	 * Resets all fields in delete record
	 */
	private void resetDelFields () {
		_delStudentIdFld.clear();
		_delNameFld.clear();
	}
	
	/**
	 * Prints a message at the status field
	 * @param message message to print
	 * @param statusLabel label to send the message
	 */
	private void printMessage (String message, TextArea statusLabel) {
		if (message != null) {
			statusLabel.setText(message);
		}
	}
	
	/**
	 * Builds a status message to the specific tab.
	 * @param statusMessage message related to the tab
	 * @param statusLabel label where the message is printed
	 * @return 
	 */
	private String statusMessageBuilder (String statusMessage) {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		String date = df.format(new Date());
		return statusMessage += "[" + date + "]\n>> " + _db.getMessage() + "\n";
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
