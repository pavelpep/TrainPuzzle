package com.trainpuzzle.factory.level_strategy;

import java.util.ArrayList;
import java.util.LinkedList;

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
import com.trainpuzzle.controller.CargoGenerator;

public class LevelSeven extends LevelOutline {
	
	
	@Override
	public Level createLevel() {
		this.board = new Board(13, 19);
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
        stationToAdd = new Station(StationType.RED, new Location(5, 3), CompassHeading.SOUTH);
        stations.add(stationToAdd);
        stationToAdd.addExportCargo(new Cargo(CargoType.IRON));
        stationToAdd.addExportCargo(new Cargo(CargoType.COAL));
/*        
        CargoGenerator ironFactory = new CargoGenerator(stationToAdd,6,CargoType.IRON);
        cargoGenerators.add(ironFactory);
        CargoGenerator coalFactory = new CargoGenerator(stationToAdd,6,CargoType.COAL);
        cargoGenerators.add(coalFactory);
*/        
        //first set of stations: need iron & coal cargo
        stationToAdd = new Station(StationType.RED, new Location(2, 6), CompassHeading.WEST);
        stations.add(stationToAdd);
        addImportCargo (stationToAdd, pathOne, new Cargo(CargoType.IRON));	
        addImportCargo (stationToAdd, pathOne, new Cargo(CargoType.COAL));

        stationToAdd = new Station(StationType.GREEN, new Location(10, 6), CompassHeading.WEST);
        stations.add(stationToAdd);
        addImportCargo (stationToAdd, pathTwo, new Cargo(CargoType.IRON));	
        addImportCargo (stationToAdd, pathTwo, new Cargo(CargoType.COAL));
        
        //second set of stations: have cotton cargo
        stationToAdd = new Station(StationType.RED, new Location(3, 10), CompassHeading.WEST);
        stations.add(stationToAdd);
        stationToAdd.addExportCargo(new Cargo(CargoType.WOOD));
        stationToAdd.addExportCargo(new Cargo(CargoType.COTTON));

        stationToAdd = new Station(StationType.GREEN, new Location(9, 10), CompassHeading.WEST);
        stations.add(stationToAdd);
        stationToAdd.addExportCargo(new Cargo(CargoType.STEEL));
        stationToAdd.addExportCargo(new Cargo(CargoType.COTTON));
        
        
        //third set of stations: need cotton cargo
        stationToAdd = new Station(StationType.RED, new Location(2, 14), CompassHeading.WEST);
        stations.add(stationToAdd);
        addImportCargo (stationToAdd, pathOne, new Cargo(CargoType.STEEL));	

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
        trackLimits.put(TrackType.STRAIGHT, NO_ECONOMY_LIMIT);
        trackLimits.put(TrackType.CURVE, NO_ECONOMY_LIMIT);
        trackLimits.put(TrackType.INTERSECTION, NO_ECONOMY_LIMIT);
        trackLimits.put(TrackType.SWITCH, NO_ECONOMY_LIMIT);
        trackLimits.put(TrackType.STRAIGHT_TRACK, NO_ECONOMY_LIMIT);
        trackLimits.put(TrackType.DIAGONAL_TRACK, NO_ECONOMY_LIMIT);
        trackLimits.put(TrackType.CURVELEFT_TRACK, NO_ECONOMY_LIMIT);
        trackLimits.put(TrackType.CURVERIGHT_TRACK, NO_ECONOMY_LIMIT);
        trackLimits.put(TrackType.INTERSECTION_TRACK, NO_ECONOMY_LIMIT);
        trackLimits.put(TrackType.DIAGONAL_INTERSECTION_TRACK, NO_ECONOMY_LIMIT);
        trackLimits.put(TrackType.CURVELEFT_STRAIGHT_SWITCH, NO_ECONOMY_LIMIT);
        trackLimits.put(TrackType.CURVERIGHT_STRAIGHT_SWITCH, NO_ECONOMY_LIMIT);
		return trackLimits;
	}

	private void setLandscape() {
/*
		setLandscapeByRow(4, 9, 10, LandscapeType.WATER);
        setLandscapeByRow(5, 10, 11, LandscapeType.WATER);
        setLandscapeByRow(14, 0, 5, LandscapeType.WATER);
        setLandscapeByRow(13, 0, 4, LandscapeType.WATER);
        setLandscapeByRow(12, 0, 3, LandscapeType.WATER);
        setLandscapeByRow(11, 0, 2, LandscapeType.WATER);
*/        
        setLandscapeByRow(8, 4, 6, LandscapeType.DARKSWAMP);
        setLandscapeByRow(9, 3, 5, LandscapeType.DARKSWAMP);
        setLandscapeByRow(8, 6, 9, LandscapeType.MEDSWAMP);
        setLandscapeByRow(9, 5, 8, LandscapeType.MEDSWAMP);
        setLandscapeByRow(8, 9, 11, LandscapeType.LIGHTSWAMP);
        setLandscapeByRow(9, 8, 10, LandscapeType.LIGHTSWAMP);
        
        setLandscapeByColumn(4, 7, 17, LandscapeType.LIGHTDIRT);
        setLandscapeByColumn(0, 2, 18, LandscapeType.DIRT);
        //setLandscapeByColumn(0, 2, 19, LandscapeType.ROUGHDIRT);
        //setLandscapeByColumn(8, 12, 18, LandscapeType.DIRT);
        //setLandscapeByColumn(9, 14, 19, LandscapeType.ROUGHDIRT);
/*        
        setLandscapeByColumn(4, 7, 18, LandscapeType.WATER);
        setLandscapeByColumn(3, 8, 19, LandscapeType.WATER);
*/        
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
		setObstaclesByColumn(3, 9, 14, ObstacleType.TREES);
		setObstaclesByColumn(1, 11, 15, ObstacleType.TREES);
		
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
