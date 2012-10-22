package com.trainpuzzle.controller;


import javax.swing.JOptionPane;

import com.trainpuzzle.model.board.*;
import com.trainpuzzle.model.level.*;

import static com.trainpuzzle.model.board.CompassHeading.*;

import com.trainpuzzle.exception.TrainCrashException;

public class Simulator {
	private Level level;
	private Board board;
	private Train train;
	private VictoryConditionOld victoryConditionOld;
	
	
	public Simulator(Level level) {
		this.level = level;
		this.board = this.level.getBoard();
		this.train = new Train();

		initializeSimulator();
	}

	private void initializeSimulator() {
		Location startPoint = new Location(this.level.getStartLocation());
		this.train.setLocation(startPoint);
		this.train.setHeading(EAST);
		this.victoryConditionOld = new VictoryConditionOld(this.level.getEndLocation());
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
		
		Tile tile = board.getTile(location.getRow(), location.getColumn());
		if(!tile.hasTrack() || tile.hasObstacle() || tile.hasStation(location)) {
			// TODO: Better way to inform user train crashed
			JOptionPane.showMessageDialog(null, "The train has crashed!");
			throw new TrainCrashException();
		} 
		
		Track track;
		if(tile.hasStationTrack(location)){
			track = tile.getStation().getTrack();
		} else {
			track = tile.getTrack();
		}
			
		CompassHeading nextHeading = track.getOutboundHeading(heading);
		this.train.setHeading(nextHeading);
		//this.train.setLocation(location);
		victoryConditionOld.removePassedLocation(train);
	}
	
	public boolean isVictoryConditionsSatisfied() {
		return this.victoryConditionOld.isVictoryConditionSatisfied(train);
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
		if(location.getRow() >= board.NUMBER_OF_ROWS || location.getColumn() >= board.NUMBER_OF_COLUMNS) {
			return true;
		}
		return false;
	}	
	
	public Train getTrain() {
		return this.train;
	}

	public void reset() {
		initializeSimulator();
	}
}