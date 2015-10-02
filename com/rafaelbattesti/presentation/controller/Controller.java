package com.rafaelbattesti.presentation.controller;

import com.rafaelbattesti.business.Record;
import com.rafaelbattesti.dataaccess.DataAccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * Main controller of the application view.
 * @author rafaelbattesti - 991382266
 *
 */
public class Controller implements Initializable {
	
	/**
	 * Button to add a record.
	 */
	@FXML private Button _addRecordBtn;
	
	/**
	 * Button to reset all fields and error label on add record tab.
	 */
	@FXML private Button _addResetBtn;
	
	/**
	 * Button to delete a record.
	 */
	@FXML private Button _delRecordBtn;
	
	/**
	 * Button to reset all fields on delete record tab.
	 */
	@FXML private Button _delResetBtn;
	
	/**
	 * Button to search record by name.
	 */
	@FXML private Button _searchNameBtn;
	
	/**
	 * Button to search record by ID.
	 */
	@FXML private Button _searchIdBtn;
	
	/**
	 * Add record: Field to input student ID.
	 */
	@FXML private TextField _addStudentIdFld;
	
	/**
	 * Add record: Fields to input first name.
	 */
	@FXML private TextField _addNameFld;
	
	/**
	 * Add record: Field to input laptop model.
	 */
	@FXML private TextField _addLaptopModelFld;
	
	/**
	 * Add record: Field to input laptop HDD.
	 */
	@FXML private TextField _addHddFld;
	
	/**
	 * Add record: Field to input laptop memory.
	 */
	@FXML private TextField _addMemoryFld;
	
	/**
	 * Add record: Field to input laptop year.
	 */
	@FXML private TextField _addYearFld;
	
	/**
	 * Delete record: Field to input student ID.
	 */
	@FXML private TextField _delStudentIdFld;
	
	/**
	 * Delete record: Field to input first name.
	 */
	@FXML private TextField _delNameFld;
	
	/**
	 * Search record: Field to input first name.
	 */
	@FXML private TextField _searchNameFld;
	
	/**
	 * Search record: Field to input student ID.
	 */
	@FXML private TextField _searchStudentIdFld;
	
	/**
	 * Add record: Log console.
	 */
	@FXML private TextArea _addRecordStatusFld;
	
	/**
	 * Delete record: Log console.
	 */
	@FXML private TextArea  _delRecordStatusFld;
	
	/**
	 * Add record: Label to display input error.
	 */
	@FXML private Label _errorLbl;
	
	/**
	 * Search record: Label to show the search parameter used.
	 */
	@FXML private Label _tableTitle;
	
	/**
	 * Search record: Table to display the results.
	 */
	@FXML private TableView<Record> _tableView;
	
	/**
	 * Search record: Column to display the laptop model results.
	 */
	@FXML private TableColumn<Record, String> _modelCol;
	
	/**
	 * Search record: Column to display the HDD results.
	 */
	@FXML private TableColumn<Record, String> _hddCol;
	
	/**
	 * Search record: Column to display the memory results.
	 */
	@FXML private TableColumn<Record, String> _memoryCol;
	
	/**
	 * Search record: Column to display the year results.
	 */
	@FXML private TableColumn<Record, String> _yearCol;
	
	/**
	 * Database object.
	 */
	private DataAccess _db;
	
	/**
	 * Add record: message log string.
	 */
	private String _statusMessageAdd = "";
	
	/**
	 * Delete record: message log string.
	 */
	private String _statusMessageDel = "";
	
	/**
	 * Format of the date for the console log.
	 */
	private static final String DATE_FORMAT   = "yyyy/MM/dd HH:mm:ss";
	
	/**
	 * RegEx for ID validation
	 */
	private static final String ID_REGEX      = "^[0-9]{9}$";
	
	/**
	 * RegEx for first name validation
	 */
	private static final String NAME_REGEX    = "^[A-Za-z]{1,20}$";
	
	/**
	 * RegEx for model validation
	 */
	private static final String MODEL_REGEX   = "^[A-Za-z0-9-]{1,20}$";
	
	/**
	 * RegEx for HDD validation
	 */
	private static final String HDD_REGEX     = "^[0-9]{0,9}$"; //INT limit in MySQL is 10 digits long. 9 guarantees integrity.
	
	/**
	 * RegEx for memory validation
	 */
	private static final String MEMORY_REGEX  = "^[0-9]{0,3}";
	
	/**
	 * RegEx for year validation
	 */
	private static final String YEAR_REGEX    = "^(20[0-1][0-5]){0,1}$";
	
