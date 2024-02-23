package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginModel extends utilityMethods{
	
	public static void signUp(ActionEvent event, String username, String password) {
		Connection connection = null;
		PreparedStatement psEnterDetails = null;
		PreparedStatement psUserExists = null;
		ResultSet rs = null;
		
		try {
			//ADD MORE SQL INJECTION PREVENTION IF POSSIBLE
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginsdb" , "root", "w4lc0m42");
			psUserExists = connection.prepareStatement("SELECT * FROM userlogins WHERE username = ?");
			psUserExists.setString(1, username);
			rs = psUserExists.executeQuery();
			if(rs.isBeforeFirst()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setResizable(false);
				alert.setTitle("Login error");
				alert.setHeaderText("Username taken!");
				alert.setContentText("Username already exists. Please enter another");
				alert.show();
			}
			else {
				if(username.length()<3 || password.length()<3) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setResizable(false);
					alert.setTitle("Login error");
					alert.setHeaderText("Username or password too short");
					alert.setContentText("Username or password must be atleast 3 letters long each");
					alert.show();
				}
				else {
					psEnterDetails = connection.prepareStatement("INSERT INTO userlogins (username, password) VALUES (?,?)");
					psEnterDetails.setString(1,username);
					psEnterDetails.setString(2, password);
					psEnterDetails.executeUpdate();
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setResizable(false);
					alert.setTitle("Login information");
					alert.setHeaderText("Signup successful!");
					alert.setContentText("You have successfully signed up. Please login with details using the login button.");
					alert.show();
				}
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
			if(psEnterDetails != null) {
				try {
					psEnterDetails.close();
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
	
	public static void login(ActionEvent event, String username, String password) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginsdb" , "root", "w4lc0m42");
			String sql = "SELECT password FROM userlogins where username = ?";
			ps = connection.prepareStatement(sql); //Inbuilt sql injection prevention
			ps.setString(1, username);
			rs = ps.executeQuery();
			if(!rs.isBeforeFirst()) {
				System.out.println("result not found");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setResizable(false);
				alert.setTitle("Login error");
				alert.setHeaderText("Username or password not found!");
				alert.setContentText("Username or password is incorrect. Please try again");
				alert.show();
			}
			else {
				while(rs.next()) {
					String retrievedPassword = rs.getString("password");
					if(retrievedPassword.equals(password)) {
						System.out.println("login successful");
						utilityMethods.changeSceneLogin(event,"MainProgram.fxml",username);
					}
					else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setResizable(false);
						alert.setTitle("Login error");
						alert.setHeaderText("Password is incorrect!");
						alert.setContentText("Password credentials do not match. Please try again");
						alert.show();
					}
				}
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
			if(ps != null) {
				try {
					ps.close();
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
	
	public static void deleteAccount(ActionEvent event, String username) { //Delete ID and account details in other DB
		Connection connection = null;
		PreparedStatement psCheckUserExists = null;
		PreparedStatement psDeleteUser = null;
		ResultSet rsCheckUserExists = null;
		ResultSet rsCheckUserExistsAgain = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginsdb" , "root", "w4lc0m42");
			String sql = "SELECT username FROM userlogins where username = ?";
			psCheckUserExists = connection.prepareStatement(sql); //Inbuilt sql injection prevention
			psCheckUserExists.setString(1, username);
			rsCheckUserExists = psCheckUserExists.executeQuery();
			if(!rsCheckUserExists.isBeforeFirst()) {
				System.out.println("username not found");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setResizable(false);
				alert.setTitle("DB error");
				alert.setHeaderText("Username not found!");
				alert.setContentText("Cannot recognise username in database. Please try again.");
				alert.show();
			}
			else {
				sql = "DELETE FROM userlogins where username = ?";
				psDeleteUser = connection.prepareStatement(sql);
				psDeleteUser.setString(1, username);
				psDeleteUser.executeUpdate();
				rsCheckUserExistsAgain = psCheckUserExists.executeQuery();
				if(!rsCheckUserExistsAgain.isBeforeFirst()) {
					System.out.println("reach");
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setResizable(false);
					alert.setTitle("DB INFO");
					alert.setHeaderText("Account successfully deleted!");
					alert.setContentText("Account containing the username "+username+" has been successfully deleted!");
					alert.show();
				}
				else {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setResizable(false);
					alert.setTitle("DB ERROR");
					alert.setHeaderText("Account not deleted.");
					alert.setContentText("Account has not been deleted from database. Please try again");
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
			if(rsCheckUserExistsAgain != null) {
				try {
					rsCheckUserExistsAgain.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(psCheckUserExists != null) {
				try {
					psCheckUserExists.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(psDeleteUser != null) {
				try {
					psDeleteUser.close();
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
	
	public static int getid(String username) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int retrievedID = 0;
		
		try {
			//ADD MORE SQL INJECTION PREVENTION IF POSSIBLE
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginsdb" , "root", "w4lc0m42");
			ps = connection.prepareStatement("SELECT id FROM userlogins WHERE username = ?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			while(rs.next()) {
				retrievedID = rs.getInt("id");
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
			if(ps != null) {
				try {
					ps.close();
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
		return retrievedID;
	}
}
