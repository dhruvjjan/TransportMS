package entity;

public class Booking {
	private int bookingId;
	private int tripId;
	private int passengerId;
	private String bookingDate;

	// Default constructor
	public Booking() {
	}

	// Parameterized constructor
	public Booking(int bookingId, int tripId, int passengerId, String bookingDate) {
		this.bookingId = bookingId;
		this.tripId = tripId;
		this.passengerId = passengerId;
		this.bookingDate = bookingDate;
	}

	// Getters and Setters
	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getTripId() {
		return tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public int getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(int passengerId) {
		this.passengerId = passengerId;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
}