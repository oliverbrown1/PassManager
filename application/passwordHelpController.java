package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class passwordHelpController {
	
	@FXML
	private Button mainMenuButton, resetAccountButton, exitButton;

	
	private Stage stage;
	private Scene scene;
	private Parent root;
		
	
	public void goBack(ActionEvent event) throws IOException{
		
		/*String username = usernameField.getText();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("scene2.fxml"));
		root = loader.load();
		
		passwordHelpController passwordHelpController = loader.getController();
		passwordHelpController.displayName(username);*/
		utilityMethods.changeScene(event, "MainMenu.fxml");
	}
	
	public void resetAccount(ActionEvent event) throws IOException{
		
		/*String username = usernameField.getText();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("scene2.fxml"));
		root = loader.load();
		
		passwordHelpController passwordHelpController = loader.getController();
		passwordHelpController.displayName(username);*/
		utilityMethods.changeScene(event,"ResetAccount.fxml");
				
	}

}