	/**
	 * Input error message.
	 */
	private static final String INVALID_INPUT = "Invalid input, please try again.";
	
	/**
	 * Runs the controller logic and holds the lambda expressions for ActionEvents.
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Initialize the database
		_db = new DataAccess();
		
		// Make status areas read only
		_addRecordStatusFld.setEditable(false);
		_delRecordStatusFld.setEditable(false);
		
		// Setup table columns to display data retrieved from database
		_modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
		_hddCol.setCellValueFactory(new PropertyValueFactory<>("hdd"));
		_memoryCol.setCellValueFactory(new PropertyValueFactory<>("memory"));
		_yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
		
		// Maps RegEx to TextFields
		HashMap<String, TextField> form = new HashMap<>();
		form.put(ID_REGEX, _addStudentIdFld);
		form.put(NAME_REGEX, _addNameFld);
		form.put(MODEL_REGEX, _addLaptopModelFld);
		form.put(HDD_REGEX, _addHddFld);
		form.put(MEMORY_REGEX, _addMemoryFld);
		form.put(YEAR_REGEX, _addYearFld);
		
		// Add record
		_addRecordBtn.setOnAction(event -> {
			addRecord(form);
		});
		
		// Resets the fields on the Add Record tab
		_addResetBtn.setOnAction(event -> {
			resetAddFields(form);
		});
		
		// Delete record
		_delRecordBtn.setOnAction(event -> {
			deleteRecord();
		});
		
		// Resets the fields on the Delete Record tab
		_delResetBtn.setOnAction(event -> {
			_delStudentIdFld.clear();
			_delNameFld.clear();
		});
		
		// Select records by name
		_searchNameBtn.setOnAction(event -> {
			searchRecords(_searchNameFld);
		});
		
		// Select record by ID
		_searchIdBtn.setOnAction(event -> {
			searchRecords(_searchStudentIdFld);
		});
	}
	
	/**
	 * Searches records by field and prints results in the table.
	 * @param field to use in the search
	 */
	private void searchRecords(TextField field) {
		ArrayList<Record> recordList = _db.retrieveByName(field.getText());
		_tableView.getItems().setAll(recordList);
		_tableTitle.setText(field.getText());
		field.clear();
	}

	/**
	 * Deletes a record and prints messages (success or not found).
	 */
	private void deleteRecord() {
		_db.deleteRecord(_delStudentIdFld.getText(), _delNameFld.getText());
		_statusMessageDel = statusMessageBuilder(_statusMessageDel, _db.getMessage());
		printMessage(_statusMessageDel, _delRecordStatusFld);
		resetDelFields();	
	}

	/**
	 * Adds a record to the database and prints messages (invalid input or success).
	 * @param form HashMap of TextFields
	 */
	private void addRecord (HashMap<String, TextField> form) {
		if (validateInput(form)) {
			Record record = new Record(_addStudentIdFld.getText(), _addNameFld.getText(),
					_addLaptopModelFld.getText(), _addHddFld.getText(), _addMemoryFld.getText(), 
					_addYearFld.getText());
			_db.insertRecord(record);
			_statusMessageAdd = statusMessageBuilder(_statusMessageAdd, _db.getMessage());
			printMessage(_statusMessageAdd, _addRecordStatusFld);
			resetAddFields(form);
		} else {
			_errorLbl.setText(INVALID_INPUT);
		}
	}
	
	/**
	 * Resets all fields in insert record
	 * @param form HashMap of TextFields
	 */
	private void resetAddFields (HashMap<String, TextField> form) {
		for (TextField field : form.values()) {
			field.clear();
			field.getStyleClass().remove("error");
		}
		_errorLbl.setText("");
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
	 * @param messageLog status log related to the tab
	 * @param commingMessage message to be attached to the messageLog
	 * @return messageLog formed with date and time
	 */
	private String statusMessageBuilder (String messageLog, String commingMessage) {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		String date = df.format(new Date());
		return messageLog += "[" + date + "]\n>> " + commingMessage + "\n";
	}
	
	/**
	 * Validates the input at the insert record tab and changes TextField classes for user visualization.
	 * @param form HashMap of TextFields
	 * @return true if all fields are valid
	 */
	public boolean validateInput(HashMap<String, TextField> form) {
		boolean isValid = true;
		for (String key : form.keySet()) {
			if (!form.get(key).getText().matches(key)) {
				form.get(key).getStyleClass().add("error");
				form.get(key).setText("");
				isValid = isValid && false;
			}
		}
		return isValid;
	}
}
