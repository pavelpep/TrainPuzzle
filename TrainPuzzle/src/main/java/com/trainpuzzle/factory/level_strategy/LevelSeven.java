package com.trainpuzzle.factory.level_strategy;

import java.util.ArrayList;

import java.util.HashMap;

import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.Cargo;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.board.TrackType;
import com.trainpuzzle.model.board.Cargo.CargoType;
import com.trainpuzzle.model.board.Landscape.LandscapeType;
import com.trainpuzzle.model.board.Obstacle.ObstacleType;
import com.trainpuzzle.model.board.Station.StationType;
import com.trainpuzzle.model.level.Economy;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.level.victory_condition.AndVictoryCondition;
import com.trainpuzzle.model.level.victory_condition.IfThenVictoryCondition;
import com.trainpuzzle.model.level.victory_condition.LogicalVictoryCondition;
import com.trainpuzzle.model.level.victory_condition.OrVictoryCondition;

public class LevelSeven extends LevelOutline {
	
	
	@Override
	public Level createLevel() {
		this.board = new Board(13, 19, LandscapeType.LIGHTSWAMP);
        this.root = new AndVictoryCondition();
        root.setDefaultModel();

        setLandscape();
        initStationsAndCargoGenerator();
        setObstacles();
        
        Economy economy = createEconomy();
        
        Level levelSeven=new Level(7, this.board, createStartLocation(), this.root, economy);
        levelSeven.setCargoGenerators(cargoGenerators);
        levelSeven.setTimeLimit(60);
        	        
    	return levelSeven;
	}

	private Economy createEconomy() {
		HashMap<TrackType, Integer> trackLimits = createTrackLimits();

        Economy economy = new Economy(NO_ECONOMY_LIMIT, trackLimits);
		return economy;
	}

	private Location createStartLocation() {
		Location startLocation = new Location(6,0);
        setStartLocation(startLocation, CompassHeading.WEST, CompassHeading.EAST, TrackType.STRAIGHT_TRACK);
		return startLocation;
	}

	private void initStationsAndCargoGenerator() {
		ArrayList<Station> stations = new ArrayList<Station>();
        Station stationToAdd=new Station(StationType.RED, new Location(8, 6), CompassHeading.EAST);
/*
        stations.add(stationToAdd);
        stationToAdd.addExportCargo(new Cargo(CargoType.IRON));
        stationToAdd.addExportCargo(new Cargo(CargoType.WOOD));
       
        stationToAdd = new Station(StationType.GREEN, new Location(12, 8), CompassHeading.SOUTH);
        stations.add(stationToAdd);
        addImportCargo (stationToAdd, root, new Cargo(CargoType.IRON));
        stationToAdd.addExportCargo(new Cargo(CargoType.COTTON));
*/      //pass station at
        
        LogicalVictoryCondition pathOne = new IfThenVictoryCondition();
        LogicalVictoryCondition pathTwo = new IfThenVictoryCondition();
        LogicalVictoryCondition selectPath = new OrVictoryCondition();
        selectPath.addChild(pathOne);
        selectPath.addChild(pathTwo);
        root.addChild(selectPath);
        
        //generator
        stationToAdd = new Station(StationType.FACTORY, new Location(5, 3), CompassHeading.SOUTH);
        stations.add(stationToAdd);
        addCargoGenerator(stationToAdd, 2, CargoType.COAL);
        
        
        //stationToAdd.addExportCargo(new Cargo(CargoType.COAL));
/*        
        CargoGenerator ironFactory = new CargoGenerator(stationToAdd,6,CargoType.IRON);
        cargoGenerators.add(ironFactory);
        CargoGenerator coalFactory = new CargoGenerator(stationToAdd,6,CargoType.COAL);
        cargoGenerators.add(coalFactory);
*/        
        //first set of stations: need iron & coal cargo
        stationToAdd = new Station(StationType.RED, new Location(2, 6), CompassHeading.WEST);
        stations.add(stationToAdd);	
        addImportCargo (stationToAdd, pathOne, new Cargo(CargoType.COAL));

        stationToAdd = new Station(StationType.GREEN, new Location(10, 6), CompassHeading.WEST);
        stations.add(stationToAdd);	
        addImportCargo (stationToAdd, pathTwo, new Cargo(CargoType.COAL));
        
        //second set of stations: have cotton cargo
        stationToAdd = new Station(StationType.RED, new Location(3, 10), CompassHeading.WEST);
        stations.add(stationToAdd);
        stationToAdd.addExportCargo(new Cargo(CargoType.WOOD));
        stationToAdd.addExportCargo(new Cargo(CargoType.COTTON));

        stationToAdd = new Station(StationType.GREEN, new Location(9, 10), CompassHeading.WEST);
        stations.add(stationToAdd);
        stationToAdd.addExportCargo(new Cargo(CargoType.IRON));
        stationToAdd.addExportCargo(new Cargo(CargoType.COTTON));
        
        
        //third set of stations: need cotton cargo
        stationToAdd = new Station(StationType.RED, new Location(2, 14), CompassHeading.WEST);
        stations.add(stationToAdd);
        addImportCargo (stationToAdd, pathOne, new Cargo(CargoType.IRON));	

        stationToAdd = new Station(StationType.GREEN, new Location(10, 14), CompassHeading.WEST);
        stations.add(stationToAdd);
        addImportCargo (stationToAdd, pathTwo, new Cargo(CargoType.WOOD));	
        
        //last station: needs cotton cargo
        stationToAdd = new Station(StationType.GREEN, new Location(5, 18), CompassHeading.SOUTH);
        stations.add(stationToAdd);
        addImportCargo (stationToAdd, root, new Cargo(CargoType.COTTON));	
        
        setStations(stations);
	}	
	
