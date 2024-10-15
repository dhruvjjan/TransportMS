package app;

import dao.TransportManagementServiceImpl;
import entity.Vehicle;
import entity.Booking;
import entity.Driver;
import entity.Route; // Assuming you have a Route entity
import java.util.Scanner;

public class TransportManagementApp {
	public static void main(String[] args) {
		// Database connection parameters
		String url = "jdbc:mysql://localhost:3306/tmsystem"; // Replace with your database name
		String username = "root"; // Replace with your database username
		String password = "root"; // Replace with your database password
		// Create an instance of the service with the database connection parameters
		TransportManagementServiceImpl service = new TransportManagementServiceImpl(url, username, password);

		Scanner scanner = new Scanner(System.in);
		// Menu for interacting with the system
		while (true) {
			System.out.println("\nTransport Management System");
			System.out.println("1. Add Vehicle");
			System.out.println("2. Update Vehicle");
			System.out.println("3. Delete Vehicle");
			System.out.println("4. Schedule Trip");
			System.out.println("5. Cancel Trip");
			System.out.println("6. Book Trip");
			System.out.println("7. Cancel Booking");
			System.out.println("8. Allocate Driver");
			System.out.println("9. Deallocate Driver");
			System.out.println("10. Get Bookings By Passenger");
			System.out.println("11. Get Available Drivers");
			System.out.println("12. Add Route"); // New option
			System.out.println("13. Update Route"); // New option
			System.out.println("14. Delete Route"); // New option
			System.out.println("15. Exit");
			System.out.print("Choose an option: ");
			int choice = scanner.nextInt();
			int tripId;
			int bookingId;
			switch (choice) {
			case 1:
				// Add a vehicle
				System.out.print("Enter vehicle ID: ");
				int vehicleId = scanner.nextInt();
				System.out.print("Enter vehicle model: ");
				String model = scanner.next();
				System.out.print("Enter vehicle capacity: ");
				int capacity = scanner.nextInt();
				System.out.print("Enter vehicle type: ");
				String type = scanner.next();
				System.out.print("Enter vehicle status: ");
				String status = scanner.next();
				Vehicle vehicle = new Vehicle(vehicleId, model, capacity, type, status);
				if (service.addVehicle(vehicle)) {
					System.out.println("Vehicle added successfully!");
				} else {
					System.out.println("Failed to add vehicle.");
				}
				break;
			case 2:
				// Update vehicle
				System.out.print("Enter vehicle ID to update: ");
				vehicleId = scanner.nextInt();
				System.out.print("Enter new vehicle model: ");
				model = scanner.next();
				System.out.print("Enter new vehicle capacity: ");
				capacity = scanner.nextInt();
				System.out.print("Enter new vehicle type: ");
				type = scanner.next();
				System.out.print("Enter new vehicle status: ");
				status = scanner.next();
				Vehicle updatedVehicle = new Vehicle(vehicleId, model, capacity, type, status);
				if (service.updateVehicle(updatedVehicle)) {
					System.out.println("Vehicle updated successfully!");
				} else {
					System.out.println("Failed to update vehicle.");
				}
				break;
			case 3:
				// Delete vehicle
				System.out.print("Enter vehicle ID to delete: ");
				vehicleId = scanner.nextInt();
				if (service.deleteVehicle(vehicleId)) {
					System.out.println("Vehicle deleted successfully!");
				} else {
					System.out.println("Failed to delete vehicle.");
				}
				break;
			case 4:
				// Schedule a trip
				System.out.print("Enter Trip ID: "); // Updated prompt
				tripId = scanner.nextInt(); // Capture Trip ID
				System.out.print("Enter Vehicle ID: "); // Correct prompt
				vehicleId = scanner.nextInt(); // Capture Vehicle ID
				System.out.print("Enter Route ID: ");
				int routeId = scanner.nextInt(); // Capture Route ID
				System.out.print("Enter departure date (yyyy-MM-dd HH:mm:ss): ");
				String departureDate = scanner.next(); // Capture Departure Date
				System.out.print("Enter arrival date (yyyy-MM-dd HH:mm:ss): ");
				String arrivalDate = scanner.next(); // Capture Arrival Date
				System.out.print("Enter maximum number of passengers: "); // New prompt for MaxPassengers
				int maxPassengers = scanner.nextInt(); // Capture Max Passengers

				// Schedule the trip with the new parameter
				if (service.scheduleTrip(vehicleId, routeId, departureDate, arrivalDate, maxPassengers)) {
				    System.out.println("Trip scheduled successfully!");
				} else {
				    System.out.println("Failed to schedule trip.");
				}
				break;

			case 5:
				// Cancel a trip
				System.out.print("Enter trip ID to cancel: ");
				tripId = scanner.nextInt();
				if (service.cancelTrip(tripId)) {
					System.out.println("Trip canceled successfully!");
				} else {
					System.out.println("Failed to cancel trip.");
				}
				break;
			case 6:
				// Book a trip
				System.out.print("Enter booking ID to book: ");
				bookingId = scanner.nextInt();
				System.out.print("Enter trip ID to book: ");
				tripId = scanner.nextInt();
				System.out.print("Enter passenger ID: ");
				int passengerId = scanner.nextInt();
				System.out.print("Enter booking date: ");
				String bookingDate = scanner.next();
				if (service.bookTrip(bookingId, tripId, passengerId, bookingDate)) {
					System.out.println("Trip booked successfully!");
				} else {
					System.out.println("Failed to book trip.");
				}
				break;
			case 7:
				// Cancel a booking
				System.out.print("Enter booking ID to cancel: ");
				bookingId = scanner.nextInt();
				if (service.cancelBooking(bookingId)) {
					System.out.println("Booking canceled successfully!");
				} else {
					System.out.println("Failed to cancel booking.");
				}
				break;
			case 8:
				// Allocate a driver
				System.out.print("Enter trip ID to allocate a driver: ");
				tripId = scanner.nextInt();
				System.out.print("Enter driver ID: ");
				int driverId = scanner.nextInt();
				if (service.allocateDriver(tripId, driverId)) {
					System.out.println("Driver allocated successfully!");
				} else {
					System.out.println("Failed to allocate driver.");
				}
				break;
			case 9:
				// Deallocate a driver
				System.out.print("Enter trip ID to deallocate a driver: ");
				tripId = scanner.nextInt();
				if (service.deallocateDriver(tripId)) {
					System.out.println("Driver deallocated successfully!");
				} else {
					System.out.println("Failed to deallocate driver.");
				}
				break;
			case 10:
				// Get bookings by passenger
				System.out.print("Enter passenger ID: ");
				passengerId = scanner.nextInt();
				System.out.println("Bookings for passenger ID " + passengerId + ": "
						+ service.getBookingsByPassenger(passengerId));
				break;
			case 11:
				// Get available drivers
				System.out.println("Available drivers: " + service.getAvailableDrivers());
				break;
			case 12:
				// Add a route
				System.out.print("Enter route ID: ");
				routeId = scanner.nextInt();
				System.out.print("Enter start location: ");
				String startLocation = scanner.next();
				System.out.print("Enter end location: ");
				String endLocation = scanner.next();
				System.out.print("Enter distance: ");
				double distance = scanner.nextDouble();
				Route route = new Route(routeId, startLocation, endLocation, distance);
				if (service.addRoute(route)) {
					System.out.println("Route added successfully!");
				} else {
					System.out.println("Failed to add route.");
				}
				break;
			case 13:
				// Update a route
				System.out.print("Enter route ID to update: ");
				routeId = scanner.nextInt();
				System.out.print("Enter new start location: ");
				startLocation = scanner.next();
				System.out.print("Enter new end location: ");
				endLocation = scanner.next();
				System.out.print("Enter new distance: ");
				distance = scanner.nextDouble();
				Route updatedRoute = new Route(routeId, startLocation, endLocation, distance);
				if (service.updateRoute(updatedRoute)) {
					System.out.println("Route updated successfully!");
				} else {
					System.out.println("Failed to update route.");
				}
				break;
			case 14:
				// Delete a route
				System.out.print("Enter route ID to delete: ");
				routeId = scanner.nextInt();
				if (service.deleteRoute(routeId)) {
					System.out.println("Route deleted successfully!");
				} else {
					System.out.println("Failed to delete route.");
				}
				break;
			case 15:
				// Exit
				System.out.println("Exiting Transport Management System. Goodbye!");
				scanner.close();
				return;
			default:
				System.out.println("Invalid option! Please try again.");
			}
		}
	}
}
