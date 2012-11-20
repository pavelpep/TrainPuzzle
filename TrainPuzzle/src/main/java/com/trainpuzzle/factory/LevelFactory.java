package com.trainpuzzle.factory;

import java.util.ArrayList;



import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.Cargo;
import com.trainpuzzle.model.board.Cargo.*;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Connection;
import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Obstacle;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.board.TrackType;
import com.trainpuzzle.model.board.Landscape.LandscapeType;
import com.trainpuzzle.model.board.Obstacle.ObstacleType;
import com.trainpuzzle.model.board.Station.StationType;
import com.trainpuzzle.model.level.Economy;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.level.victory_condition.*;

import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;


//purposely created without the public tag because we only want this class accessible by the CampaignManager (which is in the same package)
/* 
 * Created without the public tag on purpose because we only want this class 
 * to be accessible by the CampaignManager (which is in the same package) 
*/

public class LevelFactory {
	private Board board; 
	private AndVictoryCondition root;
    
	private void setStartLocation(Location location, CompassHeading compassHeading1, CompassHeading compassHeading2, TrackType trackType) {
		Track startingTrack = new Track(new Connection(compassHeading1, compassHeading2), trackType);
		startingTrack.setUnremoveable();
		this.board.getTile(location).setTrack(startingTrack);
	}
    	
