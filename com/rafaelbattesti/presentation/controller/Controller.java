package com.rafaelbattesti.presentation.controller;

import java.util.ArrayList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * Main controller of the application view
 * @author rafaelbattesti
 *
 */
public class Controller implements Initializable {
	
	// Buttons
	@FXML private Button _addRecordBtn;
	@FXML private Button _addResetBtn;
	@FXML private Button _delRecordBtn;
	@FXML private Button _delResetBtn;
	@FXML private Button _searchNameBtn;
	@FXML private Button _searchIdBtn;
	
	// TextFields (input)
	@FXML private TextField _addStudentIdFld; 
	@FXML private TextField _addNameFld;
	@FXML private TextField _addLaptopModelFld;
	@FXML private TextField _addHddFld;
	@FXML private TextField _addMemoryFld;
	@FXML private TextField _addYearFld;
	@FXML private TextField _delStudentIdFld;
	@FXML private TextField _delNameFld;
	@FXML private TextField _searchNameFld;
	@FXML private TextField _searchStudentIdFld;
	
	// TextAreas (Status messages)
	@FXML private TextArea _addRecordStatusFld;
	@FXML private TextArea  _delRecordStatusFld;
	
	// Table to display results
	@FXML private TableView<Record> _tableView;
	@FXML private TableColumn<Record, String> _modelCol;
	@FXML private TableColumn<Record, String> _hddCol;
	@FXML private TableColumn<Record, String> _memoryCol;
	@FXML private TableColumn<Record, String> _yearCol;
	
	// Other data fields
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
		_addRecordStatusFld.setEditable(false);
		_delRecordStatusFld.setEditable(false);
		
		// Setup table columns to receive data
		_modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
		_hddCol.setCellValueFactory(new PropertyValueFactory<>("hdd"));
		_memoryCol.setCellValueFactory(new PropertyValueFactory<>("memory"));
		_yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
		
		// Resets the fields on the Add Record tab
		_addResetBtn.setOnAction(event -> {
			resetAddFields();
		});
		
		// Resets the fields on the Delete Record tab
		_delResetBtn.setOnAction(event -> {
			_delStudentIdFld.clear();
			_delNameFld.clear();
		});
		
		// Add record
		_addRecordBtn.setOnAction(event -> {
			
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
		_delRecordBtn.setOnAction(event -> {
			
			_db.deleteRecord(_delStudentIdFld.getText(), _delNameFld.getText());
			
			_statusMessageDel = statusMessageBuilder(_statusMessageDel);
			printMessage(_statusMessageDel, _delRecordStatusFld);
			resetDelFields();
		});
		
		// Select records by name
		_searchNameBtn.setOnAction(event -> {
			
			ArrayList<Record> recordList = _db.retrieveByName(_searchNameFld.getText());
			_tableView.getItems().setAll(recordList);
			_searchNameFld.clear();
			
			
		});
		
		// Select record by ID
		_searchIdBtn.setOnAction(event -> {
			
			ArrayList<Record> recordList = _db.retrieveById(_searchStudentIdFld.getText());
			_tableView.getItems().setAll(recordList);
			_searchStudentIdFld.clear();
			
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
