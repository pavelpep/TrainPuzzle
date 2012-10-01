package com.trainpuzzle.model.map;

/**
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 *
 */

public class Train {
 
	private int[] location; 
	private Track.Heading heading;
	
	public Train() {
		this.location = new int[2];
		this.location[0] = 0;
		this.location[1] = 0;
	}
	
	public Train(int latitude, int longitude) {
		this.location = new int[2];
		this.location[0] = latitude;
		this.location[1] = longitude;
	}

	public int[] getLocation() {
		return location;
	}

	public void setLocation(int latitude, int longitude ) {
		this.location[0] = latitude;
		this.location[1] = longitude;
	}
	
	public Track.Heading getHeading() {
		return heading;
	}
	
	public void setHeading(Track.Heading heading) {
		this.heading = heading;
	}
}