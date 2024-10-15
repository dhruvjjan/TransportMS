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

import static org.junit.jupiter.api.Assertions.*;

class TransportManagementServiceImplTest {
    private TransportManagementServiceImpl service;

    @BeforeEach
    void setUp() {
        String url = "jdbc:mysql://localhost:3306/tmsystem?useSSL=false"; 
        String username = "root"; 
        String password = "root"; 
        service = new TransportManagementServiceImpl(url, username, password);
        
        // Clear the database tables before each test
        service.executeUpdate("DELETE FROM Bookings");
        service.executeUpdate("DELETE FROM Trips");
        service.executeUpdate("DELETE FROM Routes");
        service.executeUpdate("DELETE FROM Vehicles"); 
    }

    @AfterEach
    void tearDown() {
        if (service != null) {
            service.closeConnection();
        }
    }

    @Test
    void testAddVehicle() {
        Vehicle vehicle = new Vehicle(1, "Luxury Car", 4, "Private", "Available");
        assertTrue(service.addVehicle(vehicle), "Vehicle should be added successfully.");
    }

    @Test
    void testUpdateVehicle() {
        Vehicle vehicle = new Vehicle(2, "Mini Van", 8, "Private", "Available");
        service.addVehicle(vehicle);
        vehicle.setModel("Updated Mini Van");
        assertTrue(service.updateVehicle(vehicle), "Vehicle should be updated successfully.");
    }

    @Test
    void testDeleteVehicle() {
        Vehicle vehicle = new Vehicle(3, "Electric Bike", 1, "Private", "Available");
        service.addVehicle(vehicle);
        assertTrue(service.deleteVehicle(3), "Vehicle should be deleted successfully.");
    }

    @Test
    void testAddRoute() {
        Route route = new Route(1, "Miami", "Orlando", 350);
        assertTrue(service.addRoute(route), "Route should be added successfully.");
    }

    @Test
    void testUpdateRoute() {
        Route route = new Route(2, "Houston", "Austin", 200);
        service.addRoute(route);
        route.setEndDestination("Dallas");
        assertTrue(service.updateRoute(route), "Route should be updated successfully.");
    }

    @Test
    void testDeleteRoute() {
        Route route = new Route(3, "Boston", "Philadelphia", 320);
        service.addRoute(route);
        assertTrue(service.deleteRoute(3), "Route should be deleted successfully.");
    }

    @Test
    void testScheduleTrip() {
        Vehicle vehicle = new Vehicle(4, "Luxury Car", 4, "Private", "Available");
        Route route = new Route(4, "Miami", "Orlando", 350);
        service.addVehicle(vehicle);
        service.addRoute(route);
        
        // Schedule the trip with MaxPassengers
        assertTrue(service.scheduleTrip(vehicle.getVehicleId(), route.getRouteId(), 
                                         "2024-10-10 08:00:00", "2024-10-10 12:00:00", 4),
                    "Trip should be scheduled successfully.");
    }

    @Test
    void testCancelTrip() {
        Vehicle vehicle = new Vehicle(5, "Mini Van", 8, "Private", "Available");
        Route route = new Route(5, "Houston", "Austin", 200);
        service.addVehicle(vehicle);
        service.addRoute(route);
        
        // Schedule the trip first
        service.scheduleTrip(vehicle.getVehicleId(), route.getRouteId(), 
                             "2024-10-11 08:00:00", "2024-10-11 12:00:00", 4);
        
        // Cancel the trip using the trip ID
        assertTrue(service.cancelTrip(1), "Trip should be canceled successfully."); // Ensure the correct trip ID
    }

    @Test
    void testBookTrip() {
        Vehicle vehicle = new Vehicle(6, "Luxury Car", 4, "Private", "Available");
        Route route = new Route(6, "Miami", "Orlando", 350);
        service.addVehicle(vehicle);
        service.addRoute(route);
        
        // Schedule the trip first
        service.scheduleTrip(vehicle.getVehicleId(), route.getRouteId(), 
                             "2024-10-10 08:00:00", "2024-10-10 12:00:00", 4);
        
        // Book the trip
        assertTrue(service.bookTrip(1, 1, 1, "2024-10-05"), "Trip should be booked successfully."); // Ensure IDs are correct
    }

    @Test
    void testCancelBooking() {
        Vehicle vehicle = new Vehicle(7, "Mini Van", 8, "Private", "Available");
        Route route = new Route(7, "Houston", "Austin", 200);
        service.addVehicle(vehicle);
        service.addRoute(route);
        
        // Schedule the trip first
        service.scheduleTrip(vehicle.getVehicleId(), route.getRouteId(), 
                             "2024-10-11 08:00:00", "2024-10-11 12:00:00", 4);
        
        // Book the trip
        service.bookTrip(1, 1, 1, "2024-10-05");
        
        // Now cancel the booking
        assertTrue(service.cancelBooking(1), "Booking should be canceled successfully."); // Ensure the correct booking ID
    }

    @Test
    void testAllocateDriver() {
        Vehicle vehicle = new Vehicle(8, "Electric Bike", 1, "Private", "Available");
        Route route = new Route(8, "Boston", "Philadelphia", 320);
        Driver driver = new Driver(1, "Alex Smith", "LIC 67890");

        // Add vehicle and route
        service.addVehicle(vehicle);
        service.addRoute(route);

        // Schedule the trip
        service.scheduleTrip(vehicle.getVehicleId(), route.getRouteId(), 
                             "2024-10-12 08:00:00", "2024-10-12 12:00:00", 1);

        // Ensure the driver is allocated correctly
        assertTrue(service.allocateDriver(1, driver.getDriverId()), "Driver should be allocated successfully.");
    }


    @Test
    void testDeallocateDriver() {
        Vehicle vehicle = new Vehicle(9, "Electric Bike", 1, "Private", "Available");
        Route route = new Route(9, "Boston", "Philadelphia", 320);
        service.addVehicle(vehicle);
        service.addRoute(route);
        
        // Schedule the trip
        service.scheduleTrip(vehicle.getVehicleId(), route.getRouteId(), 
                             "2024-10-12 08:00:00", "2024-10-12 12:00:00", 1);

        // Allocate the driver to the trip
        service.allocateDriver(1, 1); // Assuming driver ID is 1
        
        // Deallocate the driver from the trip
        assertTrue(service.deallocateDriver(1), "Driver should be deallocated successfully.");
    }

    @Test
    void testGetBookingsByPassenger() {
        Vehicle vehicle = new Vehicle(10, "Luxury Car", 4, "Private", "Available");
        Route route = new Route(10, "Miami", "Orlando", 350);
        service.addVehicle(vehicle);
        service.addRoute(route);
        
        // Schedule the trip
        service.scheduleTrip(vehicle.getVehicleId(), route.getRouteId(), 
                             "2024-10-10 08:00:00", "2024-10-10 12:00:00", 4);
        
        // Book the trip
        service.bookTrip(1, 1, 1, "2024-10-05");
        
        // Verify bookings for the passenger
        assertFalse(service.getBookingsByPassenger(1).isEmpty(), "Bookings should be retrieved for the passenger.");
    }

    @Test
    void testGetAvailableDrivers() {
        // Assuming the service internally has some drivers already added
        // You can assert if the list is not empty
        assertFalse(service.getAvailableDrivers().isEmpty(), "Available drivers should be retrieved successfully.");
    }
}
