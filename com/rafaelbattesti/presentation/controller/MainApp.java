package com.rafaelbattesti.presentation.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main Application.
 * @author rafaelbattesti - 991382266
 *
 */
public class MainApp extends Application {

	/**
	 * Title of the application (window).
	 */
	private static final String TITLE = "Laptop Inventory Information System";
	
	/**
	 * Path to the FXML file.
	 */
	private static final String FXML_PATH = "../view/AppView.fxml";
	
	/**
	 * Path to the CSS file.
	 */
	private static final String CSS_PATH = "../view/style.css";
	
	/**
	 * Primary stage.
	 */
	private Stage _primaryStage;
	
	
	/**
	 * Start method initializes the stage, loads FXML and CSS files into the MainApp and shows the stage.
	 */
	@Override
	public void start(Stage stage) throws IOException {
		
		//Initializes the stage
		_primaryStage = stage;
		
		//Loads FXML and sets file location and loads onto the root node.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(FXML_PATH));
        Parent root = loader.load();
        
        //Creates a scene with the root node
        Scene scene = new Scene(root, 800, 600);
        
        //Set CSS to the main fxml file
        String css = MainApp.class.getResource(CSS_PATH).toExternalForm();
        scene.getStylesheets().add(css);

        //Sets the stage title, sets the scene on stage and shows
        _primaryStage.setTitle(TITLE);
        _primaryStage.setScene(scene);
        _primaryStage.show();
	}

	/**
	 * Main method. Launches JavaFx application.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
