package com.trainpuzzle.model.map;

/**
 * The Tile class represents a square on the gameboard.
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

	/* 
	 * **************
	 * Constructors	*
	 * **************
	 */

	public Tile() {	
		elevation = 0;
		landscape.setName("grass");
	}

	/*
	 * *******************
	 * Getters & Setters *
	 * *******************
	 */
	
	public int getElevation() {
		return elevation;
	}

	public void setElevation(int elevation) {
		this.elevation = elevation;
	}
	
	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
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
	
	public void removeTrack() {
		track = null;
	}
}