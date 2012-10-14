package com.trainpuzzle.model.map;


public class Tile {

	private int elevation;
	private Track track;
	private Obstacle obstacle;
	private Landscape landscape;

  /* Public Interface */

	public Tile() {	
		elevation = 0;
		landscape = new Landscape();
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
	
/* Getters and Setters */
	
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
	
	
	public String getLandscapeType() {
		return landscape.getType();
	}
	
	public void setLandscapeType(String type) {
		this.landscape.setType(type);
	}
	
}