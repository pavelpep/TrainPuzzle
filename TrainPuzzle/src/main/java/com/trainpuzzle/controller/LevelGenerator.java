package com.trainpuzzle.controller;

import com.trainpuzzle.model.board.*;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Connection;
import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Obstacle;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.board.Landscape.LandscapeType;
import com.trainpuzzle.model.board.Station.StationType;
import com.trainpuzzle.model.level.Economy;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.level.victory_condition.AndVictoryCondition;
import com.trainpuzzle.model.level.victory_condition.Event;
import com.trainpuzzle.model.level.victory_condition.LeafVictoryCondition;
import com.trainpuzzle.model.level.victory_condition.VictoryConditionEvaluator;

//purposely created without the public tag because we only want this class accessible by the CampaignManager (which is in the same package)
class LevelGenerator {

    

    public LevelGenerator() {
    }
    
    /*
     * **********************************
     * Hardcoded functions for Level 1	*
     * **********************************
     */
    
    public Level createLevelOne() {
    	Level level;
    	Board board = new Board();
    	
        Location startLocation = new Location(4,0);
		Track startingTrack = new Track();
		startingTrack.setUnremoveable();
		board.getTile(startLocation).setTrack(startingTrack);
		
        Location endLocation = new Location(4,19);
        Track endingTrack = new Track();
        endingTrack.setUnremoveable();
		board.getTile(endLocation).setTrack(endingTrack);
        Economy economy = new Economy();
        
		addStationOnTiles(board);
		addSomeWaterTiles(board);
		addSomeTrackTiles(board);
		addSomeObstacles(board);
		
    	level = new Level(1, board, startLocation, endLocation, economy);
    	setVictoryConditions(level, endLocation);
    	return level;
	}
    
    public Level createLevelTwo() {
    	Level level;
    	Board board = new Board();
        Location startLocation = new Location(4,4);
        Track startingTrack = new Track();
		startingTrack.setUnremoveable();
		board.getTile(startLocation).setTrack(startingTrack);
		
		
		addTrackLoop(board);
				
        Location endLocation = new Location(4,19);
        Track endingTrack = new Track();
        endingTrack.setUnremoveable();
		board.getTile(endLocation).setTrack(endingTrack);
		
        Economy economy = new Economy();
       		
		
    	level = new Level(2, board, startLocation, endLocation, economy);
    	setVictoryConditions(level, endLocation);
    	return level;
	}

	private void initializeBoard(Board board) {
       //TODO: add any initialization if necessary
	   // but i'm pretty sure board initializes itself
	}
	
	private void setVictoryConditions(Level level, Location endLocation) {
		AndVictoryCondition victoryConditions = new AndVictoryCondition();
		//TODO: ensure endStation is correct, also add another station in between
		//possibly need to add start location to victory conditions
		
		Event endStation = new Event(1, new Station(StationType.RED_FRONT, endLocation, CompassHeading.NORTH));
		
		victoryConditions.addChild(new LeafVictoryCondition(endStation));
		
		level.setVictoryConditions(victoryConditions);
	}
	
	private void addStationOnTiles(Board board) {
		Location templocation = new Location (8,4);
		Station station=new Station(StationType.GREEN_FRONT, templocation, CompassHeading.SOUTH);
		Tile tempTile = board.getTile(templocation);
		tempTile.setStationBuilding(station);
		
		templocation = station.getTrackLocation();
		tempTile = board.getTile(templocation);
		Track track=station.getTrack();
		tempTile.setTrack(track);
		
		
		board.getTile(4, 3).setStationBuilding(new Station(StationType.RED_FRONT, new Location(4,3), CompassHeading.SOUTH));
	}
	
	private void addSomeWaterTiles(Board board) {
		board.getTile(10, 10).setLandscapeType(LandscapeType.WATER);
		board.getTile(5, 5).setLandscapeType(LandscapeType.WATER);
		board.getTile(2, 3).setLandscapeType(LandscapeType.WATER);	
	}
	
