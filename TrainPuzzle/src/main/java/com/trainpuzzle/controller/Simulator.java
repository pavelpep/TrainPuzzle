package com.trainpuzzle.controller;

import java.util.Timer;
import java.util.TimerTask;

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
		this.train = new Train();

		initializeSimulator();
	}

	private void initializeSimulator() {
		Location startPoint = new Location(this.level.getStartLocation());
		this.train.setLocation(startPoint);
		this.train.setHeading(EAST);
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
			proceedNextTile();
		} catch (TrainCrashException e) {
			e.printStackTrace();
			trainCrashed = true;
			//JOptionPane.showMessageDialog(null, "The train has crashed!");
		}
	}
	
	public void proceedNextTile() throws TrainCrashException {
		Location location = train.getLocation();
		CompassHeading heading = train.getHeading();
		
		location = getNextTile(location,heading);
		if(isOffTheMap(location)) {
			throw new TrainCrashException();
		}
		
		Tile tile = board.getTile(location.getRow(), location.getColumn());
		if(!tile.hasTrack() || tile.hasObstacle()) {
			// TODO: Better way to inform user train crashed
			//JOptionPane.showMessageDialog(null, "The train has crashed!");
			throw new TrainCrashException();
		}
		
		Track track = tile.getTrack();
		CompassHeading nextHeading = track.getOutboundHeading(heading);
		if (tile.hasStationTrack()) {
			Station station = tile.getStation();
			passStation(station);
		}
		this.train.setHeading(nextHeading);
		//victoryConditionOld.removePassedLocation(train);
	}
	
	public boolean isVictoryConditionsSatisfied() {
		return this.victoryConditionEvaluator.isSatisfied();
		//return this.victoryConditionOld.isVictoryConditionSatisfied(train);
	}
	
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
		return (location.getRow() >= board.NUMBER_OF_ROWS || location.getColumn() >= board.NUMBER_OF_COLUMNS);
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
		initializeSimulator();
		this.victoryConditionEvaluator.resetEvents();
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