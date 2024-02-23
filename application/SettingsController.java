package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class SettingsController implements Initializable{
	
	public void userData(String username) {
		info.setText(username);
		info.setVisible(false);
		System.out.println(username);
		System.out.println(info.getText());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	Label info;
	@FXML
	private TextField currentUsernameField, newUsernameField, currentPasswordField, resetUsernameField;
	@FXML
	private PasswordField newPasswordField, confirmPasswordField, resetPasswordField;
	@FXML
	private Button setUsername, setPassword, resetAccount;
	
	public void changeUsername(ActionEvent event) {
		if(currentUsernameField.getText().equals("") || newUsernameField.getText().equals("")) {
			utilityMethods.errorAlert("Program Error","One or more fields are empty!","Please ensure details are entered in all fields to continue then press button.");
		}
		else if(newUsernameField.getText().length()<3) {
			utilityMethods.errorAlert("Program Error","Username cannot be changed","New username must be at least 3 characters long!.");
		}
		else {
			SettingsModel.changeUsername(event, currentUsernameField.getText(), newUsernameField.getText());
		}
	}
	
	public void changePassword(ActionEvent event) {
		String nPF = newPasswordField.getText();
		String cPF = confirmPasswordField.getText();
		String curPF = currentPasswordField.getText();
		if(curPF.equals("") || newPasswordField.getText().equals("") || confirmPasswordField.getText().equals("")) {
			utilityMethods.errorAlert("Program Error","One or more fields are empty!","Please ensure details are entered in all fields to continue then press button.");
		}
		else if(!(nPF.equals(cPF))){
			utilityMethods.errorAlert("Program Error","Password is not confirmed!","Please rewrite the new password into the 'confirm password' field and make sure both passwords match");
		}
		else if(curPF.equals(nPF)) {
			utilityMethods.errorAlert("Program Error","Password cannot be changed","Cannot change current password into new password because they are the same! Please ensure the new password is different from the current password.");
		}
		else if(nPF.length()<3) {
			utilityMethods.errorAlert("Program Error","Password cannot be changed","New password must be at least 3 characters long!");
		}
		else {
			SettingsModel.changePassword(event, curPF, nPF);
		}
	}
	
	public void resetAccount(ActionEvent event) {
		if(resetUsernameField.getText().equals("") || resetPasswordField.getText().equals("")) {
			utilityMethods.errorAlert("Input error","Username or password field empty!","Ensure both username and password field are not empty and filled in with user login details");
		}
		else {
			SettingsModel.resetAccount(resetUsernameField.getText(), resetPasswordField.getText());
		}
	}
	
	public void logout(ActionEvent event) {
		utilityMethods.changeScene(event, "MainMenu.fxml");
	}
	
	public void goback(ActionEvent event) {
		utilityMethods.changeSceneLogin(event, "MainProgram.fxml", info.getText());
	}
	
	
}
