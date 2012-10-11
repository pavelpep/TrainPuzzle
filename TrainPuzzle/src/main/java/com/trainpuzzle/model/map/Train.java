package com.trainpuzzle.model.map;

/**
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 *
 */

public class Train {
 
	private Location location; 
	private CompassHeading heading;
	
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
	
	public CompassHeading getCompassHeading() {
		return heading;
	}
	
	public void setCompassHeading(CompassHeading heading) {
		this.heading = heading;
	}
}