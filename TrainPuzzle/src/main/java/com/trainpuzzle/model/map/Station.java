package com.trainpuzzle.model.map;


public class Station {
	
	private Location location;
	private CompassHeading entranceFacing;
	
	
	/* Public Interface */

	
	public Station(Location location) {	
		setLocation(location);
	}
	
	/*Getters and Setters */

	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public CompassHeading getEntranceFacing() {
		return entranceFacing;
	}
	
	public void setEntranceFacing(CompassHeading entranceFacing) {
		this.entranceFacing = entranceFacing;
	}
	
	
	
	
}