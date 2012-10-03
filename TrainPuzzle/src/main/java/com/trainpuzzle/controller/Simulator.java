package com.trainpuzzle.controller;

import java.util.Set;

import org.apache.log4j.Logger;

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
	public static final int LATITUDE = 0;	 // x axis 
	public static final int LONGITUDE = 1; // y axis
	
	private Map map;
	private Train train;
	private Location destination;
	/* Public Interface */
	
	Simulator(Level level) {
		this.map = level.getMap();
		this.train= new Train();
		Location startPoint = level.getStartLocation();
		this.destination =level.getEndLocation();
		this.train.setLocation(startPoint.getLatitude(),startPoint.getLongitude());
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
		Tile tile = map.getTile(location.getLatitude(), location.getLongitude());
		Track track = tile.getTrack();
		
		if(!tile.hasTrack()||isOffTheMap()) {
			throw new TrainCrashException();
		} 
		getNextHeading(track,heading);
		train.setLocation(location.getLatitude(), location.getLongitude());
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
				location.setLatitude(location.getLatitude() - 1);
				location.setLongitude(location.getLongitude() - 1);
				break;
			case NORTH:
				location.setLongitude(location.getLongitude() - 1);
				break;
			case NORTHEAST:
				location.setLatitude(location.getLatitude() + 1);
				location.setLongitude(location.getLongitude() - 1);
				break;
			case EAST:
				location.setLatitude(location.getLatitude() + 1);
				break;
			case SOUTHEAST:
				location.setLatitude(location.getLatitude() + 1);
				location.setLongitude(location.getLongitude() + 1);
				break;
			case SOUTH:
				location.setLongitude(location.getLongitude() + 1);
				break;
			case SOUTHWEST: 
				location.setLatitude(location.getLatitude() - 1);
				location.setLongitude(location.getLongitude() + 1);
				break;
			case WEST:
				location.setLatitude(location.getLatitude() - 1);
				break;
		}
		
		return location;
	}
	
	/**
	 * Get where the train heading in next track and change heading stored in train
	 * 
	 * @param track the track lay on the tile the train heading to 
	 * @param heading direction the train heading now
	 * @return whether the train get into the next track successfully or not 
	 */
	private void getNextHeading(Track track, Track.CompassHeading heading) throws TrainCrashException {
		heading = heading.opposite();
		Set<Connection> connections = track.getConnections();
		for(Connection connection : connections) {
			int[] directions = connection.getHeadingValues();
			if(directions[0] == heading.getValue()) {
				heading = Track.CompassHeading.getCompassHeading(directions[1]);
				this.train.setCompassHeading(heading);
			}
			else if(directions[1] == heading.getValue()) {
				heading = Track.CompassHeading.getCompassHeading(directions[0]);
				this.train.setCompassHeading(heading);
			}
			else {
				throw new TrainCrashException();
			}
		}
		
	}
	
	private boolean isOffTheMap() {
		Location location = this.train.getLocation();
		if(location.getLatitude() >= map.getMapWidth()||location.getLongitude() >= map.getMapHeight()) {
			return true;
		}
		return false;
	}
	
	
	/* Getters and Setters */
	
/*
	 public void setTrain(Train train) {
		this.train = train;
	}
*/
	
	public Train getTrain() {
		return this.train;
	}

}