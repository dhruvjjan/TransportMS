package entity;

public class Vehicle {
	private int vehicleId;
	private String model;
	private double capacity;
	private String type;
	private String status;

	// Default constructor
	public Vehicle() {
	}

	// Parameterized constructor
	public Vehicle(int vehicleId, String model, double capacity, String type, String status) {
		this.vehicleId = vehicleId;
		this.model = model;
		this.capacity = capacity;
		this.type = type;
		this.status = status;
	}

	// Getters and Setters
	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}