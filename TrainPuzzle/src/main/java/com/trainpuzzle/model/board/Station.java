package com.trainpuzzle.model.board;

import com.trainpuzzle.model.board.Obstacle.ObstacleType;

/**
 * A station contains a station (takes a tile to hold) and a piece of track (takes another tile to hold).
 * Station is treated as a obstacle (tracks cannot be built on it)
 * Station track is treated as a normal track (train can pass through it) but it activate this station object to load different types of things
 * Station track is always a piece of straight track:
 *   if track is at the north or south of the station, it is placed horizontally
 *   else if track is at the east or west of the station, it is placed vertically
 */

public class Station {
	
	public enum StationType {
		RED_FRONT,
		RED_BACK,
		GREEN_BACK,
		GREEN_FRONT;
	}
	
	private StationType stationType;
	private Location stationLocation;
	private StationTrackPosition trackPosition;
	private Track track;
	
	
	/* Public Interface */
	
	public Station(StationType station, Location location, StationTrackPosition trackPosition) {	
		this.stationType = station;
		this.stationLocation = location;
		this.trackPosition = trackPosition;
		this.track = createTrack();
		
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
		switch(trackPosition) {
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
		return track;
	}
	
	public Location getStationLocation() {
		return stationLocation;
	}
	
	public Location getTrackLocation() {
		Location trackLocation;
		int stationRow = stationLocation.getRow();
		int stationColumn = stationLocation.getColumn();
		
		switch(trackPosition) {
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
}