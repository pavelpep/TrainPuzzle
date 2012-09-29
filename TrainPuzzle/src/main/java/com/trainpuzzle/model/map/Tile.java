package com.trainpuzzle.model.map;

/**
 * The Tile class is essentially a super class which Tile type class's inherit from.
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 *
 */

public class Tile {

private int elevation;
private Track track;
private Obstacle obstacle;
private Landscape landscape;
private Location location;

	public Tile() {	
		elevation = 0;
		landscape.setName("grass");
	}

	public int getElevation() {
		return elevation;
	}

	public void setElevation(int elevation) {
		this.elevation = elevation;
	}
	
	public void putTrack() {
		track = new Track();
	}
	
	public void removeTrack() {
		track = null;
	}

	public boolean hasTrack() {
		if(track == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean hasObstacle() {
		if(obstacle == null) {
			return false;
		}
		else {
			return true;
		}	
	}
}