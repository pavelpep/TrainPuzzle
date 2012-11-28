package com.trainpuzzle.model.board;

import static com.trainpuzzle.model.board.Obstacle.ObstacleType.*;



import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

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
	
	
	private static final long serialVersionUID = 1L;
	private transient Set<Observer> observerList = new HashSet<Observer>();
	public enum StationType {
		RED,
		GREEN,
		FACTORY,
		REQUESTER
	}
		
	private StationType stationType;
	private Location stationLocation;
	private CompassHeading entranceFacing;
	private Track track;
	private Obstacle stationBuilding;
	private LinkedList<Cargo> exportCargo = new LinkedList<Cargo>();
	private LinkedList<Cargo> importCargo = new LinkedList<Cargo>();
	private int numOfCargoGenerator = 0;
	private int numOfCargoRequestor = 0;
	private HashMap<CargoType, Boolean>  canGeneratCargoTypes = new HashMap<CargoType, Boolean>();
	
	
	public Station(StationType station, Location stationLocation, CompassHeading entranceFacing) {
		// entrance value should be an odd number (North, East, South, or West)
		assert(isOddNumber(entranceFacing.getValue()));	
		
		this.stationType = station;
		this.stationLocation = stationLocation;
		this.entranceFacing = entranceFacing;
		this.track = createTrack();
		this.stationBuilding=createObstacle(stationType);
		for (CargoType cargoType: CargoType.values()){
			this.canGeneratCargoTypes.put(cargoType, false);
		}
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
	
	public int getNumOfCargoGenerator() {
		return numOfCargoGenerator;
	}

	public void setNumOfCargoGenerator(int numOfCargoGenerator) {
		this.numOfCargoGenerator = numOfCargoGenerator;
	}

	public int getNumOfCargoRequestor() {
		return numOfCargoRequestor;
	}

	public void setNumOfCargoRequestor(int numOfCargoRequestor) {
		this.numOfCargoRequestor = numOfCargoRequestor;
	}
	
	public HashMap<CargoType, Boolean> getCargoTypeExist() {
		return canGeneratCargoTypes;
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
			case FACTORY:
				tempObstacle = new Obstacle(FACTORY_STATION);
				break;
			case REQUESTER:
				tempObstacle = new Obstacle(REQUESTER_STATION);
				break;			
			default:
				assert(false): "Error while handling unacceptable values for stationType";
		}
		return tempObstacle;
	}

	public void setCargo(Station station) {
		this.exportCargo = station.exportCargo;
		this.importCargo = station.importCargo;
		this.canGeneratCargoTypes = station.canGeneratCargoTypes;
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
	
	public Cargo getFirstExportCargo(){
		if (!exportCargo.isEmpty()){
			Cargo firstCargo = exportCargo.getFirst();
			sendExportCargo(firstCargo);
			return firstCargo;
		}
		return null;
	}
	
	public void sendExportCargo(Cargo cargo) {
		if (!exportCargo.contains(cargo)){		

			return;
		}
		exportCargo.removeFirstOccurrence(cargo);
		notifyAllObservers();
	}
	
	public void addExportCargo(Cargo cargo) {
		if (stationType==StationType.GREEN || stationType==StationType.RED){
			if (exportCargo.size()+importCargo.size() >= 3) return;		
		}
		else{
			if  (exportCargo.size() >= 99) 	return;
		}
		this.exportCargo.add(cargo);
		notifyAllObservers();
	}
	
	public void receiveImportCargo(Cargo cargo) {
		importCargo.removeFirstOccurrence(cargo);
		notifyAllObservers();
	}
	
		
	public void addImportCargo(Cargo cargo) {
		if (stationType==StationType.GREEN || stationType==StationType.RED){
			if (exportCargo.size()+importCargo.size() >= 3) return;		
		}
		else{
			if (importCargo.size() >=99) return;			
		}
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