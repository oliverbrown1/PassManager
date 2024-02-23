package application;

import java.io.IOException;
import java.lang.Math;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainProgramController extends MainProgramModel implements Initializable{
	
	@FXML
	TextField fileNameField, usernameField, websiteField, emailField, passwordFieldVISIBLE, searchField;
	@FXML
	PasswordField passwordField;
	@FXML
	Button settingsButton, addButton, deleteButton, doneButton, viewPasswordButton, accessButton, suggestPassword;
	@FXML
	Label info;
	@FXML
	TextArea notesArea;
	@FXML
	AnchorPane userDetails;
	@FXML
	ComboBox<String> comboIcon;
	@FXML
	ListView<String> userItems;
	
	ArrayList<String> files = new ArrayList<>();
	private Boolean editMode = false;
	File file = new File();
	String filename;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	// 
	public void userData(String username) {  
		info.setText(username);
		info.setVisible(false);
		System.out.println(username);
		System.out.println(info.getText());
	}
	
	public void appendObject(){
		file.setFile(fileNameField.getText());
		file.setUser(usernameField.getText());
		file.setPass(passwordField.getText());
		file.setWeb(websiteField.getText());
		file.setEmail(emailField.getText());
		file.setIcon(comboIcon.getValue());
		file.setNotes(notesArea.getText());
	}
	
	public void addUserDetails(ArrayList<String> fileContents, String filename ){
		fileNameField.setText(file.getFile());
		usernameField.setText(fileContents.get(0));
		passwordField.setText(fileContents.get(1));
		passwordFieldVISIBLE.setText(fileContents.get(1));
		websiteField.setText(fileContents.get(2));
		emailField.setText(fileContents.get(3));
		comboIcon.setValue(fileContents.get(4));
		notesArea.setText(fileContents.get(5));
	}
	
	public void clearUserDetails() {
		fileNameField.clear();
		usernameField.clear();
		websiteField.clear();
		emailField.clear();
		passwordField.clear();
		passwordFieldVISIBLE.clear();
		notesArea.clear();
	}
	
	public void goSettings(ActionEvent e) throws IOException {
		changeSceneSettings(e,"Settings.fxml", info.getText());
	}
	
	public void addDetails(ActionEvent e )throws IOException{
		editMode = false;
		clearUserDetails();
		userDetails.setVisible(true);
	}
	public void finishDetails(ActionEvent e)throws IOException{

		int id = LoginModel.getid(info.getText());
		System.out.println(id);
		if(editMode) {
			appendObject();
			if (comboIcon.getValue() == null) {		
			}
			else {
				file.setIcon(comboIcon.getValue().toString());
			}
			if(passwordField.isVisible()) {
				file.setPass(passwordField.getText());
			}
			else {
				file.setPass(passwordFieldVISIBLE.getText());
			}
			if(file.getPass().equals("") || file.getFile().equals("")) {
				errorAlert("Input error", "Password or fileName field is empty!", "Ensure that the password field and fileName is filled in. No other text fields are required.");
			}
			else {
				editItem(filename, file.getFile(), file.getUser(), file.getPass(), file.getWeb() , file.getEmail(), file.getIcon(), file.getNotes());
				informationAlert("DB info","Data edit successful" , "Changes have been made to database.");
				userDetails.setVisible(false);
			}
		}
		else {
			appendObject();
			if (comboIcon.getValue() == null) {		
			}
			else {
				file.setIcon(comboIcon.getValue().toString());
			}
			if(passwordField.isVisible()) {
				file.setPass(passwordField.getText());
			}
			else { 
				file.setPass(passwordFieldVISIBLE.getText());
			}
			if(file.getPass().equals("") || file.getFile().equals("")) {
				errorAlert("Input error", "Password or fileName field is empty!", "Ensure that the password field and fileName is filled in. No other text fields are required.");
			}
			else {
				Boolean checkFileNameAvailable = checkFilename(file.getFile());
				if(checkFileNameAvailable) {
					Boolean checkFileAdded = addItem(id, file.getFile(), file.getUser(), file.getPass(), file.getWeb(), file.getEmail(), file.getIcon() , file.getNotes());
					if(checkFileAdded) {
						informationAlert("DB info","Data successfully added to database" , "Data successfully added to database");
						userDetails.setVisible(false);
					}
					else {
						errorAlert("DB Error","Item not added","Please try again later");
					}
				}
				else {
					errorAlert("Input error","Item not added","Filename already exists in Database. Filename must be unique.");
				}
			}
		}		
	}
	
	public void viewPassword(ActionEvent e)throws IOException{
		if(passwordField.isVisible()){
			passwordFieldVISIBLE.setText(passwordField.getText());
			passwordField.setVisible(false);
			passwordFieldVISIBLE.setVisible(true);
		}
		else {
			passwordField.setText(passwordFieldVISIBLE.getText());
			passwordFieldVISIBLE.setVisible(false);
			passwordField.setVisible(true);
		}
	}
	
	public void generatePassword(ActionEvent e)throws IOException{
		String generatedPassword = generatePassword();
		passwordField.setText(generatedPassword);
		passwordFieldVISIBLE.setText(generatedPassword);
	}
	
	public void getList(MouseEvent e) throws IOException{
		userItems.getItems().clear();
		int id = LoginModel.getid(info.getText());
		files = MainProgramModel.getFiles(id);
		userItems.getItems().addAll(files);
	}
	
	private List<String> searchList(String searchInput, List<String> files){
		List<String> searchWords = Arrays.asList(searchInput.trim().split(""));
		
		return files.stream().filter(input ->{
			return searchWords.stream().allMatch(word ->
			      input.toLowerCase().contains(word.toLowerCase()));
		}).collect(Collectors.toList());
	}
	
	public void search(KeyEvent event) {
		userItems.getItems().clear();
		userItems.getItems().addAll(searchList(searchField.getText(), files));

	}
	
	//When item is clicked in ListView, the program will display the information linked to this item from the database
	public void itemSearched(MouseEvent e) throws IOException{
		clearUserDetails();
		file.setFile(userItems.getSelectionModel().getSelectedItem());
		//If nothing is selected, the program will display an error
		if(file.getFile() == null) {
			utilityMethods.errorAlert("Input error", "No item selected.","Please select an item from the list view. "
					+ "If you have no items, please add a new login/item using the plus button." );
		}
		/*If an existing item is selected, an inherited method from MainProgramModel.java class is called to obtain the 
		 * requested item contents from the database which is stored in an ArrayList. This is then output to the 
		 * program UI using a local method so the client can access item information.
		 */
		
		else {
			ArrayList<String> fileContents = getFields(file.getFile());
			addUserDetails(fileContents, file.getFile());
			filename = file.getFile();
			editMode = true;
			userDetails.setVisible(true);
		}
		
	}
	
	public void deleteItem(ActionEvent e) throws IOException{
		Alert alert = confirmationAlert("DB Info", "Are you sure you want to delete this item?", "This will be permanently deleted. Please press cancel button to go back.");
		Optional<ButtonType> result = alert.showAndWait();
		
		if(result.get() == ButtonType.OK) {
			System.out.println("asdk");
			deleteItem(filename);
			userDetails.setVisible(false);
			clearUserDetails();
		}
		
	}



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		passwordFieldVISIBLE.setVisible(false);
		userDetails.setVisible(false);
		comboIcon.getItems().addAll("","Website","Passport","Card");
		// TODO Auto-generated method stub
		
	}
	


}
