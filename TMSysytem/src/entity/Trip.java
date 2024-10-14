package entity;

public class Trip {
	private int tripId;
	private int vehicleId;
	private int routeId;
	private String departureDate;
	private String arrivalDate;
	private String status;

	// Default constructor
	public Trip() {
	}

	// Parameterized constructor
	public Trip(int tripId, int vehicleId, int routeId, String departureDate, String arrivalDate, String status) {
		this.tripId = tripId;
		this.vehicleId = vehicleId;
		this.routeId = routeId;
		this.departureDate = departureDate;
		this.arrivalDate = arrivalDate;
		this.status = status;
	}

	// Getters and Setters
	public int getTripId() {
		return tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}