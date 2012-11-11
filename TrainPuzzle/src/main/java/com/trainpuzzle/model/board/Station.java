package com.trainpuzzle.model.board;

import static com.trainpuzzle.model.board.Obstacle.ObstacleType.*;

import java.util.LinkedList;

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
	
	// TODO: create load type
	
	private StationType stationType;
	private Location stationLocation;
	private CompassHeading entranceFacing;
	private Track track;
	private Obstacle stationBuilding;
	private LinkedList<Cargo> extraCargo = new LinkedList<Cargo>();
	private LinkedList<Cargo> requiredCargo = new LinkedList<Cargo>();
	
	
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
	
	public StationType getType() {
		return this.stationType;
	}

	public void setType(StationType stationType) {
		this.stationType = stationType;
	}
	
	private Track createTrack() {
		Connection connection = null;
		switch(entranceFacing) {
			case EAST:	// FALLTHROUGH
			case WEST:
				connection = new Connection(CompassHeading.NORTH, CompassHeading.SOUTH);
				break;
			case NORTH:	// FALLTHROUGH
			case SOUTH:
				connection = new Connection(CompassHeading.EAST, CompassHeading.WEST);
				break;
			default:
				assert(false): "Error while handling unacceptable values for entranceFacing";
				
		}
		Track track = new Track(connection, TrackType.STRAIGHT_TRACK);
		track.setUnremoveable();
		track.setToBeStationTrack();
		return track;
	}
	
	private Obstacle createObstacle(StationType stationType){
		Obstacle tempObstacle = null;
		switch(stationType) {
			case GREEN:
				tempObstacle = new Obstacle(GREEN_STATION);
				break;
			case RED:
				tempObstacle = new Obstacle(RED_STATION);
				break;
			default:
				assert(false): "Error while handling unacceptable values for stationType";
		}
		return tempObstacle;
	}

	
	public Location getStationLocation() {
		return stationLocation;
	}
	
	public Location getTrackLocation() {
		Location trackLocation = null;
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
				trackLocation = new Location(stationRow - 1, stationColumn);
				break;
			default:
				assert(false): "Error while handling unacceptable values for entranceFacing";
		}
		return trackLocation;
	}
	
	public Track getTrack() {
		return track;
	}
	
	public Obstacle getBuilding() {
		return stationBuilding;
	}	
	
	public boolean hasExtraCargo() {
		return extraCargo.size() > 0;
	}
	
	public boolean hasRequiredCargo() {
		return requiredCargo.size() > 0;
	}
	
	public LinkedList<Cargo> getExportCargo() {
		return this.extraCargo;	
	}
	
	public void setExtraCargo(LinkedList<Cargo> extraCargo) {
		this.extraCargo = extraCargo;
	}

	public LinkedList<Cargo> getImportCargo() {
		return requiredCargo;	
	}	
	
	public Cargo pickupExtraCargo() {
		assert extraCargo.size() > 0;
		
		return extraCargo.pollFirst();		
	}
	
	public void setRequiredCargo(LinkedList<Cargo> requiredCargo) {
		this.requiredCargo = requiredCargo;
	}

	public void addExtraCargo(Cargo cargo) {
		assert extraCargo.size() < 2 : "Cargo types can be up to 2";
		
		this.extraCargo.add(cargo);
	}
	
	public void addRequiredCargo(Cargo Cargo) {
		assert requiredCargo.size() < 2 : "Cargo types can be up to 2";		
		
		this.requiredCargo.add(Cargo);
	}

	public boolean isRequiredCargo(Cargo cargo) {
		return requiredCargo.contains(cargo);
	}
	
	public void dropoffRequiredCargo(Cargo cargo)  {
		requiredCargo.removeFirstOccurrence(cargo);
	}
	
	@Override
	public boolean equals(Object obj) {
		return stationLocation.equals(((Station) obj).getStationLocation());
	}
	
	
}