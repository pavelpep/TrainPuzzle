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


public class LevelTwelve extends LevelOutline {
	
	
	@Override
	public Level createLevel() {
        this.root = new AndVictoryCondition();

        setLandscape();
        initStationsAndCargoGenerator();
        setObstacles();
        
        Economy economy = createEconomy();
        
        Level levelTwelve=new Level(12, this.board, createStartLocation(), this.root, economy);
        levelTwelve.setCargoGenerators(cargoGenerators);
        levelTwelve.setTimeLimit(-1);
        	        
    	return levelTwelve;
	}

	private Economy createEconomy() {
		HashMap<TrackType, Integer> trackLimits = createTrackLimits();

        Economy economy = new Economy(NO_ECONOMY_LIMIT, trackLimits);
		return economy;
	}

	private Location createStartLocation() {
		Location startLocation = new Location(4,0);
        setStartLocation(startLocation, CompassHeading.WEST, CompassHeading.EAST, TrackType.STRAIGHT_TRACK);
		return startLocation;
	}

	private void initStationsAndCargoGenerator() {
		ArrayList<Station> stations = new ArrayList<Station>();
		
        
        Station stationToAdd = new Station(StationType.GREEN, new Location(5, 1), CompassHeading.NORTH);
        stations.add(stationToAdd);
        stationToAdd = new Station(StationType.RED, new Location(12, 12), CompassHeading.WEST);
        stations.add(stationToAdd);
        setPassStations(stations);
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
		setLandscapeByRow(4, 9, 10, LandscapeType.WATER);
        setLandscapeByRow(5, 10, 11, LandscapeType.WATER);
        setLandscapeByRow(14, 0, 5, LandscapeType.WATER);

              
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
        
        setObstaclesByColumn(8, 14, 0, ObstacleType.MOUNTAINS);

	}

}
