package com.trainpuzzle.controller;


import javax.swing.JOptionPane;

import com.trainpuzzle.model.board.*;
import com.trainpuzzle.model.level.*;

import static com.trainpuzzle.model.board.CompassHeading.*;

import com.trainpuzzle.exception.TrainCrashException;

public class Simulator {
	private Board map;
	private Train train;
	private VictoryCondition victoryCondition;
	
	
	public Simulator(Level level) {
		this.map = level.getMap();
		Location startPoint = level.getStartLocation();
		this.train = new Train(startPoint);
		this.train.setHeading(EAST);
		this.victoryCondition = new VictoryCondition(level.getEndLocation());
	}
	
	/**
	 * Move the train to next tile according to its heading and change its heading, too
	 * 
	 * @return whether the train goes to next tile successfully or not
	 */
	public void proceedNextTile() throws TrainCrashException {
		Location location = train.getLocation();
		CompassHeading heading = train.getHeading();
		
		location = getNextTile(location,heading);
		if(isOffTheMap(location)) {
			throw new TrainCrashException();
		}
		
		Tile tile = map.getTile(location.getRow(), location.getColumn());
		if(!tile.hasTrack() || tile.hasObstacle() || tile.hasStation()) {
			// TODO: Better way to inform user train crashed
			JOptionPane.showMessageDialog(null, "The train has crashed!");
			throw new TrainCrashException();
		} 
		
		Track track = tile.getTrack();
		
		CompassHeading nextHeading = track.getOutboundHeading(heading);
		this.train.setHeading(nextHeading);
		//this.train.setLocation(location);
		victoryCondition.removePassedLocation(train);
	}
	
	public boolean isVictoryConditionsSatisfied() {
		return this.victoryCondition.isVictoryConditionSatisfied(train);
	}
	
	/**
	 * Get location that the train is heading
	 * 
	 * @param location train's current location
	 * @param headingValue where train heading to
	 * @return an int array holding latitude and longitude for next tile the is going to
	 */
	private Location getNextTile(Location location, CompassHeading heading) {
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
	
	private boolean isOffTheMap(Location location) {
		if(location.getRow() >= Board.NUMBER_OF_ROWS || location.getColumn() >= Board.NUMBER_OF_COLUMNS) {
			return true;
		}
		return false;
	}	
	
	public Train getTrain() {
		return this.train;
	}
}