	private HashMap<TrackType, Integer> createTrackLimits() {
		HashMap<TrackType, Integer> trackLimits = new HashMap<TrackType,Integer>();
        
        trackLimits.put(TrackType.TRACK, NO_ECONOMY_LIMIT);
        trackLimits.put(TrackType.STRAIGHT, 22);
        trackLimits.put(TrackType.CURVE, 22);
        trackLimits.put(TrackType.INTERSECTION, 1);
        trackLimits.put(TrackType.SWITCH, 0);
        trackLimits.put(TrackType.STRAIGHT_TRACK, 22);
        trackLimits.put(TrackType.DIAGONAL_TRACK, 22);
        trackLimits.put(TrackType.CURVELEFT_TRACK, 15);
        trackLimits.put(TrackType.CURVERIGHT_TRACK, 15);
        trackLimits.put(TrackType.INTERSECTION_TRACK, 0);
        trackLimits.put(TrackType.DIAGONAL_INTERSECTION_TRACK, 1);
        trackLimits.put(TrackType.CURVELEFT_STRAIGHT_SWITCH, 0);
        trackLimits.put(TrackType.CURVERIGHT_STRAIGHT_SWITCH, 0);
		return trackLimits;
	}

	private void setLandscape() {
		
		
		//////////////other to be removed
        
        //start location: non-blocking swamp
        setLandscapeByColumn(0, 12, 0, LandscapeType.DARKSWAMP);
        setLandscapeByColumn(0, 4, 1, LandscapeType.DARKSWAMP);
        setLandscapeByColumn(5, 8, 1, LandscapeType.MEDSWAMP);
        setLandscapeByColumn(9, 12, 1, LandscapeType.DARKSWAMP);
        setLandscapeByColumn(0, 3, 2, LandscapeType.DARKSWAMP);
        setLandscapeByColumn(4, 6, 2, LandscapeType.MEDSWAMP);
        setLandscapeByColumn(8, 10, 2, LandscapeType.MEDSWAMP);
        setLandscapeByColumn(10, 12, 2, LandscapeType.DARKSWAMP);
        setLandscapeByColumn(0, 2, 3, LandscapeType.DARKSWAMP);
        setLandscapeByColumn(11, 12, 3, LandscapeType.DARKSWAMP);
        setLandscapeByColumn(0, 1, 4, LandscapeType.DARKSWAMP);
        setLandscapeByColumn(0, 0, 5, LandscapeType.DARKSWAMP);
        
        //second red station: non-blocking swamp
        setLandscapeByRow(0, 8, 12, LandscapeType.DARKSWAMP);
        setLandscapeByRow(1, 9, 12, LandscapeType.MEDSWAMP);
        setLandscapeByRow(2, 10, 11, LandscapeType.LIGHTSWAMP);
        
        //random blocks
        setLandscapeByColumn(6, 6, 9, LandscapeType.MEDSWAMP);
        setLandscapeByColumn(7, 7, 12, LandscapeType.MEDSWAMP);
        
        //last station: beach landscape
        setLandscapeByColumn(0, 0, 13, LandscapeType.ROUGHDIRT);
        setLandscapeByColumn(12, 12, 13, LandscapeType.ROUGHDIRT);
        setLandscapeByColumn(0, 0, 14, LandscapeType.DIRT);
        setLandscapeByColumn(1, 1, 14, LandscapeType.ROUGHDIRT);
        setLandscapeByColumn(11, 11, 14, LandscapeType.ROUGHDIRT);
        setLandscapeByColumn(12, 12, 14, LandscapeType.DIRT);
        setLandscapeByColumn(0, 0, 15, LandscapeType.LIGHTDIRT);
        setLandscapeByColumn(1, 1, 15, LandscapeType.DIRT);
        setLandscapeByColumn(2, 10, 15, LandscapeType.ROUGHDIRT);
        setLandscapeByColumn(11, 11, 15, LandscapeType.DIRT);
        setLandscapeByColumn(12, 12, 15, LandscapeType.LIGHTDIRT);
        setLandscapeByColumn(1, 1, 16, LandscapeType.LIGHTDIRT);
        setLandscapeByColumn(11, 11, 16, LandscapeType.LIGHTDIRT);
        setLandscapeByColumn(0, 0, 16, LandscapeType.WATER);
        setLandscapeByColumn(12, 12, 16, LandscapeType.WATER);
        setLandscapeByColumn(2, 10, 16, LandscapeType.DIRT);
        setLandscapeByColumn(2, 10, 17, LandscapeType.LIGHTDIRT);
        setLandscapeByColumn(0, 1, 17, LandscapeType.WATER);
        setLandscapeByColumn(11, 12, 17, LandscapeType.WATER);
        
        setLandscapeByColumn(5, 6, 18, LandscapeType.LIGHTDIRT);
        setLandscapeByColumn(0, 4, 18, LandscapeType.WATER);
        setLandscapeByColumn(7, 12, 18, LandscapeType.WATER);
        //setLandscapeByColumn(9, 14, 19, LandscapeType.ROUGHDIRT);
	}

