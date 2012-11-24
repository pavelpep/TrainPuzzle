package com.trainpuzzle.model.board;

public class TrainCar {

	private Location location; 
	private CompassHeading heading;
	private Cargo cargo;

	public TrainCar() {
		
	}
	
	public TrainCar(Location location, CompassHeading heading) {	
		setLocation(location);
		setHeading(heading);
	}
	
	public boolean hasCargo() {
		return cargo != null;
	}
	
	public void addCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	public Cargo dropCargo() {
		Cargo currentCargo = cargo;
		cargo = null;
		
		return currentCargo;
	}

	public void resetCargo() {
		cargo = null;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public CompassHeading getHeading() {
		return heading;
	}
	
	public void setHeading(CompassHeading heading) {
		this.heading = heading;
	}	
	public Cargo getCargo() {
		return cargo;
	}
}

