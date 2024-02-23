package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class SettingsModel {
	
	public static void changeUsername(ActionEvent event, String curUsername, String newUsername) {
		Connection connection = null;
		PreparedStatement psChangeUsername = null;
		PreparedStatement psUserExists = null;
		PreparedStatement psNewUserExists = null;
		ResultSet rsCheckUserExists = null;
		ResultSet rsCheckUserHasChanged = null;
		
		try {
			//ADD MORE SQL INJECTION PREVENTION IF POSSIBLE
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginsdb" , "root", "w4lc0m42");
			String sql1 = "SELECT username from userlogins where username = ?";
			psUserExists = connection.prepareStatement(sql1);
			psUserExists.setString(1, curUsername);
			rsCheckUserExists = psUserExists.executeQuery();
			if(!rsCheckUserExists.isBeforeFirst()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setResizable(false);
				alert.setTitle("DB Error");
				alert.setHeaderText("Username does not exist!");
				alert.setContentText("Cannot find username in the database");
				alert.show();
			}
			else {
				String sql2 = "UPDATE userlogins SET username = ? WHERE username = ?";
				psChangeUsername = connection.prepareStatement(sql2);
				psChangeUsername.setString(1, newUsername);
				psChangeUsername.setString(2, curUsername);
				psChangeUsername.executeUpdate();
				//Checking it has been updated
				psNewUserExists = connection.prepareStatement(sql1);
				psNewUserExists.setString(1, newUsername);
				rsCheckUserHasChanged = psNewUserExists.executeQuery();
				if(!rsCheckUserHasChanged.isBeforeFirst()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setResizable(false);
					alert.setTitle("DB Error");
					alert.setHeaderText("Username has not been changed!");
					alert.setContentText("Could not change current username, please try again.");
					alert.show();
				}
				else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setResizable(false);
					alert.setTitle("DB Info");
					alert.setHeaderText("Username has been changed successfully!");
					alert.setContentText("Your username has been changed to "+newUsername+" from "+curUsername+".");
					alert.show();
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(rsCheckUserExists != null) {
				try {
					rsCheckUserExists.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(rsCheckUserHasChanged != null) {
				try {
					rsCheckUserHasChanged.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(psUserExists != null) {
				try {
					psUserExists.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(psNewUserExists != null) {
				try {
					psNewUserExists.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void changePassword(ActionEvent event, String curPassword, String newPassword) {
		Connection connection = null;
		PreparedStatement psPassExists = null;
		PreparedStatement psChangePass = null;
		PreparedStatement psNewPassExists = null;
		ResultSet rsCheckUserExists = null;
		ResultSet rsCheckUserHasChanged = null;
		
		try {
			//ADD MORE SQL INJECTION PREVENTION IF POSSIBLE
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginsdb" , "root", "w4lc0m42");
			String sql1 = "SELECT password from userlogins where password = ?";
			psPassExists = connection.prepareStatement(sql1);
			psPassExists.setString(1, curPassword);
			rsCheckUserExists = psPassExists.executeQuery();
			if(!rsCheckUserExists.isBeforeFirst()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setResizable(false);
				alert.setTitle("DB Error");
				alert.setHeaderText("Password does not exist!");
				alert.setContentText("Cannot find password in the database");
				alert.show();
			}
			else {
				String sql2 = "UPDATE userlogins SET password = ? WHERE password = ?";
				psChangePass = connection.prepareStatement(sql2);
				psChangePass.setString(1, newPassword);
				psChangePass.setString(2, curPassword);
				psChangePass.executeUpdate();
				//Checking it has been updated
				psNewPassExists = connection.prepareStatement(sql1);
				psNewPassExists.setString(1, newPassword);
				rsCheckUserHasChanged = psNewPassExists.executeQuery();
				if(!rsCheckUserHasChanged.isBeforeFirst()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setResizable(false);
					alert.setTitle("DB Error");
					alert.setHeaderText("Password has not been changed!");
					alert.setContentText("Could not change current password, please try again.");
					alert.show();
				}
				else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setResizable(false);
					alert.setTitle("DB Info");
					alert.setHeaderText("Password has been changed successfully!");
					alert.setContentText("Your password has been changed to "+newPassword+" from "+curPassword+".");
					alert.show();
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(rsCheckUserExists != null) {
				try {
					rsCheckUserExists.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(rsCheckUserHasChanged != null) {
				try {
					rsCheckUserHasChanged.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(psPassExists != null) {
				try {
					psPassExists.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(psNewPassExists != null) {
				try {
					psNewPassExists.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void resetAccount(String username, String password) {
		Connection connection = null;
		PreparedStatement psUserExists = null;
		PreparedStatement psDeleteItems = null;
		ResultSet rs = null;
		int id = 0;
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginsdb" , "root", "w4lc0m42");
			String sql = "SELECT id FROM userlogins where username = ? and password = ?";
			psUserExists = connection.prepareStatement(sql);
			psUserExists.setString(1, username);
			psUserExists.setString(2, password);
			rs = psUserExists.executeQuery();
			if(!rs.isBeforeFirst()) {
				utilityMethods.errorAlert("DB Error", "Username or password not found", "Username or password is not present in database. Please re-enter user login details.");
			}
			else {
				while(rs.next()) {
					id = rs.getInt("id");
				}
				sql = "DELETE FROM usercontents where id = ?";
				psDeleteItems = connection.prepareStatement(sql);
				psDeleteItems.setInt(1, id);
				psDeleteItems.executeUpdate();
				utilityMethods.informationAlert("DB Info", "All items successfully deleted!", "All items are now deleted. If you wish to delete account, please do so in the main menu.");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(rs != null) {
				try {
					rs.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(psUserExists != null) {
				try {
					psUserExists.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(psDeleteItems != null) {
				try {
					psDeleteItems.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
	}
}