	private void setObstacles() {
		//ArrayList<Location> rockLocations = new ArrayList<Location>();
        //rockLocations.add(new Location(6, 3));
        //rockLocations.add(new Location(3, 15));
        //rockLocations.add(new Location(8, 17));
        //setObstacles(rockLocations, ObstacleType.ROCK);
        //new Location(7,0);
        //new Location(6, 3)
    //    setObstaclesByRow(7, 5, 7, ObstacleType.TREES);
		
		//for start location
		//setObstaclesByRow(5, 0, 2, ObstacleType.TREES);
		//setObstaclesByRow(7, 0, 2, ObstacleType.TREES);
		setObstaclesByColumn(0, 5, 0, ObstacleType.TREES);
		setObstaclesByColumn(7, 12, 0, ObstacleType.TREES);
		setObstaclesByColumn(0, 5, 1, ObstacleType.TREES);
		setObstaclesByColumn(7, 12, 1, ObstacleType.TREES);
		setObstaclesByColumn(0, 5, 2, ObstacleType.TREES);
		setObstaclesByColumn(7, 12, 2, ObstacleType.TREES);
		
		setObstaclesByColumn(0, 4, 3, ObstacleType.TREES);
		setObstaclesByColumn(7, 12, 3, ObstacleType.TREES);
		
		//for first set of stations
		setObstaclesByColumn(3, 9, 6, ObstacleType.TREES);
		setObstaclesByColumn(1, 11, 7, ObstacleType.TREES);
		
		
		//for second set of stations
		setObstaclesByColumn(4, 8, 10, ObstacleType.TREES);
		setObstaclesByColumn(2, 10, 11, ObstacleType.TREES);
		
		
		//for third set of stations
		setObstaclesByColumn(3, 9, 14, ObstacleType.ROCK);
		setObstaclesByColumn(1, 11, 15, ObstacleType.ROCK);
		
/*
        setObstaclesByRow(5, 9, 9, ObstacleType.TREES);
        setObstaclesByRow(10, 0, 3, ObstacleType.TREES);
        setObstaclesByRow(11, 3, 4, ObstacleType.TREES);
        setObstaclesByRow(12, 4, 5, ObstacleType.TREES);
        setObstaclesByRow(13, 5, 6, ObstacleType.TREES);
        setObstaclesByRow(14, 6, 6, ObstacleType.TREES);
        
        setObstaclesByRow(11, 12, 12, ObstacleType.MOUNTAINS);
        setObstaclesByRow(7, 4, 5, ObstacleType.MOUNTAINS);
        setObstaclesByRow(8, 5, 5, ObstacleType.MOUNTAINS);
        setObstaclesByRow(12, 7, 7, ObstacleType.MOUNTAINS);
*/
	}

}
