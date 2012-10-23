package com.trainpuzzle.model.board;

import static com.trainpuzzle.model.board.Obstacle.ObstacleType.*;

/**
 * A station contains a station (takes a tile to hold) and a piece of track (takes another tile to hold).
 * Station is treated as an obstacle (tracks cannot be built on it)
 * Station track is treated as a normal track (train can pass through it) but it activate the loading action
 * Station track is always a piece of straight track:
 *   if track is at the north or south of the station, it is placed horizontally
 *   else if track is at the east or west of the station, it is placed vertically
 */

public class Station {
	
	public enum StationType {
		RED_FRONT,
		RED_BACK,
		RED_LEFT,
		RED_RIGHT,
		GREEN_BACK,
		GREEN_FRONT,
		GREEN_LEFT,
		GREEN_RIGHT;
	}
	
	private StationType stationType;
	private Location stationLocation;
	private CompassHeading entranceFacing;
	private Track track;
	private Obstacle stationBuilding;
	
	
	
	/* Public Interface */
	
	public Station(StationType station, Location location, CompassHeading trackPosition) {	
		this.stationType = station;
		this.stationLocation = location;
		this.entranceFacing = trackPosition;
		this.track = createTrack();
		this.stationBuilding=new Obstacle(STATION);
		
	}
	
	/*Getters and Setters */

	public StationType getType() {
		return this.stationType;
	}

	public void setType(StationType station) {
		this.stationType = station;
	}
	
	
	public Location getLocation() {
		return stationLocation;
	}
	
	
	private Track createTrack() {
		Connection connection;
		switch(entranceFacing) {
			case EAST:
			case WEST:
				connection = new Connection(CompassHeading.NORTH, CompassHeading.SOUTH);
				break;
			case NORTH:
			case SOUTH:
			default:	// on default, set as NORTH or SOUTH
				connection = new Connection(CompassHeading.WEST, CompassHeading.EAST);
		}
		Track track = new Track(connection);
		track.setUnremoveable();
		track.setBeStationTrack();
		return track;
	}
	
	public Location getStationLocation() {
		return stationLocation;
	}
	
	public Location getTrackLocation() {
		Location trackLocation;
		int stationRow = stationLocation.getRow();
		int stationColumn = stationLocation.getColumn();
		
		switch(entranceFacing) {
			case EAST:
				trackLocation = new Location(stationRow + 1, stationColumn);
				break;
			case SOUTH:
				trackLocation = new Location(stationRow, stationColumn + 1);
				break;
			case WEST:
				trackLocation = new Location(stationRow - 1, stationColumn);
				break;
			case NORTH:
			default:	// on default, set as NORTH
				trackLocation = new Location(stationRow, stationColumn - 1);	
		}
		return trackLocation;
	}
	
	public Track getTrack() {
		return track;
	}
	
	public Obstacle getBuilding() {
		return stationBuilding;
	}
	

	/**
	 * Pass in a location and return true if it is the same as the track location
	 */
	public boolean isStationTrack(Location location) {
		return (getTrackLocation() == location);
	}
}