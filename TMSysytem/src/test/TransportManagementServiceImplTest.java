package test;

import entity.Vehicle;
import entity.Trip;
import entity.Booking;
import entity.Driver;
import entity.Route;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dao.TransportManagementServiceImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class TransportManagementServiceImplTest {
	private TransportManagementServiceImpl service;
	private Connection connection;

	@BeforeEach
	void setUp() {
		String url = "jdbc:mysql://localhost:3307/tmanage"; 
		String username = "root"; 
		String password = "bsrisql"; 
		// Create an instance of the service
		service = new TransportManagementServiceImpl(url, username, password);
	}

	@AfterEach
	void tearDown() {
		if (service != null) {
			service.closeConnection();
		}
	}

	@Test
	void testAddVehicle() {
		Vehicle vehicle = new Vehicle(7, "Luxury Car", 4, "Private", "Available");
		assertTrue(service.addVehicle(vehicle), "Vehicle should be added successfully.");
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Test
	void testUpdateVehicle() {
		Vehicle vehicle = new Vehicle(8, "Mini Van", 8, "Private", "Available");
		service.addVehicle(vehicle);
		vehicle.setModel("Updated Mini Van");
		assertTrue(service.updateVehicle(vehicle), "Vehicle should be updated successfully.");
	}

	@Test
	void testDeleteVehicle() {
		Vehicle vehicle = new Vehicle(9, "Electric Bike", 1, "Private", "Available");
		service.addVehicle(vehicle);
		assertTrue(service.deleteVehicle(9), "Vehicle should be deleted successfully.");
	}

	@Test
	void testAddRoute() {
		Route route = new Route(7, "Miami", "Orlando", 350);
		assertTrue(service.addRoute(route), "Route should be added successfully.");
	}

	@Test
	void testUpdateRoute() {
		Route route = new Route(8, "Houston", "Austin", 200);
		service.addRoute(route);
		route.setEndDestination("Dallas");
		assertTrue(service.updateRoute(route), "Route should be updated successfully.");
	}

	@Test
	void testDeleteRoute() {
		Route route = new Route(9, "Boston", "Philadelphia", 320);
		service.addRoute(route);
		assertTrue(service.deleteRoute(9), "Route should be deleted successfully.");
	}

	@Test
	void testScheduleTrip() {
		Vehicle vehicle = new Vehicle(7, "Luxury Car", 4, "Private", "Available");
		Route route = new Route(7, "Miami", "Orlando", 350);
		service.addVehicle(vehicle);
		service.addRoute(route);
		assertTrue(service.scheduleTrip(7, 7, 7, "2024-10-10 08:00:00", "2024-10-10 12:00:00"),
				"Trip should be scheduled successfully.");
	}

	@Test
	void testCancelTrip() {
		Vehicle vehicle = new Vehicle(8, "Mini Van", 8, "Private", "Available");
		Route route = new Route(8, "Houston", "Austin", 200);
		service.addVehicle(vehicle);
		service.addRoute(route);
		service.scheduleTrip(8, 8, 8, "2024-10-11 08:00:00", "2024-10-11 12:00:00");
		assertTrue(service.cancelTrip(8), "Trip should be canceled successfully.");
	}

	@Test
	void testBookTrip() {
		Vehicle vehicle = new Vehicle(7, "Luxury Car", 4, "Private", "Available");
		Route route = new Route(7, "Miami", "Orlando", 350);
		service.addVehicle(vehicle);
		service.addRoute(route);
		service.scheduleTrip(7, 7, 7, "2024-10-10 08:00:00", "2024-10-10 12:00:00");
		assertTrue(service.bookTrip(7, 7, 7, "2024-10-05"), "Trip should be booked successfully.");
	}

	@Test
	void testCancelBooking() {
		Vehicle vehicle = new Vehicle(8, "Mini Van", 8, "Private", "Available");
		Route route = new Route(8, "Houston", "Austin", 200);
		service.addVehicle(vehicle);
		service.addRoute(route);
		service.scheduleTrip(8, 8, 8, "2024-10-11 08:00:00", "2024-10-11 12:00:00");
		service.bookTrip(8, 8, 8, "2024-10-05");
		assertTrue(service.cancelBooking(8), "Booking should be canceled successfully.");
	}

	@Test
	void testAllocateDriver() {
		Vehicle vehicle = new Vehicle(9, "Electric Bike", 1, "Private", "Available");
		Route route = new Route(9, "Boston", "Philadelphia", 320);
		Driver driver = new Driver(3, "Alex Smith", "LIC 67890");

		// Add vehicle and route
		service.addVehicle(vehicle);
		service.addRoute(route);

		// Schedule the trip
		service.scheduleTrip(9, 9, 9, "2024-10-12 08:00:00", "2024-10-12 12:00:00");

		// Allocate the driver to the trip
		assertTrue(service.allocateDriver(9, 3), "Driver should be allocated successfully.");
	}

	@Test
	void testDeallocateDriver() {
		Vehicle vehicle = new Vehicle(9, "Electric Bike", 1, "Private", "Available");
		Route route = new Route(9, "Boston", "Philadelphia", 320);
		Driver driver = new Driver(3, "Alex Smith", "LIC 67890");

		// Add vehicle and route
		service.addVehicle(vehicle);
		service.addRoute(route);

		// Schedule the trip
		service.scheduleTrip(9, 9, 9, "2024-10-12 08:00:00", "2024-10-12 12:00:00");

		// Allocate the driver to the trip
		service.allocateDriver(9, 3);

		// Deallocate the driver from the trip
		assertTrue(service.deallocateDriver(9), "Driver should be deallocated successfully.");
	}

	@Test
	void testGetBookingsByPassenger() {
		Vehicle vehicle = new Vehicle(7, "Luxury Car", 4, "Private", "Available");
		Route route = new Route(7, "Miami", "Orlando", 350);
		service.addVehicle(vehicle);
		service.addRoute(route);
		service.scheduleTrip(7, 7, 7, "2024-10-10 08:00:00", "2024-10-10 12:00:00");
		service.bookTrip(7, 7, 7, "2024-10-05");
		assertFalse(service.getBookingsByPassenger(7).isEmpty(), "Bookings should be retrieved for the passenger.");
	}

	@Test
	void testGetAvailableDrivers() {
		// Assuming the service internally has some drivers already added
		// You can assert if the list is not empty
		assertFalse(service.getAvailableDrivers().isEmpty(), "Available drivers should be retrieved successfully.");
	}
}