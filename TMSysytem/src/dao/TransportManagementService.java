package dao;

import entity.Vehicle;

import entity.Trip;
import entity.Booking;
import entity.Driver;
import entity.Route; // Import the Route entity
import java.util.List;

public interface TransportManagementService {

	// Vehicle management methods
	boolean addVehicle(Vehicle vehicle);

	boolean updateVehicle(Vehicle vehicle);

	boolean deleteVehicle(int vehicleId);

	// Route management methods
	boolean addRoute(Route route); // Add a route

	boolean updateRoute(Route route); // Update a route

	boolean deleteRoute(int routeId); // Delete a route

	// Trip management methods
	boolean scheduleTrip(int tripId, int vehicleId, int routeId, String departureDate, String arrivalDate);

	boolean cancelTrip(int tripId);

	// Booking management methods
	boolean bookTrip(int bookingId, int tripId, int passengerId, String bookingDate);

	boolean cancelBooking(int bookingId);

	// Driver management methods
	boolean allocateDriver(int tripId, int driverId);

	boolean deallocateDriver(int tripId);

	// Booking retrieval methods
	List<Booking> getBookingsByPassenger(int passengerId);

	List<Booking> getBookingsByTrip(int tripId);

	// Driver retrieval method
	List<Driver> getAvailableDrivers();
}
