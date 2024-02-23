package application;

import java.sql.*;

public class SqliteConnection {
	
	public static void main(String[] args) {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginsdb" , "root", "w4lc0m42");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from userlogins");
			while(rs.next()) {
				System.out.println(rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