	private void addSomeObstacles(Board board) {
		
		board.getTile(7, 7).setObstacle(new Obstacle(Obstacle.ObstacleType.TREES));
		board.getTile(7, 8).setObstacle(new Obstacle(Obstacle.ObstacleType.ROCK));
	}
	
	private void addTrackLoop(Board board) {

		board.getTile(4,4).setTrack(new Track());
		board.getTile(4,5).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.EAST)));
		board.getTile(4,6).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.EAST)));
		board.getTile(4,7).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.EAST)));
		board.getTile(4,8).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.EAST)));
		board.getTile(4,9).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.EAST)));
		board.getTile(4,10).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.SOUTHEAST)));
		board.getTile(5,11).setTrack(new Track(new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTH)));
		board.getTile(6,11).setTrack(new Track(new Connection(CompassHeading.SOUTH, CompassHeading.NORTH)));
		board.getTile(7,11).setTrack(new Track(new Connection(CompassHeading.SOUTH, CompassHeading.NORTH)));
		board.getTile(8,11).setTrack(new Track(new Connection(CompassHeading.SOUTHWEST, CompassHeading.NORTH)));
		board.getTile(9,10).setTrack(new Track(new Connection(CompassHeading.NORTHEAST, CompassHeading.WEST)));
		board.getTile(9,9).setTrack(new Track());
		board.getTile(9,8).setTrack(new Track());
		board.getTile(9,7).setTrack(new Track());
		board.getTile(9,6).setTrack(new Track());
		board.getTile(9,5).setTrack(new Track());
		board.getTile(9,4).setTrack(new Track());
		board.getTile(9,3).setTrack(new Track(new Connection(CompassHeading.NORTHWEST, CompassHeading.EAST)));
		board.getTile(8,2).setTrack(new Track(new Connection(CompassHeading.NORTH, CompassHeading.SOUTHEAST)));
		board.getTile(7,2).setTrack(new Track(new Connection(CompassHeading.NORTH, CompassHeading.SOUTH)));
		board.getTile(6,2).setTrack(new Track(new Connection(CompassHeading.NORTH, CompassHeading.SOUTH)));
		board.getTile(5,2).setTrack(new Track(new Connection(CompassHeading.NORTHEAST, CompassHeading.SOUTH)));
		board.getTile(4,3).setTrack(new Track(new Connection(CompassHeading.SOUTHWEST, CompassHeading.EAST)));
	}
	
	private void addSomeTrackTiles(Board board) {

		board.getTile(4,1).setTrack(new Track());
		board.getTile(4,2).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.SOUTHEAST)));
		board.getTile(5,3).setTrack(new Track(new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTHEAST)));
		board.getTile(6,4).setTrack(new Track(new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTHEAST)));
		board.getTile(7,5).setTrack(new Track(new Connection(CompassHeading.NORTHWEST, CompassHeading.EAST)));
		board.getTile(7,6).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.EAST)));
		board.getTile(7,7).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.EAST)));
		board.getTile(7,8).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.EAST)));
		board.getTile(7,9).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.EAST)));
		board.getTile(7,10).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.NORTHEAST)));
		board.getTile(6,11).setTrack(new Track(new Connection(CompassHeading.SOUTHWEST, CompassHeading.NORTHEAST)));
		board.getTile(5,12).setTrack(new Track(new Connection(CompassHeading.SOUTHWEST, CompassHeading.NORTHEAST)));
		board.getTile(4,13).setTrack(new Track(new Connection(CompassHeading.SOUTHWEST, CompassHeading.EAST)));
		board.getTile(4,14).setTrack(new Track());
		board.getTile(4,15).setTrack(new Track());
		board.getTile(4,16).setTrack(new Track());
		board.getTile(4,17).setTrack(new Track());
		board.getTile(4,18).setTrack(new Track());
	}
	 
	
    
}