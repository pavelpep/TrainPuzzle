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
	private Track.CompassHeading heading;
	
	/* Public Interface */
	
	public Train() {
		this.location = new Location(0,0);
	}
	
	public Train(int latitude, int longitude) {	
		this.location = new Location(latitude,longitude);
	}
	
	/*Getters and Setters */

	public Location getLocation() {
		return location;
	}

	public void setLocation(int latitude, int longitude ) {
		this.location.setLatitude(latitude);
		this.location.setLongitude(longitude);
	}
	
	public Track.CompassHeading getCompassHeading() {
		return heading;
	}
	
	public void setCompassHeading(Track.CompassHeading heading) {
		this.heading = heading;
	}
}