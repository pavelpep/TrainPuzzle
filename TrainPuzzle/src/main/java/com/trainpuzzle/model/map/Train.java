package com.trainpuzzle.model.map;


public class Train {
 
	private Location location; 
	private Heading heading;
	
	/* Public Interface */
	
	public Train() {
		this.location = new Location(0,0);
	}
	
	public Train(int row, int column) {	
		this.location = new Location(row,column);
	}
	
	/*Getters and Setters */

	public Location getLocation() {
		return location;
	}

	public void setLocation(int row, int column ) {
		this.location.setRow(row);
		this.location.setColumn(column);
	}
	
	public Heading getHeading() {
		return heading;
	}
	
	public void setHeading(Heading heading) {
		this.heading = heading;
	}
}