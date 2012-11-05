package com.trainpuzzle.controller;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;


import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.board.Tile;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.board.Train;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.level.victory_condition.Event;
import com.trainpuzzle.model.level.victory_condition.VictoryConditionEvaluator;

import static com.trainpuzzle.model.board.CompassHeading.*;
import com.trainpuzzle.exception.TrainCrashException;

public class Simulator {
	private Level level;
	private Board board;
	private Train train;
	private VictoryConditionEvaluator victoryConditionEvaluator;
	
	private boolean trainCrashed = false;
    private boolean isRunning = false;
    
    private Timer timer = new Timer();
    private final int defaultTickInterval = 200;
    private final int tickIntervalLowerBound = 50;
    private final int tickIntervalUpperBound = 1000;
    private int tickInterval = defaultTickInterval;
    
	public Simulator(Level level) {
		this.level = level;
		this.board = this.level.getBoard();
		

		initializeSimulator();
	}

	private void initializeSimulator() {
		Location startPoint = new Location(this.level.getStartLocation());
		this.train = new Train(startPoint,EAST);
		//this.victoryConditionOld = new VictoryConditionOld(this.level.getEndLocation());
		this.victoryConditionEvaluator = new VictoryConditionEvaluator(level.getVictoryConditions());
		trainCrashed = false;
	}
	
	/**
	 * Move the train to next tile according to its heading and change its heading, too
	 * 
	 * @return whether the train goes to next tile successfully or not
	 */
	
	public void move() {
    	try {
    		if (!isVictoryConditionsSatisfied()) {
    			proceedNextTile();
    		}
		} catch (TrainCrashException e) {
			e.printStackTrace();
			trainCrashed = true;
			//JOptionPane.showMessageDialog(null, "The train has crashed!");
		}
	}
	
	public void proceedNextTile() throws TrainCrashException {
	
		Location location = train.getLocation();
		CompassHeading heading = train.getHeading();
		Location nextLocation = getNextLocation(location,heading);
		Tile tile = getTileWithTrack(nextLocation);
		Track track = tile.getTrack();
		heading = track.getOutboundHeading(heading);
		this.train.setLocation(nextLocation);
		this.train.setHeading(heading);
		if(tile.hasStationTrack()) {
			Station station = tile.getStation();
			passStation(station);
		}
	
	}

	private Tile getTileWithTrack(Location location) throws TrainCrashException {
		if(isOffTheMap(location)) {
			throw new TrainCrashException();
		}
		
		Tile tile = board.getTile(location.getRow(), location.getColumn());
		if(!tile.hasTrack() || tile.hasObstacle()) {
			// TODO: Better way to inform user train crashed
			JOptionPane.showMessageDialog(null, "The train has crashed!");
			throw new TrainCrashException();
		}
		return tile;
	}
	
	public boolean isVictoryConditionsSatisfied() {
		return this.victoryConditionEvaluator.isSatisfied();
	}
	
	private Location getNextLocation(Location location, CompassHeading heading) {
		Location nextLocation = new Location(location);
		switch(heading) {
			case NORTHWEST:
				nextLocation.setRow(location.getRow() - 1);
				nextLocation.setColumn(location.getColumn() - 1);
				break;
			case NORTH:
				nextLocation.setRow(location.getRow() - 1);
				break;
			case NORTHEAST:
				nextLocation.setRow(location.getRow() - 1);
				nextLocation.setColumn(location.getColumn() + 1);
				break;
			case EAST:
				nextLocation.setColumn(location.getColumn() + 1);
				break;
			case SOUTHEAST:
				nextLocation.setRow(location.getRow() + 1);
				nextLocation.setColumn(location.getColumn() + 1);
				break;
			case SOUTH:
				nextLocation.setRow(location.getRow() + 1);
				break;
			case SOUTHWEST: 
				nextLocation.setRow(location.getRow() + 1);
				nextLocation.setColumn(location.getColumn() - 1);
				break;
			case WEST:
				nextLocation.setColumn(location.getColumn() - 1);
				break;
		}
		return nextLocation;
	}
	
	private boolean isOffTheMap(Location location) {
		return (location.getRow() >= board.rows || location.getColumn() >= board.columns);
	}	
	
	private void passStation(Station station) {
		//String name = "pass station "+station.hashCode();
		Event event = new Event(100, station);
		this.victoryConditionEvaluator.processEvent(event);
	}
	
	public boolean isTrainCrashed() {
		return trainCrashed;
	}
	
	public Train getTrain() {
		return this.train;
	}
	
    
	public void reset() {
		timer.cancel();
		isRunning = false;
		Location startPoint = new Location(this.level.getStartLocation());
		this.train.setLocation(startPoint);
		this.train.setHeading(EAST);
		this.train.resetTrainCars();
		this.victoryConditionEvaluator.resetEvents();
		trainCrashed=false;
	}
	
	public void stop() {
		timer.cancel();
		isRunning = false;
	}
	
	public void run() {
		timer.cancel();
		timer = new Timer();
		
		TimerTask timerTask = new TimerTask() {
	 		
			public void run() {
	        	move();
	 			
	        	if(isVictoryConditionsSatisfied() || trainCrashed) {
	        		this.cancel();
	        		isRunning = false;
	        	}
			}
	    };
	    
		timer.schedule(timerTask, 0, tickInterval);
		isRunning = true;
	}
	
	public void setTickInterval(int tickIntervalInMillis) {
	    this.tickInterval = tickIntervalInMillis;
	    if(isRunning){
	    	run();
	    }
	}
	
	public int getTickInterval() {
		return tickInterval;
	}
	
	public int getDefaultTickInterval() {
		return defaultTickInterval;
	}
	
	public int getTickIntervalLowerBound() {
		return tickIntervalLowerBound;
	}
	
	public int getTickIntervalUpperBound() {
		return tickIntervalUpperBound;
	}
    
}