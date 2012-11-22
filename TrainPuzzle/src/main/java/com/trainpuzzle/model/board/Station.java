package com.trainpuzzle.model.board;

import static com.trainpuzzle.model.board.Obstacle.ObstacleType.*;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import org.apache.log4j.Logger;

import com.trainpuzzle.model.board.Cargo.CargoType;
import com.trainpuzzle.observe.Observable;
import com.trainpuzzle.observe.Observer;

/**
 * A station contains a station (takes a tile to hold) and a piece of track (takes another tile to hold).
 * Station is treated as an obstacle (tracks cannot be built on it)
 * Station track is treated as a special track (Station Track - train can pass through it) but it activate the loading action
 * Station track is always a piece of straight track:
 *   if track is at the north or south of the station, it is placed horizontally
 *   else if track is at the east or west of the station, it is placed vertically
 */

public class Station implements java.io.Serializable, Observable {
	
	private Logger logger = Logger.getLogger(Station.class);
	private static final long serialVersionUID = 1L;
	private transient Set<Observer> observerList = new HashSet<Observer>();
	
	public enum StationType {
		RED,
		GREEN,
		IRON_FACTORY,
		WOOD_FACTORY,
		COTTON_FACTORY
	}
		
	private StationType stationType;
	private Location stationLocation;
	private CompassHeading entranceFacing;
	private Track track;
	private Obstacle stationBuilding;
	private LinkedList<Cargo> exportCargo = new LinkedList<Cargo>();
	private LinkedList<Cargo> importCargo = new LinkedList<Cargo>();
	
	public Station(StationType station, Location stationLocation, CompassHeading entranceFacing) {
		// entrance value should be an odd number (North, East, South, or West)
		assert(isOddNumber(entranceFacing.getValue()));	
		
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
	
	private Obstacle createObstacle(StationType stationType) {
		Obstacle tempObstacle = null;
		switch(stationType) {
			case GREEN:
				tempObstacle = new Obstacle(GREEN_STATION);
				break;
			case RED:
				tempObstacle = new Obstacle(RED_STATION);
				break;
			case IRON_FACTORY:
				tempObstacle = new Obstacle(IRON_FACTORY_STATION);
				break;
			default:
				assert(false): "Error while handling unacceptable values for stationType";
		}
		return tempObstacle;
	}

	public void setCargo(Station station) {
		this.exportCargo = station.exportCargo;
		this.importCargo = station.importCargo;
		notifyAllObservers();
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
	
	public boolean hasExportCargo() {
		return exportCargo.size() > 0;
	}
	
	public boolean hasImportCargo() {
		return importCargo.size() > 0;
	}
	
	public LinkedList<Cargo> getExportCargo() {
		return this.exportCargo;	
	}
	
	public LinkedList<Cargo> getImportCargo() {
		return importCargo;	
	}	
	
	public void sendExportCargo(Cargo cargo) {
		logger.debug("Train Received Cargo from Station");
		assert exportCargo.size() > 0;
		exportCargo.removeFirstOccurrence(cargo);		
		notifyAllObservers();
	}
	
	public void receiveImportCargo(Cargo cargo) {
		logger.debug("Station Received Cargo from Train");
		importCargo.removeFirstOccurrence(cargo);
		notifyAllObservers();
	}
	
	public void addExportCargo(Cargo cargo) {
		if  (exportCargo.size() < 100) {
			this.exportCargo.add(cargo);
		}
		notifyAllObservers();
	}
		
	public void addImportCargo(Cargo cargo) {
		assert importCargo.size() < 2 : "Cargo types can be up to 2";		
		this.importCargo.add(cargo);
		notifyAllObservers();
	}
	
	@Override
	public boolean equals(Object obj) {
		return stationLocation.equals(((Station) obj).getStationLocation());
	}

	@Override
	public void register(Observer observer) {
		if(observerList == null) {
			observerList = new HashSet<Observer>();
		}
		observerList.add(observer);	
	}

	@Override
	public void notifyAllObservers() {
		for(Observer observer : observerList) {
			observer.notifyChange(this);
		}
	}	
}