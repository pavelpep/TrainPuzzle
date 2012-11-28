package com.trainpuzzle.factory.level_strategy;

import java.util.ArrayList;

import java.util.HashMap;

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


public class LevelTen extends LevelOutline {
	
	
	@Override
	public Level createLevel() {
        this.root = new AndVictoryCondition();

        setLandscape();
        initStationsAndCargoGenerator();
        setObstacles();
        
        Economy economy = createEconomy();
        
        Level levelTen = new Level(10, this.board, createStartLocation(), this.root, economy);
        levelTen.setCargoGenerators(cargoGenerators);
        levelTen.setCargorequestors(cargoRequestGenerators);

        levelTen.setTimeLimit(200);
        	        
    	return levelTen;
	}

	private Economy createEconomy() {
		HashMap<TrackType, Integer> trackLimits = createTrackLimits();

        Economy economy = new Economy(NO_ECONOMY_LIMIT, trackLimits);
		return economy;
	}

	private Location createStartLocation() {
		Location startLocation = new Location(7,0);
        setStartLocation(startLocation, CompassHeading.WEST, CompassHeading.EAST, TrackType.STRAIGHT_TRACK);
		return startLocation;
	}

	private void initStationsAndCargoGenerator() {
		ArrayList<Station> stations = new ArrayList<Station>();
		
		//There can not be more than 3 cargos or requested cargos in a green or red station;
        Station stationToAdd = new Station(StationType.FACTORY, new Location(2,6), CompassHeading.SOUTH);
        stations.add(stationToAdd);
        addCargoGenerator(stationToAdd, 7, CargoType.IRON);
               
        stationToAdd = new Station(StationType.REQUESTER, new Location(2,13), CompassHeading.NORTH);
        stations.add(stationToAdd);
        addCargoRequester(stationToAdd, root, 30, CargoType.IRON);
        
        stationToAdd = new Station(StationType.RED, new Location(9,6), CompassHeading.EAST);
        stations.add(stationToAdd);
        addImportCargo (stationToAdd, root, new Cargo(CargoType.IRON));
        addImportCargo (stationToAdd, root, new Cargo(CargoType.COTTON)); 
        stationToAdd.addExportCargo(new Cargo(CargoType.COAL)); 

        stationToAdd = new Station(StationType.GREEN, new Location(9,13), CompassHeading.WEST);
        stations.add(stationToAdd);
        addImportCargo (stationToAdd, root, new Cargo(CargoType.IRON)); 
        stationToAdd.addExportCargo(new Cargo(CargoType.COTTON)); 
        stationToAdd.addExportCargo(new Cargo(CargoType.STEEL)); 

        stationToAdd = new Station(StationType.RED, new Location(7,18), CompassHeading.WEST);
        stations.add(stationToAdd);
        addImportCargo (stationToAdd, root, new Cargo(CargoType.IRON));	
        addImportCargo (stationToAdd, root, new Cargo(CargoType.COAL));	
        addImportCargo (stationToAdd, root, new Cargo(CargoType.STEEL));	

        setPassStations(stations);
	}	
	
	private HashMap<TrackType, Integer> createTrackLimits() {
		HashMap<TrackType, Integer> trackLimits = new HashMap<TrackType,Integer>();
        
        trackLimits.put(TrackType.TRACK, 70);
        trackLimits.put(TrackType.STRAIGHT, 30);
        trackLimits.put(TrackType.CURVE, 30);
        trackLimits.put(TrackType.INTERSECTION, 7);
        trackLimits.put(TrackType.SWITCH, 7);
        trackLimits.put(TrackType.STRAIGHT_TRACK, 30);
        trackLimits.put(TrackType.DIAGONAL_TRACK, 30);
        trackLimits.put(TrackType.CURVELEFT_TRACK, 30);
        trackLimits.put(TrackType.CURVERIGHT_TRACK, 30);
        trackLimits.put(TrackType.INTERSECTION_TRACK, 4);
        trackLimits.put(TrackType.DIAGONAL_INTERSECTION_TRACK, 4);
        trackLimits.put(TrackType.CURVELEFT_STRAIGHT_SWITCH, 5);
        trackLimits.put(TrackType.CURVERIGHT_STRAIGHT_SWITCH, 5);
		return trackLimits;
	}

	private void setLandscape() {
		setLandscapeByRow(0, 1, 6, LandscapeType.LIGHTSWAMP);
		setLandscapeByRow(0, 7, 12, LandscapeType.MEDSWAMP);
		setLandscapeByRow(0, 13, 18, LandscapeType.DARKSWAMP);
		setLandscapeByRow(14, 1, 18, LandscapeType.WATER);		  

		setLandscapeByColumn(0, 6, 0, LandscapeType.DIRT);
		setLandscapeByColumn(8, 14, 0, LandscapeType.ROUGHDIRT);
		setLandscapeByColumn(0, 6, 19, LandscapeType.ROUGHDIRT);
		setLandscapeByColumn(8, 14, 19, LandscapeType.DIRT);
        
		setLandscapeByRow(8, 9, 10, LandscapeType.WATER);		  
		setLandscapeByRow(9, 9, 10, LandscapeType.WATER);		  

	}

	private void setObstacles() {
		setObstaclesByRow(7, 19, 19, ObstacleType.ROCK);
        setObstaclesByRow(11, 16, 16, ObstacleType.ROCK);
        setObstaclesByRow(12, 15, 17, ObstacleType.ROCK);
        setObstaclesByRow(13, 14, 18, ObstacleType.ROCK);

        setObstaclesByRow(11, 3, 3, ObstacleType.TREES);
        setObstaclesByRow(12, 2, 4, ObstacleType.TREES);
        setObstaclesByRow(13, 1, 5, ObstacleType.TREES);
        
        setObstaclesByRow(1, 1, 2, ObstacleType.MOUNTAINS);
        setObstaclesByRow(1, 17, 18, ObstacleType.MOUNTAINS);
		setObstaclesByRow(5, 5, 7, ObstacleType.MOUNTAINS);	
		
        setObstaclesByColumn(5, 8, 15, ObstacleType.TREES);

	}

}
