package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ResetAccountController {
	
	@FXML 
	private Button resetAccountButton, cancelButton;
	@FXML
	private TextField usernameDeleteField;
	@FXML
	private CheckBox check;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	public void cancelAction(ActionEvent event) throws IOException{
		
		/*String username = usernameField.getText();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("scene2.fxml"));
		root = loader.load();
		
		passwordHelpController passwordHelpController = loader.getController();
		passwordHelpController.displayName(username);*/
		
		utilityMethods.changeScene(event, "passwordhelp.fxml");
	}
	
	public void resetAccount(ActionEvent event) throws IOException{
		if(usernameDeleteField.getText().equals("")) {
			utilityMethods.errorAlert("Input error", "Username field is empty", "Please enter username in the username field");
		}
		else if(!check.isSelected()){
		 	utilityMethods.errorAlert("Input error", "Check box not ticked!", "Please tick checkbox to continue with action.");
		}
		else {
			LoginModel.deleteAccount(event, usernameDeleteField.getText());
		}
	}
}


