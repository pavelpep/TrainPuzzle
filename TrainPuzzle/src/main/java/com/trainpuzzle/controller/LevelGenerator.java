package com.trainpuzzle.controller;

import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Connection;
import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Obstacle;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.board.StationTrackPosition;
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
        Location endLocation = new Location(4,19);
        Economy economy = new Economy();
        
		addStationTiles(board);
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
        Location endLocation = new Location(4,19);
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
		
		Event endStation = new Event(1, new Station(StationType.RED_FRONT, endLocation, StationTrackPosition.NORTH));
		
		victoryConditions.addChild(new LeafVictoryCondition(endStation));
		
		level.setVictoryConditions(victoryConditions);
	}
	
	private void addStationTiles(Board board) {
		board.getTile(6, 8).setStation(new Station(StationType.GREEN_FRONT, new Location(6,8), StationTrackPosition.SOUTH));
		board.getTile(4, 3).setStation(new Station(StationType.RED_FRONT, new Location(4,3), StationTrackPosition.SOUTH));
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
	
	private void addSomeTrackTiles(Board board) {
		board.getTile(4,0).setTrack(new Track());
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
		board.getTile(4,19).setTrack(new Track());
	}
	 
	
    
}