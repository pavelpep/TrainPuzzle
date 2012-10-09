package com.trainpuzzle.controller;

import java.util.Set;

import com.trainpuzzle.model.map.*;
import com.trainpuzzle.model.level.*;

import com.trainpuzzle.exception.TrainCrashException;
/**
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 */
public class Simulator {	
	public static final int LATITUDE = 0;	 
	public static final int LONGITUDE = 1; 
	
	private Map map;
	private Train train;
	private Location destination;
	/* Public Interface */
	
	public Simulator(Level level) {
		this.map = level.getMap();
		this.train= new Train();
		Location startPoint = level.getStartLocation();
		this.destination =level.getEndLocation();
		this.train.setLocation(startPoint.getRow(),startPoint.getColumn());
		this.train.setCompassHeading(Track.CompassHeading.EAST);
	}
	
	/**
	 * Move the train to next tile according to its heading and change its heading, too
	 * 
	 * @return whether the train goes to next tile successfully or not
	 */
	public void proceedNextTile() throws TrainCrashException {
		Location location = train.getLocation();
		Track.CompassHeading heading = train.getCompassHeading();       
		location = getNextTile(location,heading);
		if(isOffTheMap(location)) {
			throw new TrainCrashException();
		}
		
		Tile tile = map.getTile(location.getRow(), location.getColumn());
		if(!tile.hasTrack()) {
			throw new TrainCrashException();
		} 
		
		Track track = tile.getTrack();
		Track.CompassHeading nextHeading = getNextHeading(track,heading);
		this.train.setCompassHeading(nextHeading);
		train.setLocation(location.getRow(), location.getColumn());
	}
	
	public boolean isVictoryConditionsSatisfied() {
		if(destination.equals(train.getLocation())) {
			return true;
		}
		return false;
	}
	
	/* Private Functions */
	
	/**
	 * Get location that the train is heading
	 * 
	 * @param location train's current location
	 * @param headingValue where train heading to
	 * @return an int array holding latitude and longitude for next tile the is going to
	 */
	private Location getNextTile(Location location, Track.CompassHeading heading) {
		switch(heading) {
			case NORTHWEST:
				location.setRow(location.getRow() - 1);
				location.setColumn(location.getColumn() - 1);
				break;
			case NORTH:
				location.setRow(location.getRow() - 1);
				break;
			case NORTHEAST:
				location.setRow(location.getRow() - 1);
				location.setColumn(location.getColumn() + 1);
				break;
			case EAST:
				location.setColumn(location.getColumn() + 1);
				break;
			case SOUTHEAST:
				location.setRow(location.getRow() + 1);
				location.setColumn(location.getColumn() + 1);
				break;
			case SOUTH:
				location.setRow(location.getRow() + 1);
				break;
			case SOUTHWEST: 
				location.setRow(location.getRow() + 1);
				location.setColumn(location.getColumn() - 1);
				break;
			case WEST:
				location.setColumn(location.getColumn() - 1);
				break;
		}
		
		return location;
	}
	
	/**
	 * Get where the train heading in next track
	 * 
	 * @param track the track lay on the tile the train heading to 
	 * @param heading direction the train heading now
	 * @return whether the train get into the next track successfully or not 
	 */
	private Track.CompassHeading getNextHeading(Track track, Track.CompassHeading heading) throws TrainCrashException {
		Track.CompassHeading oppositeHeading = heading.opposite();
		Set<Connection> connections = track.getConnections();
		
		for(Connection connection : connections) {
			int[] headings = connection.getHeadingValues();
			
			if (headings[0] == oppositeHeading.getValue()) {
				return Track.CompassHeading.getCompassHeading(headings[1]);
			} else if (headings[1] == oppositeHeading.getValue()) {
				return Track.CompassHeading.getCompassHeading(headings[0]);
			}
		}		
		throw new TrainCrashException();
	}
	
	private boolean isOffTheMap(Location location) {
		if(location.getRow() >= map.getNumberOfRows() || location.getColumn() >= map.getNumberOfColumns()) {
			return true;
		}
		return false;
	}

	
	/* Getters and Setters */
	
	
	public Train getTrain() {
		return this.train;
	}
}