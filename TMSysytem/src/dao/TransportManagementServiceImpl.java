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
import entity.Route;
import entity.Vehicle;

public class TransportManagementServiceImpl implements TransportManagementService {
    private Connection connection;

    // Constructor to establish the database connection
    public TransportManagementServiceImpl(String url, String username, String password) {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

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
        return executeUpdate("INSERT INTO Vehicles (VehicleID, Model, Capacity, Vtype, Vstatus) VALUES (?, ?, ?, ?, ?)", 
                             vehicle.getVehicleId(), vehicle.getModel(), vehicle.getCapacity(), vehicle.getType(), vehicle.getStatus());
    }

    @Override
    public boolean updateVehicle(Vehicle vehicle) {
        return executeUpdate("UPDATE Vehicles SET Model = ?, Capacity = ?, Vtype = ?, Vstatus = ? WHERE VehicleID = ?",
                             vehicle.getModel(), vehicle.getCapacity(), vehicle.getType(), vehicle.getStatus(),
                             vehicle.getVehicleId());
    }

    @Override
    public boolean deleteVehicle(int vehicleId) {
        return executeUpdate("DELETE FROM Vehicles WHERE VehicleID = ?", vehicleId);
    }

    // Route management methods
    @Override
    public boolean addRoute(Route route) {
        return executeUpdate("INSERT INTO Routes (RouteID, StartDestination, EndDestination, Distance) VALUES (?, ?, ?, ?)",
                             route.getRouteId(), route.getStartDestination(), route.getEndDestination(), route.getDistance());
    }

    @Override
    public boolean updateRoute(Route route) {
        return executeUpdate("UPDATE Routes SET StartDestination = ?, EndDestination = ?, Distance = ? WHERE RouteID = ?",
                             route.getStartDestination(), route.getEndDestination(), route.getDistance(), route.getRouteId());
    }

    @Override
    public boolean deleteRoute(int routeId) {
        return executeUpdate("DELETE FROM Routes WHERE RouteID = ?", routeId);
    }

    // Trip management methods
    @Override
    public boolean scheduleTrip(int tripId, int vehicleId, int routeId, String departureDate, String arrivalDate) {
        String sql = "INSERT INTO Trips (TripID, VehicleID, RouteID, DepartureDate, ArrivalDate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, tripId);
            ps.setInt(2, vehicleId);
            ps.setInt(3, routeId);
            ps.setString(4, departureDate);
            ps.setString(5, arrivalDate);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean cancelTrip(int tripId) {
        return executeUpdate("DELETE FROM Trips WHERE TripID = ?", tripId);
    }

    // Booking management methods
    @Override
    public boolean bookTrip(int bookingId, int tripId, int passengerId, String bookingDate) {
        String sql = "INSERT INTO Bookings (BookingID, TripID, PassengerID, BookingDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ps.setInt(2, tripId);
            ps.setInt(3, passengerId);
            ps.setString(4, bookingDate);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        return executeUpdate("DELETE FROM Bookings WHERE BookingID = ?", bookingId);
    }

    @Override
    public boolean allocateDriver(int tripId, int driverId) {
        String sql = "UPDATE Trips SET DriverID = ? WHERE TripID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, driverId);  // Corrected driverId and tripId
            ps.setInt(2, tripId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deallocateDriver(int tripId) {
        return executeUpdate("UPDATE Trips SET DriverID = NULL WHERE TripID = ?", tripId);
    }

    // Booking retrieval methods
    @Override
    public List<Booking> getBookingsByPassenger(int passengerId) {
        return getBookings("SELECT * FROM Bookings WHERE PassengerID = ?", passengerId);
    }

    @Override
    public List<Booking> getBookingsByTrip(int tripId) {
        return getBookings("SELECT * FROM Bookings WHERE TripID = ?", tripId);
    }

    private List<Booking> getBookings(String sql, int param) {
        List<Booking> bookings = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, param);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(new Booking(rs.getInt("BookingID"), rs.getInt("TripID"), rs.getInt("PassengerID"),
                                         rs.getString("BookingDate")));
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
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Drivers WHERE Available = TRUE")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                drivers.add(new Driver(rs.getInt("ID"), rs.getString("Name"), rs.getString("LicenseNumber"))); 
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
