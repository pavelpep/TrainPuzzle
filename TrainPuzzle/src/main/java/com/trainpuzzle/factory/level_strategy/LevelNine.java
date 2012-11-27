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


public class LevelNine extends LevelOutline {

	@Override
	public Level createLevel() {
		this.board = new Board(9, 15, LandscapeType.MEDSWAMP);
        this.root = new AndVictoryCondition();
        root.setDefaultModel();
        
        initStations();
        setLandscape();
        setObstacles();
        
        Economy economy = createEconomy();
        		        
    	return new Level(9, this.board, createStartLocation(), this.root, economy);
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

	private void initStations() {
		ArrayList<Station> stations = new ArrayList<Station>();
		Station stationToAdd = new Station(StationType.RED, new Location(3, 5), CompassHeading.SOUTH);  
		stations.add(stationToAdd);
		stationToAdd.addExportCargo(new Cargo(CargoType.COAL));
		addImportCargo (stationToAdd, root, new Cargo(CargoType.WOOD));
		
		stationToAdd = new Station(StationType.GREEN, new Location(1, 11), CompassHeading.NORTH);  
		stations.add(stationToAdd);
		stationToAdd.addExportCargo(new Cargo(CargoType.COTTON));
		addImportCargo (stationToAdd, root, new Cargo(CargoType.COAL));
		
		stationToAdd = new Station(StationType.RED, new Location(6, 7), CompassHeading.WEST);  
		stations.add(stationToAdd);
		stationToAdd.addExportCargo(new Cargo(CargoType.WOOD));
		addImportCargo (stationToAdd, root, new Cargo(CargoType.COTTON));
        
        setStations(stations);
	}

	private HashMap<TrackType, Integer> createTrackLimits() {        
		HashMap<TrackType, Integer> trackLimits = new HashMap<TrackType,Integer>();

		trackLimits.put(TrackType.TRACK, 45);
        trackLimits.put(TrackType.STRAIGHT, 20);
        trackLimits.put(TrackType.CURVE, 20);
        trackLimits.put(TrackType.INTERSECTION, 5);
        trackLimits.put(TrackType.SWITCH, 5);
        trackLimits.put(TrackType.STRAIGHT_TRACK, NO_ECONOMY_LIMIT);
        trackLimits.put(TrackType.DIAGONAL_TRACK, NO_ECONOMY_LIMIT);
        trackLimits.put(TrackType.CURVELEFT_TRACK, 15);
        trackLimits.put(TrackType.CURVERIGHT_TRACK, 15);
        trackLimits.put(TrackType.INTERSECTION_TRACK, 5);
        trackLimits.put(TrackType.DIAGONAL_INTERSECTION_TRACK, 5);
        trackLimits.put(TrackType.CURVELEFT_STRAIGHT_SWITCH, 5);
        trackLimits.put(TrackType.CURVERIGHT_STRAIGHT_SWITCH, 5);
		return trackLimits;
        
	}

	private void setLandscape() {
		LandscapeType water = LandscapeType.WATER;
		LandscapeType darkSwamp = LandscapeType.DARKSWAMP;
		
		setLandscapeByRow(2, 1, 12, water);
		
		setLandscapeByRow(5, 10, 14, darkSwamp);
		setLandscapeByRow(6, 10, 14, darkSwamp);
		setLandscapeByRow(7, 10, 14, darkSwamp);
		setLandscapeByRow(8, 10, 14, darkSwamp);
		

	}
	
	private void setObstacles() {
		ObstacleType trees = ObstacleType.TREES;
		ObstacleType mountains = ObstacleType.MOUNTAINS;
		ObstacleType rock = ObstacleType.ROCK;
		
		setObstaclesByRow(2, 3, 10, trees);
		setObstaclesByRow(1, 2, 8, trees);
		
		setObstaclesByRow(5, 0, 2, mountains);
		setObstaclesByRow(6, 0, 2, mountains);
		
		setObstaclesByRow(4, 10, 14, rock);
		setObstaclesByColumn(5, 8, 9, rock);
        
	}

}