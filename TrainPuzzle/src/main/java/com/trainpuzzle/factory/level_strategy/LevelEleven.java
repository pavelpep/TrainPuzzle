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


public class LevelEleven extends LevelOutline {
	
	
	@Override
	public Level createLevel() {
        this.root = new AndVictoryCondition();

        setLandscape();
        initStationsAndCargoGenerator();
        setObstacles();
        
        Economy economy = createEconomy();
        
        Level levelEleven=new Level(1, this.board, createStartLocation(), this.root, economy);
        levelEleven.setCargoGenerators(cargoGenerators);
        levelEleven.setTimeLimit(60);
        	        
    	return levelEleven;
	}

	private Economy createEconomy() {
		HashMap<TrackType, Integer> trackLimits = createTrackLimits();

        Economy economy = new Economy(NO_ECONOMY_LIMIT, trackLimits);
		return economy;
	}

	private Location createStartLocation() {
		Location startLocation = new Location(8,0);
        setStartLocation(startLocation, CompassHeading.WEST, CompassHeading.EAST, TrackType.STRAIGHT_TRACK);
		return startLocation;
	}

	private void initStationsAndCargoGenerator() {
		ArrayList<Station> stations = new ArrayList<Station>();
		
		//There can not be more than 3 cargos or requested cargos in a green or red station;
        /*Station stationToAdd=new Station(StationType.RED, new Location(8, 6), CompassHeading.EAST);
        stations.add(stationToAdd);
        stationToAdd.addExportCargo(new Cargo(CargoType.IRON));
        stationToAdd.addExportCargo(new Cargo(CargoType.WOOD));
        stationToAdd.addExportCargo(new Cargo(CargoType.WOOD));
        
        stationToAdd = new Station(StationType.GREEN, new Location(12, 8), CompassHeading.SOUTH);
        stations.add(stationToAdd);
        addImportCargo (stationToAdd, root, new Cargo(CargoType.IRON));
        stationToAdd.addExportCargo(new Cargo(CargoType.COTTON));
        
        stationToAdd = new Station(StationType.GREEN, new Location(12, 12), CompassHeading.SOUTH);
        stations.add(stationToAdd);
        addImportCargo (stationToAdd, root, new Cargo(CargoType.WOOD));	
        addImportCargo (stationToAdd, root, new Cargo(CargoType.COTTON));
        addImportCargo (stationToAdd, root, new Cargo(CargoType.WOOD));	
        */
        
        Station stationToAdd = new Station(StationType.FACTORY, new Location(8, 15), CompassHeading.WEST);
        stations.add(stationToAdd);
        
        //There can not be more than 2 generators at one station
        //Don't add generator and requestor at one station simultaneously.
        addCargoGenerator(stationToAdd, 6, CargoType.COAL);
        addCargoGenerator(stationToAdd, 6, CargoType.STEEL);
        
        setPassStations(stations);
	}	
	
	private HashMap<TrackType, Integer> createTrackLimits() {
		HashMap<TrackType, Integer> trackLimits = new HashMap<TrackType,Integer>();
        
        trackLimits.put(TrackType.TRACK, 30);
        trackLimits.put(TrackType.STRAIGHT, NO_ECONOMY_LIMIT);
        trackLimits.put(TrackType.CURVE, 15);
        trackLimits.put(TrackType.INTERSECTION, 5);
        trackLimits.put(TrackType.SWITCH, 5);
        trackLimits.put(TrackType.STRAIGHT_TRACK, NO_ECONOMY_LIMIT);
        trackLimits.put(TrackType.DIAGONAL_TRACK, NO_ECONOMY_LIMIT);
        trackLimits.put(TrackType.CURVELEFT_TRACK, 10);
        trackLimits.put(TrackType.CURVERIGHT_TRACK, 10);
        trackLimits.put(TrackType.INTERSECTION_TRACK, 5);
        trackLimits.put(TrackType.DIAGONAL_INTERSECTION_TRACK, 5);
        trackLimits.put(TrackType.CURVELEFT_STRAIGHT_SWITCH, 5);
        trackLimits.put(TrackType.CURVERIGHT_STRAIGHT_SWITCH, 5);
		return trackLimits;
	}

	private void setLandscape() {
		setLandscapeByRow(4, 9, 10, LandscapeType.WATER);
        setLandscapeByRow(5, 10, 11, LandscapeType.WATER);
        setLandscapeByRow(14, 0, 5, LandscapeType.WATER);
        setLandscapeByRow(13, 0, 4, LandscapeType.WATER);
        setLandscapeByRow(12, 0, 3, LandscapeType.WATER);
        setLandscapeByRow(11, 0, 2, LandscapeType.WATER);
              
        setLandscapeByColumn(2, 8, 17, LandscapeType.LIGHTDIRT);
        setLandscapeByColumn(1, 3, 18, LandscapeType.DIRT);
        setLandscapeByColumn(0, 2, 19, LandscapeType.ROUGHDIRT);
        setLandscapeByColumn(8, 12, 18, LandscapeType.DIRT);
        setLandscapeByColumn(9, 14, 19, LandscapeType.ROUGHDIRT);
        
        setLandscapeByColumn(4, 7, 18, LandscapeType.WATER);
        setLandscapeByColumn(3, 8, 19, LandscapeType.WATER);
        
	}

	private void setObstacles() {
		ArrayList<Location> rockLocations = new ArrayList<Location>();
        rockLocations.add(new Location(6, 3));
        rockLocations.add(new Location(3, 15));
        rockLocations.add(new Location(8, 17));
        setObstacles(rockLocations, ObstacleType.ROCK);
        
        setObstaclesByRow(0, 0, 5, ObstacleType.TREES);
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
	}

}
