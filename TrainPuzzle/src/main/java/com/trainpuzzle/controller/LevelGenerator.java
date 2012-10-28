package com.trainpuzzle.controller;

import java.util.ArrayList;

import com.trainpuzzle.model.board.*;
import com.trainpuzzle.model.board.Landscape.LandscapeType;
import com.trainpuzzle.model.board.Obstacle.ObstacleType;
import com.trainpuzzle.model.board.Station.StationType;
import com.trainpuzzle.model.level.Economy;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.level.victory_condition.AndVictoryCondition;
import com.trainpuzzle.model.level.victory_condition.Event;
import com.trainpuzzle.model.level.victory_condition.LeafVictoryCondition;


//purposely created without the public tag because we only want this class accessible by the CampaignManager (which is in the same package)
class LevelGenerator {
	private Board board;
	private AndVictoryCondition root;
	private Economy economy;
    
	private void setStartLocation(Location location, CompassHeading compassHeading1, CompassHeading compassHeading2) {
		Track startingTrack = new Track(new Connection(compassHeading1, compassHeading2));
		startingTrack.setUnremoveable();
		this.board.getTile(location).setTrack(startingTrack);
	}
    	
	private void setStations(ArrayList<Station> stations) {
		for (Station station : stations) {
			board.getTile(station.getStationLocation()).setStationBuilding(station);
			board.getTile(station.getTrackLocation()).setStationTrack(station);
			
			// adds station victory condition 
			Event event = new Event(100,station); 
			LeafVictoryCondition leaf = new LeafVictoryCondition(event);
			root.addChild(leaf);
		}
	}
 	
	private void setLandscapes(ArrayList<Location> locations, LandscapeType landscapeType) {
		for (Location currentLocation : locations) {
			this.board.getTile(currentLocation).setLandscapeType(landscapeType);
		}
	}
	
	private void setLandscapeByRow(int row, int columnStart, int columnEnd, LandscapeType landscapeType) {
		for (int i = columnStart; i <= columnEnd; i++){
			this.board.getTile(row, i).setLandscapeType(landscapeType);
		}
	}
	
	private void setLandscapeByColumn(int rowStart, int rowEnd, int column, LandscapeType landscapeType) {
		for (int i = rowStart; i <= rowEnd; i++){
			this.board.getTile(i, column).setLandscapeType(landscapeType);
		}
	}
	
	private void setObstacles(ArrayList<Location> locations, ObstacleType obstacleType) {
		for (Location currentLocation : locations) {
			this.board.getTile(currentLocation).setObstacle(new Obstacle(obstacleType));
		}
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
	
	   public Level createLevelOne() {
	    	this.board = new Board();
	        this.economy = new Economy();
	        this.root = new AndVictoryCondition();

	        Location startLocation = new Location(4,0);
	        setStartLocation(startLocation, CompassHeading.WEST, CompassHeading.EAST);
			
	        ArrayList<Station> stations = new ArrayList<Station>();
	        stations.add(new Station(StationType.RED, new Location(8, 6), CompassHeading.EAST));
	        stations.add(new Station(StationType.GREEN, new Location(12, 8), CompassHeading.SOUTH));
	        setStations(stations);
	        
	        setLandscapeByRow(14, 0, 5, LandscapeType.WATER);
	        setLandscapeByRow(13, 0, 4, LandscapeType.WATER);
	        setLandscapeByRow(12, 0, 3, LandscapeType.WATER);
	        setLandscapeByRow(11, 0, 2, LandscapeType.WATER);
	        setLandscapeByColumn(4, 7, 18, LandscapeType.WATER);
	        setLandscapeByColumn(3, 8, 19, LandscapeType.WATER);
	        
	        ArrayList<Location> treeLocations = new ArrayList<Location>();
	        treeLocations.add(new Location(10, 0));
	        treeLocations.add(new Location(10, 1));
	        treeLocations.add(new Location(10, 2));
	        treeLocations.add(new Location(10, 3));
	        treeLocations.add(new Location(11, 3));
	        treeLocations.add(new Location(11, 4));
	        treeLocations.add(new Location(12, 4));
	        treeLocations.add(new Location(12, 5));
	        treeLocations.add(new Location(13, 5));
	        treeLocations.add(new Location(13, 6));
	        treeLocations.add(new Location(14, 6));
	        setObstacles(treeLocations, ObstacleType.TREES);

	    	return new Level(1, this.board, startLocation, this.root, this.economy);
		}
		
	    public Level createLevelTwo() {
	    	this.board = new Board();
	        this.economy = new Economy();
	        this.root = new AndVictoryCondition();

	        Location startLocation = new Location(4,4);
	        setStartLocation(startLocation, CompassHeading.WEST, CompassHeading.EAST);
			
	        ArrayList<Station> stations = new ArrayList<Station>();
	        stations.add(new Station(StationType.GREEN, new Location(11, 3), CompassHeading.EAST));
	        stations.add(new Station(StationType.RED, new Location(2, 8), CompassHeading.SOUTH));
	        stations.add(new Station(StationType.GREEN, new Location(7, 18), CompassHeading.WEST));
	        setStations(stations);
	        
	        ArrayList<Location> waterLocations = new ArrayList<Location>();
	        waterLocations.add(new Location(0,5));
	        waterLocations.add(new Location(0,6));
	        waterLocations.add(new Location(0,7));
	        waterLocations.add(new Location(0,8));
	        waterLocations.add(new Location(0,9));
	        waterLocations.add(new Location(0,10));
	        waterLocations.add(new Location(1,5));
	        waterLocations.add(new Location(1,6));
	        waterLocations.add(new Location(1,7));
	        waterLocations.add(new Location(1,8));
	        waterLocations.add(new Location(1,9));
	        waterLocations.add(new Location(9,12));
	        waterLocations.add(new Location(9,13));
	        waterLocations.add(new Location(9,14));
	        waterLocations.add(new Location(10,12));
	        waterLocations.add(new Location(10,13));
	        waterLocations.add(new Location(10,14));
	        waterLocations.add(new Location(10,15));
	        setLandscapes(waterLocations, LandscapeType.WATER);
	        
	        ArrayList<Location> treeLocations = new ArrayList<Location>();
	        treeLocations.add(new Location(5, 13));
	        treeLocations.add(new Location(2, 1));
	        treeLocations.add(new Location(6, 9));
	        treeLocations.add(new Location(8, 3));
	        treeLocations.add(new Location(3, 13));
	        treeLocations.add(new Location(6, 10));
	        treeLocations.add(new Location(2, 6));
	        treeLocations.add(new Location(1, 13));
	        treeLocations.add(new Location(13, 10));
	        treeLocations.add(new Location(6, 13));
	        treeLocations.add(new Location(4, 10));
	        setObstacles(treeLocations, ObstacleType.TREES);

	    	return new Level(2, this.board, startLocation, this.root, this.economy);
		}
	    
	    public Level createLevelThree() {
	    	this.board = new Board(10, 10);
	        this.economy = new Economy();
	        this.root = new AndVictoryCondition();
	        
	        Location startLocation = new Location(0,0);
	        setStartLocation(startLocation, CompassHeading.WEST, CompassHeading.EAST);
			
	        ArrayList<Station> stations = new ArrayList<Station>();
	        stations.add(new Station(StationType.GREEN, new Location(7, 5), CompassHeading.EAST));
	        stations.add(new Station(StationType.RED, new Location(1, 8), CompassHeading.SOUTH));

	        setStations(stations);
	        
	    	return new Level(3, this.board, startLocation, this.root, this.economy);
		}
}