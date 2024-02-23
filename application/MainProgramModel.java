package application;

import java.sql.Connection;
import java.lang.Math;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

//Importing universal methods from utilityMethods.java
public class MainProgramModel extends utilityMethods{
	
	
	public static String generatePassword() {
		String password = "";
		String letter="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String integer="1234567890";
		int min,max;
		for(int i=0;i<10;i++) {
			min = 0;
			max = 26;
			int randomLetter = (int)(Math.random() * (max-min)) + min;
			max = 10;
			int randomInteger = (int)(Math.random() * (max-min)) + min;
			max = 2;
			int choice = (int)(Math.random() * (max-min)) + min;
			if(choice==0) {
				password = password + letter.charAt(randomLetter);
			}
			else {
				password = password + integer.charAt(randomInteger);
			}

		}
		return password;
	}
	
	
	public static Boolean addItem(int id, String filename, String username, String password, String website, String email, String icon, String notes) {
		
		Connection connection = null;
		PreparedStatement psEnterDetails = null;
		PreparedStatement psFetchFile = null;
		ResultSet rs = null;
		Boolean check = false;
		
		try {
			//Establishing connection between program and database (JDBC driver already installed in program)
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginsdb" , "root", "w4lc0m42");
			//Preparing statement for database to fetch or manipulate information
			psFetchFile = connection.prepareStatement("SELECT filename from usercontents where filename = ?");
			psFetchFile.setString(1, filename);
			//Executing query
			rs = psFetchFile.executeQuery();
			if(rs.isBeforeFirst()) {
				errorAlert("DB Error","Filename already exists in DB",
						"Filename must be unique. Please enter a new filename and retry.");
			}
			else {
				check=true;
				//ADD MORE SQL INJECTION PREVENTION IF POSSIBLE
				psEnterDetails = connection.prepareStatement("INSERT INTO usercontents (id,filename,username,password,website,email,icon,notes) VALUES(?,?,?,?,?,?,?,?)");
				psEnterDetails.setInt(1, id);
				psEnterDetails.setString(2, filename);
				psEnterDetails.setString(3, username);
				psEnterDetails.setString(4, password);
				psEnterDetails.setString(5, website);
				psEnterDetails.setString(6, email);
				psEnterDetails.setString(7, icon);
				psEnterDetails.setString(8, notes);
				psEnterDetails.executeUpdate();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(psEnterDetails != null) {
				try {
					psEnterDetails.close();
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(psFetchFile != null) {
				try {
					psFetchFile.close();
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
		return check;
	}
	
   public static Boolean checkFilename(String newFilename) {
	Connection connection = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	Boolean flag = true;
	
	try {
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginsdb" , "root", "w4lc0m42");
		ps = connection.prepareStatement("SELECT filename from usercontents");
		rs = ps.executeQuery();
		while(rs.next()) {
			String filename = rs.getString("filename");
			if(newFilename.equals(filename)) {
				 flag = false;
			}
		}
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
	finally {
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
		if(rs != null) {
			try {
				rs.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	return flag;
	
   }
	
public static void editItem(String curFilename, String newFilename, String username, String password, String website, String email, String icon, String notes) {
		
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginsdb" , "root", "w4lc0m42");
			//ADD MORE SQL INJECTION PREVENTION IF POSSIBLE
			ps = connection.prepareStatement("UPDATE usercontents SET filename = ?, username = ?, password = ?, website = ?, email = ?, icon = ?, notes = ? WHERE filename = ?");
			ps.setString(1, newFilename);
			ps.setString(2, username);
			ps.setString(3, password);
			ps.setString(4, website);
			ps.setString(5, email);
			ps.setString(6, icon);
			ps.setString(7, notes);
			ps.setString(8, curFilename);
			ps.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
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
    
public static void deleteItem(String filename) {
	Connection connection = null;
	PreparedStatement ps = null;
	
	try {
		String sql = "DELETE FROM usercontents WHERE filename = ?";
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginsdb" , "root", "w4lc0m42");
		//ADD MORE SQL INJECTION PREVENTION IF POSSIBLE
		ps = connection.prepareStatement(sql);
		ps.setString(1, filename);
		ps.executeUpdate();
		utilityMethods.informationAlert("DB Info", "Item successfully deleted!", "Your item has been deleted successfully!");
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
	finally {
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
	
	public static ArrayList<String> getFiles(int id){ 
		ArrayList<String> filenameList = new ArrayList<String>();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			//ADD MORE SQL INJECTION PREVENTION IF POSSIBLE
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginsdb" , "root", "w4lc0m42");
			ps = connection.prepareStatement("SELECT filename FROM usercontents where id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(!rs.isBeforeFirst()) {
				System.out.println("faield");
			}
			else {
				while(rs.next()) {
					filenameList.add(rs.getString("filename"));
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
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
			if(rs != null) {
				try {
					rs.close();
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return filenameList;
		
	}
	
	public static ArrayList<String> getFields(String filename){
		ArrayList<String> fileContents = new ArrayList<String>();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			//ADD MORE SQL INJECTION PREVENTION IF POSSIBLE
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginsdb" , "root", "w4lc0m42");
			ps = connection.prepareStatement("SELECT * FROM usercontents where filename = ?");
			ps.setString(1, filename);
			rs = ps.executeQuery();
			if(!rs.isBeforeFirst()) {
				utilityMethods.errorAlert("DB Error", "Failed to fetch contents of this item", "Please retry again later.");
			}
			else {
				while(rs.next()) {
					fileContents.add(rs.getString("username"));
					fileContents.add(rs.getString("password"));
					fileContents.add(rs.getString("website"));
					fileContents.add(rs.getString("email"));
					fileContents.add(rs.getString("icon"));
					fileContents.add(rs.getString("notes"));
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
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
			if(rs != null) {
				try {
					rs.close();
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return fileContents;
	}
}
