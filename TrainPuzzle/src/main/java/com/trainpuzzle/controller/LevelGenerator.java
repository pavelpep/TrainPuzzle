package com.trainpuzzle.controller;

import com.trainpuzzle.model.board.*;
import com.trainpuzzle.model.board.Landscape.LandscapeType;
import com.trainpuzzle.model.board.Station.StationType;
import com.trainpuzzle.model.level.Economy;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.level.victory_condition.AndVictoryCondition;
import com.trainpuzzle.model.level.victory_condition.Event;
import com.trainpuzzle.model.level.victory_condition.LeafVictoryCondition;
import com.trainpuzzle.model.level.victory_condition.VictoryCondition;
import com.trainpuzzle.model.level.victory_condition.VictoryConditionEvaluator;

//purposely created without the public tag because we only want this class accessible by the CampaignManager (which is in the same package)
class LevelGenerator {
    
    public Level createLevelOne() {
    	Level level;
    	Board board = new Board();
        Economy economy = new Economy();

        Location startLocation = new Location(4,0);
		Track startingTrack = new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST));
		startingTrack.setUnremoveable();
		board.getTile(startLocation).setTrack(startingTrack);
		
        //Location endLocation = new Location(4,19);
        //Track endingTrack = new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST));
        //endingTrack.setUnremoveable();
		//board.getTile(endLocation).setTrack(endingTrack);
        
        AndVictoryCondition root =new AndVictoryCondition();
		addStations(board,root);
		addSomeWaterTiles(board);
		addSomeTrackTiles(board);
		addSomeObstacles(board);
		
    	level = new Level(1, board, startLocation, root, economy);
    	//setVictoryConditions(level, endLocation);
    	return level;
	}
    
    public Level createLevelTwo() {
    	Level level;
    	Board board = new Board();
        Economy economy = new Economy();

        Location startLocation = new Location(4,4);
        Track startingTrack = new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST));
		startingTrack.setUnremoveable();
		board.getTile(startLocation).setTrack(startingTrack);
		
		AndVictoryCondition root =new AndVictoryCondition(); 
		addStations(board,root);
		addTrackLoop(board);     				
    	level = new Level(2, board, startLocation,root, economy);
    	//setVictoryConditions(level, 0);
    	return level;
	}
	
	private void addStations(Board board,AndVictoryCondition root){
		Location location= new Location(5,7);
		addStationOnTile(root, board, StationType.GREEN, location, CompassHeading.SOUTH);
	
		location= new Location(10,12);
		addStationOnTile(root, board, StationType.RED, location, CompassHeading.SOUTH);
		
		location= new Location(2,14);
		addStationOnTile(root, board, StationType.GREEN, location, CompassHeading.SOUTH);
		
		location= new Location(8,16);
		addStationOnTile(root, board, StationType.RED, location, CompassHeading.SOUTH);
	}
	
	private void addStationOnTile(AndVictoryCondition root, Board board, StationType stationType, Location location, CompassHeading entranceFacing ) {
		Station station = new Station(stationType, location, entranceFacing);
		board.getTile(location).setStationBuilding(station);
		board.getTile(station.getTrackLocation()).setStationTrack(station);
		
		Event event = new Event(100,station);
		LeafVictoryCondition leaf = new LeafVictoryCondition(event);
		root.addChild(leaf);
		//board.getTile(4, 3).setStationBuilding(new Station(StationType.RED_FRONT, new Location(4,3), CompassHeading.SOUTH));
	}
	
	private void addSomeWaterTiles(Board board) {
		board.getTile(11, 0).setLandscapeType(LandscapeType.WATER);
		board.getTile(11, 1).setLandscapeType(LandscapeType.WATER);	
		board.getTile(11, 2).setLandscapeType(LandscapeType.WATER);
		board.getTile(12, 0).setLandscapeType(LandscapeType.WATER);
		board.getTile(12, 1).setLandscapeType(LandscapeType.WATER);	
		board.getTile(12, 2).setLandscapeType(LandscapeType.WATER);	
		board.getTile(12, 3).setLandscapeType(LandscapeType.WATER);	
		board.getTile(13, 0).setLandscapeType(LandscapeType.WATER);
		board.getTile(13, 1).setLandscapeType(LandscapeType.WATER);	
		board.getTile(13, 2).setLandscapeType(LandscapeType.WATER);	
		board.getTile(13, 3).setLandscapeType(LandscapeType.WATER);	
		board.getTile(13, 4).setLandscapeType(LandscapeType.WATER);	
		board.getTile(14, 0).setLandscapeType(LandscapeType.WATER);
		board.getTile(14, 1).setLandscapeType(LandscapeType.WATER);	
		board.getTile(14, 2).setLandscapeType(LandscapeType.WATER);	
		board.getTile(14, 3).setLandscapeType(LandscapeType.WATER);	
		board.getTile(14, 4).setLandscapeType(LandscapeType.WATER);	
		board.getTile(14, 5).setLandscapeType(LandscapeType.WATER);	
	}
	
	private void addSomeObstacles(Board board) {
		board.getTile(10, 0).setObstacle(new Obstacle(Obstacle.ObstacleType.TREES));
		board.getTile(10, 1).setObstacle(new Obstacle(Obstacle.ObstacleType.TREES));	
		board.getTile(10, 2).setObstacle(new Obstacle(Obstacle.ObstacleType.TREES));	
		board.getTile(11, 3).setObstacle(new Obstacle(Obstacle.ObstacleType.TREES));
		board.getTile(12, 4).setObstacle(new Obstacle(Obstacle.ObstacleType.TREES));
		board.getTile(13, 5).setObstacle(new Obstacle(Obstacle.ObstacleType.TREES));
		board.getTile(14, 6).setObstacle(new Obstacle(Obstacle.ObstacleType.TREES));
		board.getTile(4, 10).setObstacle(new Obstacle(Obstacle.ObstacleType.ROCK));
		board.getTile(2, 3).setObstacle(new Obstacle(Obstacle.ObstacleType.ROCK));
		board.getTile(9, 18).setObstacle(new Obstacle(Obstacle.ObstacleType.ROCK));
	}
	
	private void addTrackLoop(Board board) {
		board.getTile(4,4).setTrack(new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST)));
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
		board.getTile(9,9).setTrack(new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST)));
		board.getTile(9,8).setTrack(new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST)));
		board.getTile(9,7).setTrack(new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST)));
		board.getTile(9,6).setTrack(new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST)));
		board.getTile(9,5).setTrack(new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST)));
		board.getTile(9,4).setTrack(new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST)));
		board.getTile(9,3).setTrack(new Track(new Connection(CompassHeading.NORTHWEST, CompassHeading.EAST)));
		board.getTile(8,2).setTrack(new Track(new Connection(CompassHeading.NORTH, CompassHeading.SOUTHEAST)));
		board.getTile(7,2).setTrack(new Track(new Connection(CompassHeading.NORTH, CompassHeading.SOUTH)));
		board.getTile(6,2).setTrack(new Track(new Connection(CompassHeading.NORTH, CompassHeading.SOUTH)));
		board.getTile(5,2).setTrack(new Track(new Connection(CompassHeading.NORTHEAST, CompassHeading.SOUTH)));
		board.getTile(4,3).setTrack(new Track(new Connection(CompassHeading.SOUTHWEST, CompassHeading.EAST)));
	}
	
	private void addSomeTrackTiles(Board board) {
		board.getTile(4,1).setTrack(new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST)));
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
		board.getTile(4,14).setTrack(new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST)));
		board.getTile(4,15).setTrack(new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST)));
		board.getTile(4,16).setTrack(new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST)));
		board.getTile(4,17).setTrack(new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST)));
		board.getTile(4,18).setTrack(new Track(new Connection(CompassHeading.EAST, CompassHeading.WEST)));
	}

	/*
	private void setVictoryConditions(Level level, Location endLocation) {
	AndVictoryCondition victoryConditions = new AndVictoryCondition();
	//TODO: ensure endStation is correct, also add another station in between
	//possibly need to add start location to victory conditions
	
	Event endStation = new Event(1, new Station(StationType.GREEN, endLocation, CompassHeading.NORTH),"passEndStation");
	
	victoryConditions.addChild(new LeafVictoryCondition(endStation));
	
	level.setVictoryConditions(victoryConditions);
	}
	*/
}