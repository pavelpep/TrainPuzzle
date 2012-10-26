package com.trainpuzzle.model.board;

import static com.trainpuzzle.model.board.Obstacle.ObstacleType.*;

/**
 * A station contains a station (takes a tile to hold) and a piece of track (takes another tile to hold).
 * Station is treated as an obstacle (tracks cannot be built on it)
 * Station track is treated as a special track (Station Track - train can pass through it) but it activate the loading action
 * Station track is always a piece of straight track:
 *   if track is at the north or south of the station, it is placed horizontally
 *   else if track is at the east or west of the station, it is placed vertically
 */

public class Station implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	public enum StationType {
		RED,
		GREEN;
	}
	
	private StationType stationType;
	private Location stationLocation;
	private CompassHeading entranceFacing;
	private Track track;
	private Obstacle stationBuilding;
	
	/* Public Interface */
	
	public Station(StationType station, Location stationLocation, CompassHeading entranceFacing) {
		assert(isOddNumber(entranceFacing.getValue()));	// entrance value should be an odd number (North, East, South, or West)
		
		this.stationType = station;
		this.stationLocation = stationLocation;
		this.entranceFacing = entranceFacing;
		this.track = createTrack();
		this.stationBuilding=createObstacle(stationType);
	}
	
	private boolean isOddNumber(int value) {
		return (value % 2 == 1);
	}
	
	/*Getters and Setters */

	public StationType getType() {
		return this.stationType;
	}

	public void setType(StationType station) {
		this.stationType = station;
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
	
	private Obstacle createObstacle(StationType stationType){
		Obstacle tempObstacle;
		switch(stationType) {
			case GREEN:
				tempObstacle = new Obstacle(GREEN_STATION);
				break;
			case RED:
			default:
				tempObstacle = new Obstacle(RED_STATION);
				break;
		}
		return tempObstacle;
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
				trackLocation = new Location(stationRow, stationColumn + 1);
				break;
			case SOUTH:
				trackLocation = new Location(stationRow + 1, stationColumn);
				break;
			case WEST:
				trackLocation = new Location(stationRow, stationColumn -1);
				break;
			case NORTH:
			default:	// on default, set as NORTH
				trackLocation = new Location(stationRow - 1, stationColumn);	
		}
		return trackLocation;
	}
	
	public Track getTrack() {
		return track;
	}
	
	public Obstacle getBuilding() {
		return stationBuilding;
	}
}