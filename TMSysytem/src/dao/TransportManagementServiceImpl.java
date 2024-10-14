package dao;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entity.Trip;
import entity.Booking;
import entity.Driver;
import entity.Route; // Ensure you import the Route entity
import entity.Vehicle;

public class TransportManagementServiceImpl implements TransportManagementService {
	private Connection connection;

	// Constructor to establish the database connection
	public TransportManagementServiceImpl(String url, String username, String password) {
		try {
			// Load the JDBC driver (optional for newer JDBC versions)
			Class.forName("com.mysql.cj.jdbc.Driver"); // Use appropriate driver class for your DBMS 

			// Initialize the connection
			this.connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tmsystem?useSSL=false", "root", "root");
			System.out.println("Database connection established successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed to establish database connection.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("JDBC Driver not found.");
		}
	}

	// Method to execute update queries
	private boolean executeUpdate(String sql, Object... params) {
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			for (int i = 0; i < params.length; i++) {
				stmt.setObject(i + 1, params[i]);
			}
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Vehicle management methods
	@Override
	public boolean addVehicle(Vehicle vehicle) {
		return executeUpdate("INSERT INTO Vehicle (vehicleId, model, capacity, type, status) VALUES (?, ?, ?, ?, ?)",
				vehicle.getVehicleId(), vehicle.getModel(), vehicle.getCapacity(), vehicle.getType(),
				vehicle.getStatus());
	}

	@Override
	public boolean updateVehicle(Vehicle vehicle) {
		return executeUpdate("UPDATE Vehicle SET model = ?, capacity = ?, type = ?, status = ? WHERE vehicleId = ?",
				vehicle.getModel(), vehicle.getCapacity(), vehicle.getType(), vehicle.getStatus(),
				vehicle.getVehicleId());
	}

	@Override
	public boolean deleteVehicle(int vehicleId) {
		return executeUpdate("DELETE FROM Vehicle WHERE vehicleId = ?", vehicleId);
	}

	// Route management methods
	@Override
	public boolean addRoute(Route route) {
		return executeUpdate(
				"INSERT INTO Route (routeId, startDestination, endDestination, distance) VALUES (?, ?, ?, ?)",
				route.getRouteId(), route.getStartDestination(), route.getEndDestination(), route.getDistance());
	}

	@Override
	public boolean updateRoute(Route route) {
		return executeUpdate(
				"UPDATE Route SET startDestination = ?, endDestination = ?, distance = ? WHERE routeId = ?",
				route.getStartDestination(), route.getEndDestination(), route.getDistance(), route.getRouteId());
	}

	@Override
	public boolean deleteRoute(int routeId) {
		return executeUpdate("DELETE FROM Route WHERE routeId = ?", routeId);
	}

	// Trip management methods
	@Override
	public boolean scheduleTrip(int tripId, int vehicleId, int routeId, String departureDate, String arrivalDate) {
		String sql = "INSERT INTO Trip (tripId, vehicleId, routeId, departureDate, arrivalDate) VALUES (?, ?, ?, ?, ?)";
		// Now use the sql variable in the PreparedStatement
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, tripId); // Setting tripId
			ps.setInt(2, vehicleId); // Setting vehicleId
			ps.setInt(3, routeId); // Setting routeId
			ps.setString(4, departureDate); // Setting departureDate
			ps.setString(5, arrivalDate); // Setting arrivalDate
			ps.executeUpdate(); // Execute the SQL query
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean cancelTrip(int tripId) {
		return executeUpdate("DELETE FROM Trip WHERE tripId = ?", tripId);
	}

	// Booking management methods
	@Override
	public boolean bookTrip(int bookingId, int tripId, int passengerId, String bookingDate) {
		String sql = "INSERT INTO Booking (bookingId, tripId, passengerId, bookingDate) VALUES (?, ?, ?, ?)";

		// SQL statement including bookingId

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, bookingId); // Setting tripId
			ps.setInt(2, tripId); // Setting vehicleId
			ps.setInt(3, passengerId); // Setting routeId
			ps.setString(4, bookingDate); // Setting departureDate
			// Setting arrivalDate
			ps.executeUpdate(); // Execute the SQL query
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean cancelBooking(int bookingId) {
		return executeUpdate("DELETE FROM Booking WHERE bookingId = ?", bookingId);
	}

	@Override
	public boolean allocateDriver(int tripId, int driverId) {
		String sql = "UPDATE Trip SET driverId = ? WHERE tripId = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, tripId); // Setting tripId
			ps.setInt(2, driverId); // Setting vehicleId
			// Setting departureDate
			// Setting arrivalDate
			ps.executeUpdate(); // Execute the SQL query
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deallocateDriver(int tripId) {
		return executeUpdate("UPDATE Trip SET driverId = NULL WHERE tripId = ?", tripId);
	}

	// Booking retrieval methods
	@Override
	public List<Booking> getBookingsByPassenger(int passengerId) {
		return getBookings("SELECT * FROM Booking WHERE passengerId = ?", passengerId);
	}

	@Override
	public List<Booking> getBookingsByTrip(int tripId) {
		return getBookings("SELECT * FROM Booking WHERE tripId = ?", tripId);
	}

	private List<Booking> getBookings(String sql, int param) {
		List<Booking> bookings = new ArrayList<>();
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, param);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				bookings.add(new Booking(rs.getInt("bookingId"), rs.getInt("tripId"), rs.getInt("passengerId"),
						rs.getString("bookingDate")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookings;
	}

	// Driver retrieval method
	@Override
	public List<Driver> getAvailableDrivers() {
		List<Driver> drivers = new ArrayList<>();
		try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Driver WHERE licenseNumber = TRUE")) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				drivers.add(new Driver(rs.getInt("id"), rs.getString("name"), rs.getString("available"))); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return drivers;
	}

	public void closeConnection() {
	    if (connection != null) {
	        try {
	            connection.close();
	            System.out.println("Database connection closed successfully.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("Failed to close database connection.");
	        }
	    }
	}

}
