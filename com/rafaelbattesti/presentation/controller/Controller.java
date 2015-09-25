package com.rafaelbattesti.presentation.controller;

import java.util.ResourceBundle;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class Controller implements Initializable {
	
	@FXML
	private Button _addRecordBtn, _addResetBtn, _delRecordBtn, _delResetBtn, _searchNameBtn, _searchIdBtn;
	
	@FXML
	private TextField _addStudentIdFld, _addNameFld, _addLaptopModelFld, _addHddFld, _addMemoryFld, _addYearFld, 
	_delStudentIdFld, _delNameFld, _searchNameFld, _searchStudentIdFld;
	
	@FXML
	private TextArea _addRecordStatusFld, _delRecordStatusFld;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Resets the fields on the Add Record tab
		_addResetBtn.setOnAction((event) -> {
			_addStudentIdFld.clear();
			_addNameFld.clear();
			_addLaptopModelFld.clear();
			_addHddFld.clear();
			_addMemoryFld.clear();
			_addYearFld.clear();
		});
		
		//Resets the fields on the Delete Record tab
		_delResetBtn.setOnAction((event) -> {
			_delStudentIdFld.clear();
			_delNameFld.clear();
		});
		
		
		
	}
	
	public void addRecord(ActionEvent event) {
		
	}
	
	public void deleteRecord(ActionEvent event) {
		
	}
	
	public void searchByName(ActionEvent event) {
		
	}
	
	public void searchById(ActionEvent event) {
		
	}
	
	public void validateInput(ActionEvent event) {
		
	}

}