	private void setStations(ArrayList<Station> stations) {
		for (Station station : stations) {
			board.getTile(station.getStationLocation()).setStationBuilding(station);
			board.getTile(station.getTrackLocation()).setStationTrack(station);
			
			// Adds victory condition for station
			Event event = new Event(100,station); 
			LeafVictoryCondition leaf = new LeafVictoryCondition(event);
			root.addChild(leaf);
			
			//add CargoFactory station to A List
			if (station.getType()==StationType.IRON_FACTORY){
				board.addCargoFactories(station);
			}
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
	
	private void setObstaclesByRow(int row, int columnStart, int columnEnd, ObstacleType obstacleType) {
		for (int i = columnStart; i <= columnEnd; i++){
			this.board.getTile(row, i).setObstacle(new Obstacle(obstacleType));
		}
	}
	
	private void setObstaclesByColumn(int rowStart, int rowEnd, int column, ObstacleType obstacleType) {
		for (int i = rowStart; i <= rowEnd; i++){
			this.board.getTile(i, column).setObstacle(new Obstacle(obstacleType));
		}
	}
	
	private void setObstacles(ArrayList<Location> locations, ObstacleType obstacleType) {
		for (Location currentLocation : locations) {
			this.board.getTile(currentLocation).setObstacle(new Obstacle(obstacleType));
		}
	}
	private void addImportCargo (Station station, LogicalVictoryCondition condiction, Cargo cargo) {
		station.addImportCargo(cargo);
		DropCargoEvent event = new DropCargoEvent(100,station, cargo);
		LeafVictoryCondition lcondition = new LeafVictoryCondition(event);
		condiction.addChild(lcondition);
		
	}

	public Level createLevel(int levelNumber){
		Level level;
		switch(levelNumber) {
		case 1: level = createLevelOne();
				break;
		case 2: level = createLevelTwo();
				break;
		case 3: level = createLevelThree();
				break;
		default:level = createLevelOne();
				break;
		}
		return level;
	}
	
	private Level createLevelOne() {
    	this.board = new Board();
        this.root = new AndVictoryCondition();

        Location startLocation = new Location(4,0);
        setStartLocation(startLocation, CompassHeading.WEST, CompassHeading.EAST, TrackType.STRAIGHT_TRACK);
		
        ArrayList<Station> stations = new ArrayList<Station>();
        Station stationToAdd=new Station(StationType.RED, new Location(8, 6), CompassHeading.EAST);
        stations.add(stationToAdd);
        stationToAdd.addExportCargo(new Cargo(CargoType.IRON));
        stationToAdd.addExportCargo(new Cargo(CargoType.WOOD));
        
        stationToAdd=new Station(StationType.GREEN, new Location(12, 8), CompassHeading.SOUTH);
        stations.add(stationToAdd);
        addImportCargo (stationToAdd, root, new Cargo(CargoType.IRON));
        stationToAdd.addExportCargo(new Cargo(CargoType.COTTON));
        
        stationToAdd=new Station(StationType.GREEN, new Location(12, 12), CompassHeading.SOUTH);
        stations.add(stationToAdd);
        addImportCargo (stationToAdd, root, new Cargo(CargoType.WOOD));	
        addImportCargo (stationToAdd, root, new Cargo(CargoType.COTTON));	
        
        stationToAdd=new Station(StationType.IRON_FACTORY, new Location(8, 15), CompassHeading.WEST);
        stations.add(stationToAdd);
        
        setStations(stations);
        
        setLandscapeByRow(4, 9, 10, LandscapeType.WATER);
        setLandscapeByRow(5, 10, 11, LandscapeType.WATER);
        setLandscapeByRow(14, 0, 5, LandscapeType.WATER);
        setLandscapeByRow(13, 0, 4, LandscapeType.WATER);
        setLandscapeByRow(12, 0, 3, LandscapeType.WATER);
        setLandscapeByRow(11, 0, 2, LandscapeType.WATER);
        
        setLandscapeByColumn(2, 8, 17, LandscapeType.DIRT);
        setLandscapeByColumn(1, 3, 18, LandscapeType.DIRT);
        setLandscapeByColumn(0, 2, 19, LandscapeType.DIRT);
        setLandscapeByColumn(4, 7, 18, LandscapeType.WATER);
        setLandscapeByColumn(3, 8, 19, LandscapeType.WATER);
        setLandscapeByColumn(8, 12, 18, LandscapeType.DIRT);
        setLandscapeByColumn(9, 14, 19, LandscapeType.DIRT);
        
        setObstaclesByRow(0, 0, 5, ObstacleType.TREES);
        setObstaclesByRow(7, 4, 5, ObstacleType.MOUNTAINS);
        setObstaclesByRow(8, 5, 5, ObstacleType.MOUNTAINS);
        setObstaclesByRow(12, 7, 7, ObstacleType.MOUNTAINS);
        
        ArrayList<Location> rockLocations = new ArrayList<Location>();
        rockLocations.add(new Location(6, 3));
        rockLocations.add(new Location(3, 15));
        rockLocations.add(new Location(8, 17));
        setObstacles(rockLocations, ObstacleType.ROCK);
        
        setObstaclesByRow(5, 9, 9, ObstacleType.TREES);
        setObstaclesByRow(10, 0, 3, ObstacleType.TREES);
        setObstaclesByRow(11, 3, 4, ObstacleType.TREES);
        setObstaclesByRow(12, 4, 5, ObstacleType.TREES);
        setObstaclesByRow(13, 5, 6, ObstacleType.TREES);
        setObstaclesByRow(14, 6, 6, ObstacleType.TREES);
        setObstaclesByRow(11, 12, 12, ObstacleType.MOUNTAINS);

        
        HashMap<TrackType, Integer> trackLimitsLevelOne = new HashMap<TrackType,Integer>();
        final int NO_LIMIT = -1;
        trackLimitsLevelOne.put(TrackType.TRACK, 30);
        trackLimitsLevelOne.put(TrackType.STRAIGHT, NO_LIMIT);
        trackLimitsLevelOne.put(TrackType.CURVE, 15);
        trackLimitsLevelOne.put(TrackType.INTERSECTION, 5);
        trackLimitsLevelOne.put(TrackType.SWITCH, 5);
        trackLimitsLevelOne.put(TrackType.STRAIGHT_TRACK, NO_LIMIT);
        trackLimitsLevelOne.put(TrackType.DIAGONAL_TRACK, NO_LIMIT);
        trackLimitsLevelOne.put(TrackType.CURVELEFT_TRACK, 10);
        trackLimitsLevelOne.put(TrackType.CURVERIGHT_TRACK, 10);
        trackLimitsLevelOne.put(TrackType.INTERSECTION_TRACK, 5);
        trackLimitsLevelOne.put(TrackType.DIAGONAL_INTERSECTION_TRACK, 5);
        trackLimitsLevelOne.put(TrackType.CURVELEFT_STRAIGHT_SWITCH, 5);
        trackLimitsLevelOne.put(TrackType.CURVERIGHT_STRAIGHT_SWITCH, 5);
        int budget = NO_LIMIT;
        Economy economyLevelOne = new Economy(budget, trackLimitsLevelOne);
        		        
    	return new Level(1, this.board, startLocation, this.root, economyLevelOne);
	}
		
	private Level createLevelTwo() {
    	this.board = new Board();
        this.root = new AndVictoryCondition();
        Location startLocation = new Location(4,3);
        setStartLocation(startLocation, CompassHeading.WEST, CompassHeading.EAST, TrackType.STRAIGHT_TRACK);
		
        ArrayList<Station> stations = new ArrayList<Station>();
        Station stationToAdd = new Station(StationType.GREEN, new Location(11, 3), CompassHeading.EAST);  
        stations.add(stationToAdd);
        stationToAdd.addExportCargo(new Cargo(CargoType.IRON));
        stationToAdd.addExportCargo(new Cargo(CargoType.WOOD));
        
        stationToAdd = new Station(StationType.RED, new Location(2, 8), CompassHeading.SOUTH);
        stations.add(stationToAdd);	        
        addImportCargo (stationToAdd, root, new Cargo(CargoType.IRON));
        stationToAdd.addExportCargo(new Cargo(CargoType.COTTON));
        
        stationToAdd = new Station(StationType.GREEN, new Location(7, 18), CompassHeading.WEST);
        stations.add(stationToAdd);	        
        addImportCargo (stationToAdd, root, new Cargo(CargoType.COTTON));
        addImportCargo (stationToAdd, root, new Cargo(CargoType.WOOD));
        
        setStations(stations);
        
        setLandscapeByRow(0, 5, 10, LandscapeType.WATER);
        setLandscapeByRow(1, 5, 9, LandscapeType.WATER);
        setLandscapeByRow(9, 12, 14, LandscapeType.WATER);
        setLandscapeByRow(10, 12, 15, LandscapeType.WATER);
        
        ArrayList<Location> mountainLocations = new ArrayList<Location>();
        mountainLocations.add(new Location(8, 10));
        mountainLocations.add(new Location(4, 7));
        setObstacles(mountainLocations, ObstacleType.MOUNTAINS);
        
        setObstaclesByRow(11, 13, 14, ObstacleType.MOUNTAINS);
        setObstaclesByRow(12, 14, 15, ObstacleType.MOUNTAINS);
        setObstaclesByRow(13, 13, 14, ObstacleType.MOUNTAINS);
        setObstaclesByRow(14, 12, 13, ObstacleType.MOUNTAINS);
              
        ArrayList<Location> treeLocations = new ArrayList<Location>();
        treeLocations.add(new Location(5, 13));
        treeLocations.add(new Location(2, 1));
        treeLocations.add(new Location(6, 9));
        treeLocations.add(new Location(8, 3));
        treeLocations.add(new Location(3, 13));
        treeLocations.add(new Location(6, 10));
        treeLocations.add(new Location(2, 17));
        treeLocations.add(new Location(1, 13));
        treeLocations.add(new Location(13, 10));
        treeLocations.add(new Location(6, 13));
        treeLocations.add(new Location(4, 10));
        setObstacles(treeLocations, ObstacleType.TREES);
        
        HashMap<TrackType, Integer> trackLimitsLevelTwo = new HashMap<TrackType,Integer>();
        final int NO_LIMIT = -1;
        trackLimitsLevelTwo.put(TrackType.TRACK, 37);
        trackLimitsLevelTwo.put(TrackType.STRAIGHT, 18);
        trackLimitsLevelTwo.put(TrackType.CURVE, 15);
        trackLimitsLevelTwo.put(TrackType.INTERSECTION, 2);
        trackLimitsLevelTwo.put(TrackType.SWITCH, 2);
        trackLimitsLevelTwo.put(TrackType.STRAIGHT_TRACK, 18);
        trackLimitsLevelTwo.put(TrackType.DIAGONAL_TRACK, 18);
        trackLimitsLevelTwo.put(TrackType.CURVELEFT_TRACK, 10);
        trackLimitsLevelTwo.put(TrackType.CURVERIGHT_TRACK, 10);
        trackLimitsLevelTwo.put(TrackType.INTERSECTION_TRACK, 2);
        trackLimitsLevelTwo.put(TrackType.DIAGONAL_INTERSECTION_TRACK, 2);
        trackLimitsLevelTwo.put(TrackType.CURVELEFT_STRAIGHT_SWITCH,2);
        trackLimitsLevelTwo.put(TrackType.CURVERIGHT_STRAIGHT_SWITCH, 2);
        int budget = NO_LIMIT;
        Economy economyLevelTwo = new Economy(budget, trackLimitsLevelTwo);
        		        
    	return new Level(2, this.board, startLocation, this.root, economyLevelTwo);
	}
	    
	private Level createLevelThree() {
	    	this.board = new Board(10, 10);
	        Economy economy = new Economy();
	        this.root = new AndVictoryCondition();
	        root.setName("And");
	        
	        Location startLocation = new Location(0,0);
	        setStartLocation(startLocation, CompassHeading.WEST, CompassHeading.EAST, TrackType.STRAIGHT_TRACK);
			
	        ArrayList<Station> stations = new ArrayList<Station>();
	        stations.add(new Station(StationType.GREEN, new Location(7, 5), CompassHeading.EAST));
	        stations.add(new Station(StationType.RED, new Location(1, 8), CompassHeading.SOUTH));
	        
	        setObstaclesByRow(2, 4, 6, ObstacleType.TREES);

	        setObstaclesByRow(3, 4, 9, ObstacleType.TREES);
	        setObstaclesByColumn(4, 5, 2, ObstacleType.TREES);
	        setObstaclesByColumn(4, 5, 3, ObstacleType.TREES);
	        
	        ArrayList<Location> rockLocations = new ArrayList<Location>();
	        rockLocations.add(new Location(4, 5));
	        rockLocations.add(new Location(3, 2));
	        setObstacles(rockLocations, ObstacleType.ROCK);
	        
	        setLandscapeByColumn(1, 4, 0, LandscapeType.WATER);
	        setLandscapeByColumn(1, 2, 1, LandscapeType.WATER);
	        setLandscapeByColumn(7, 7, 3, LandscapeType.WATER);
	        setLandscapeByColumn(7, 8, 4, LandscapeType.WATER);
	        setLandscapeByColumn(4, 8, 8, LandscapeType.WATER);
	        setLandscapeByColumn(4, 8, 9, LandscapeType.WATER);

    	
	        setStations(stations);
        
	        HashMap<TrackType, Integer> trackLimitsLevelThree = new HashMap<TrackType,Integer>();
	        final int NO_LIMIT = -1;
	        trackLimitsLevelThree.put(TrackType.TRACK, 29);
	        trackLimitsLevelThree.put(TrackType.STRAIGHT, 10);
	        trackLimitsLevelThree.put(TrackType.CURVE, 15);
	        trackLimitsLevelThree.put(TrackType.INTERSECTION, 2);
	        trackLimitsLevelThree.put(TrackType.SWITCH, 2);
	        trackLimitsLevelThree.put(TrackType.STRAIGHT_TRACK, 10);
	        trackLimitsLevelThree.put(TrackType.DIAGONAL_TRACK, 10);
	        trackLimitsLevelThree.put(TrackType.CURVELEFT_TRACK, 10);
	        trackLimitsLevelThree.put(TrackType.CURVERIGHT_TRACK, 10);
	        trackLimitsLevelThree.put(TrackType.INTERSECTION_TRACK, 2);
	        trackLimitsLevelThree.put(TrackType.DIAGONAL_INTERSECTION_TRACK, 2);
	        trackLimitsLevelThree.put(TrackType.CURVELEFT_STRAIGHT_SWITCH, 2);
	        trackLimitsLevelThree.put(TrackType.CURVERIGHT_STRAIGHT_SWITCH, 2);
	        int budget = NO_LIMIT;
	        Economy economyLevelThree = new Economy(budget, trackLimitsLevelThree);
	        return new Level(3, this.board, startLocation, this.root, economyLevelThree);
	}
}