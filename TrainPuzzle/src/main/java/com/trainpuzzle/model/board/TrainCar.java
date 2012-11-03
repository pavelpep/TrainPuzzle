package com.trainpuzzle.model.board;

public class TrainCar {


	private Location location; 
	private CompassHeading heading;
	
	
	public TrainCar() {
		
	}
	
	public TrainCar(Location location, CompassHeading heading) {	
		setLocation(location);
		setHeading(heading);
	}
	
	
	/*Getters and Setters */

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
}
