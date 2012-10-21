package com.trainpuzzle.model.map;


public class Tile {

	private int elevation;
	private Track track;
	private Obstacle obstacle;
	private Landscape landscape;
	private Station station;

  /* Public Interface */

	public Tile() {	
		elevation = 0;
		landscape = new Landscape(Landscape.LandscapeType.GRASS);
	}

	public void removeTrack() {
		track = null;
	}
	
	public boolean hasTrack() {
		return (track != null);
	}	
	
	public boolean hasObstacle() {
		return (obstacle != null);
	}
	
	public boolean hasStation() {
		return (station != null);
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
	
	public void rotateTrack() {
		this.track.rotateTrack();
	}
	
	public Landscape.LandscapeType getLandscapeType() {
		return landscape.getType();
	}
	
	public void setLandscapeType(Landscape.LandscapeType type) {
		this.landscape.setType(type);
	}
	
	public Station getStation() {
		return station;
	}
	
	public void setStation(Station station) {
		this.station = station;
	}
	
	public Station.StationType getStationType() {
		return station.getType();
	}

	public void setObstacle(Obstacle obstacle) {
		this.obstacle = obstacle;
	}
	
	public Obstacle.ObstacleType getObstacleType() {
		return obstacle.getType();
	}
	
}