package entity;

import java.sql.Connection;
import entity.DBUtil;

public class DBUtilTest {
	public static void main(String[] args) {
		// Test the database connection
		Connection connection = DBUtil.getDBConn();
		// Check if connection is established successfully
		if (connection != null) {
			System.out.println("Connection is successful!");
		} else {
			System.out.println("Failed to establish connection.");
		}
		// Close the connection if it's open
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				System.out.println("Connection closed successfully.");
			}
		} catch (Exception e) {
			System.err.println("Failed to close connection: " + e.getMessage());
		}
	}
}
