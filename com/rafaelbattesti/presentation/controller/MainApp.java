package com.rafaelbattesti.presentation.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

	private static final String TITLE = "Laptop Inventory Information System";
	private Stage _primaryStage;
	
	@Override
	public void start(Stage stage) throws IOException {
		
		_primaryStage = stage;
		
		//Starts a new loader for FXML
        FXMLLoader loader = new FXMLLoader();
        
        //Set the loader location
        loader.setLocation(MainApp.class.getResource("../view/AppView.fxml"));
        
        //Loads the FXML file
        Parent root = loader.load();
        
        //Creates a scene with the root node
        Scene scene = new Scene(root, 800, 600);
        
        //Set CSS to the main fxml file
        String css = MainApp.class.getResource("../view/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        //Sets the stage title
        _primaryStage.setTitle(TITLE);
        
        //Sets the scene on the stage
        _primaryStage.setScene(scene);
        
        //Shows the stage
        _primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
