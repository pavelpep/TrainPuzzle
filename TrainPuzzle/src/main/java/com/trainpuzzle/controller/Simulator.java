package com.trainpuzzle.controller;

import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.Cargo;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.board.Tile;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.board.Train;
import com.trainpuzzle.model.board.TrainCar;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.level.victory_condition.DropCargoEvent;
import com.trainpuzzle.model.level.victory_condition.Event;
import com.trainpuzzle.model.level.victory_condition.VictoryConditionEvaluator;
import com.trainpuzzle.observe.Observable;
import com.trainpuzzle.observe.Observer;

import static com.trainpuzzle.model.board.CompassHeading.*;
import com.trainpuzzle.exception.TrainCrashException;

public class Simulator implements Observable{
	
	private transient Set<Observer> observerList = new HashSet<Observer>();
	
	private Level level;
	private Board board;
	private Train train;
	private VictoryConditionEvaluator victoryConditionEvaluator;
	
	private boolean trainCrashed = false;
	private boolean isRunning = false;

	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
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
		this.victoryConditionEvaluator = new VictoryConditionEvaluator(level.getVictoryConditions());
		trainCrashed = false;
	}
	
	
	public void reset() {
		isRunning = false;
		Location startPoint = new Location(this.level.getStartLocation());
		this.train.setLocation(startPoint);
		this.train.setHeading(EAST);
		this.train.resetTrainCars();
		this.victoryConditionEvaluator.resetEvents();
		trainCrashed=false;
		stop();
	}
	
	public void stop() {
		executor.shutdownNow();
		isRunning = false;
		notifyAllObservers();
		System.out.println("STOPPED");
	}
	
	public void run(){
		executor.shutdownNow();
		executor = Executors.newSingleThreadScheduledExecutor();
		SimulatorTimer simulatorTimer = new SimulatorTimer(this);
		executor.scheduleAtFixedRate(simulatorTimer, 0, tickInterval, TimeUnit.MILLISECONDS);
		isRunning = true;
	}
	
	public void move() throws TrainCrashException{
		try {
			if (!isVictoryConditionsSatisfied()) {
				proceedNextTile();
			}
			else if(isVictoryConditionsSatisfied()) {
				stop();
			}
		} catch (TrainCrashException e) {
			e.printStackTrace();
			trainCrashed = true;
			stop();
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
	
	private void passStation(Station station) {
		Event event = new Event(100, station);
		this.victoryConditionEvaluator.processEvent(event);
		dropAndPickCargo(station);
		
	}

	private void dropAndPickCargo(Station station) {
		//For this iteration, to simplify the case, just use one cars to load all cargoes because of unawareness of 
		//how many cargoes can be loaded in a cars and assuming that there is no cargo limits for a car
		TrainCar[] trainCars = train.getTrainCars();
		LinkedList<Cargo> cargoStillWanted = null;
		for(TrainCar trainCar : trainCars) {
			if(trainCar.hasCargo() && !station.getrequiredCargo().equals(null)) {
				cargoStillWanted = trainCar.unloadCaroges(station.getrequiredCargo());
				station.setRequiredCargo(cargoStillWanted);
			}
			if(station.hasExtraCargo()) {
				trainCar.loadCargoes(station.getExtraCargo());
				station.setExtraCargo(null);
			}
		}
	}

/*	private void dropCargo(Station station, TrainCar trainCar) {
		Cargo cargo = trainCar.dropCargo();
		station.dropoffRequiredCargo(cargo);
		DropCargoEvent event = new DropCargoEvent(100,station,cargo);
		this.victoryConditionEvaluator.processEvent(event);
	}*/
	
	public boolean isTrainCrashed() {
		return trainCrashed;
	}
	
	public Train getTrain() {
		return this.train;
	}
	
	public VictoryConditionEvaluator getVictoryConditionEvaluator() {
		return victoryConditionEvaluator;
	}

	public void register(Observer observer){
		if(observerList == null) {
    	  observerList = new HashSet<Observer>();
		}
		observerList.add(observer);
	}
		
	public void notifyAllObservers() {
		for(Observer observer : observerList) {
			observer.notifyChange(this);
		}
	} 
}