package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class utilityMethods {
	
	//Changes to new scene linked with controller + model
	public static void changeSceneLogin(ActionEvent event, String fxmlFile, String username) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(utilityMethods.class.getResource(fxmlFile));
			root = loader.load();
			MainProgramController MainProgramController = loader.getController();
			MainProgramController.userData(username);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	public static void changeSceneSettings(ActionEvent event, String fxmlFile, String username) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(utilityMethods.class.getResource(fxmlFile));
			root = loader.load();
			SettingsController SettingsController = loader.getController();
			SettingsController.userData(username);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	public static void changeScene(ActionEvent event, String fxmlFile) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(utilityMethods.class.getResource(fxmlFile));
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	public static void errorAlert(String titleT,String headerT, String contentT) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setResizable(false);
		alert.setTitle(titleT);
		alert.setHeaderText(headerT);
		alert.setContentText(contentT);
		alert.show();
	}
	
	public static void informationAlert(String titleT, String headerT, String contentT) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setResizable(false);
		alert.setTitle(titleT);
		alert.setHeaderText(headerT);
		alert.setContentText(contentT);
		alert.show();
	}
	
	public static Alert confirmationAlert(String titleT, String headerT, String contentT) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setResizable(false);
		alert.setTitle(titleT);
		alert.setHeaderText(headerT);
		alert.setContentText(contentT);
		return alert;
	}

}
