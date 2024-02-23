package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainMenuController extends LoginModel implements Initializable{

	@FXML
	Button forgotPasswordButton, loginButton, signupButton;
	@FXML
	PasswordField passwordField;
	@FXML
	TextField usernameField;
	@FXML
	AnchorPane loginPane;
	@FXML
	private Label connectionStatus;
	

	/*private Stage stage;
	private Scene scene;
	private Parent root;*/
	
	
	//UI actions
	
	public void signup(ActionEvent event) {
		if(usernameField.getText().equals("") || passwordField.getText().equals("") ) {
			errorAlert("Login error","Details are not entered!","Please ensure you have filled in something for both username and password fields!");
		}
		else {
			signUp(event, usernameField.getText(), passwordField.getText());	
		}
		
	}
	
	public void forgotPassword(ActionEvent event) throws IOException{
		
		/*String username = usernameField.getText();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("scene2.fxml"));
		root = loader.load();
		
		passwordHelpController passwordHelpController = loader.getController();
		passwordHelpController.displayName(username);*/
				
		/*root = FXMLLoader.load(getClass().getResource("MainProgram.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();*/
		
		changeScene(event,"passwordhelp.fxml");
	}
	
	public void login(ActionEvent event) {
		if(usernameField.getText().equals("") || passwordField.getText().equals("") ) {
			errorAlert("Login error","Details are not entered!","Please ensure you have filled in something for both username and password fields!");
		}
		else {
			login(event, usernameField.getText(), passwordField.getText());		
		}
	}
	
	@Override

	public void initialize(URL url, ResourceBundle resourceBundle) {
	}

}
