package entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/tmsystem";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	public static Connection getDBConn() {
		Connection connection = null;
		try {
			System.out.println("Loading JDBC driver...");
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("JDBC Driver loaded successfully!");
			System.out.println("Attempting to establish connection...");
			connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			System.out.println("Database connection established successfully.");
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver not found: " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Connection failed: " + e.getMessage());
		}
		return connection;
	}

	public static void main(String[] args) {
		getDBConn(); // Test the connection
	}
}
