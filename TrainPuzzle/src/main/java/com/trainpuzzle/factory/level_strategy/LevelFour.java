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


public class LevelFour extends LevelOutline {
	
	
	@Override
	public Level createLevel() {
        this.board = new Board(12, 13, LandscapeType.LIGHTDIRT);
		this.root = new AndVictoryCondition();
		 root.setDefaultModel();

        initStations();
        setLandscape();
        setObstacles();
        
        Economy economy = createEconomy();
        		        
    	return new Level(4, this.board, createStartLocation(), this.root, economy);
	}

	private Economy createEconomy() {
		HashMap<TrackType, Integer> trackLimits = createTrackLimits();

        Economy economy = new Economy(NO_ECONOMY_LIMIT, trackLimits);
		return economy;
	}

	private Location createStartLocation() {
		Location startLocation = new Location(1, 3);
        setStartLocation(startLocation, CompassHeading.WEST, CompassHeading.EAST, TrackType.STRAIGHT);
		return startLocation;
	}


	private void initStations() {
		ArrayList<Station> stations = new ArrayList<Station>();
        Station stationToAdd = new Station(StationType.GREEN, new Location(11, 4), CompassHeading.NORTH);  
        stations.add(stationToAdd);
        stationToAdd.addExportCargo(new Cargo(CargoType.IRON));
        stationToAdd.addExportCargo(new Cargo(CargoType.WOOD));
        
        stationToAdd = new Station(StationType.RED, new Location(7, 3), CompassHeading.SOUTH);
        stations.add(stationToAdd);	        
        addImportCargo (stationToAdd, root, new Cargo(CargoType.IRON));
        stationToAdd.addExportCargo(new Cargo(CargoType.COTTON));
        
        stationToAdd = new Station(StationType.GREEN, new Location(6, 7), CompassHeading.EAST);
        stations.add(stationToAdd);	        
        addImportCargo (stationToAdd, root, new Cargo(CargoType.COTTON));
        addImportCargo (stationToAdd, root, new Cargo(CargoType.WOOD));
        
        setStations(stations);
	}

	private HashMap<TrackType, Integer> createTrackLimits() {        
		HashMap<TrackType, Integer> trackLimits = new HashMap<TrackType,Integer>();
        trackLimits.put(TrackType.TRACK, 107);
        trackLimits.put(TrackType.STRAIGHT, 35);
        trackLimits.put(TrackType.CURVE, 30);
        trackLimits.put(TrackType.INTERSECTION, 2);
        trackLimits.put(TrackType.SWITCH, 10);
        trackLimits.put(TrackType.STRAIGHT_TRACK, 25);
        trackLimits.put(TrackType.DIAGONAL_TRACK, 10);
        trackLimits.put(TrackType.CURVELEFT_TRACK, 15);
        trackLimits.put(TrackType.CURVERIGHT_TRACK, 15);
        trackLimits.put(TrackType.INTERSECTION_TRACK, 2);
        trackLimits.put(TrackType.DIAGONAL_INTERSECTION_TRACK, 2);
        trackLimits.put(TrackType.CURVELEFT_STRAIGHT_SWITCH,5);
        trackLimits.put(TrackType.CURVERIGHT_STRAIGHT_SWITCH, 5);
		return trackLimits;
	}

	private void setLandscape() {
		setLandscapeByColumn(3, 8, 2, LandscapeType.ROUGHDIRT);
		setLandscapeByColumn(3, 8, 3, LandscapeType.ROUGHDIRT);
		setLandscapeByColumn(3, 8, 4, LandscapeType.DIRT);
		setLandscapeByColumn(3, 8, 5, LandscapeType.DIRT);
		setLandscapeByColumn(3, 8, 9, LandscapeType.DIRT);
		
		setLandscapeByRow(2, 3, 5, LandscapeType.ROUGHDIRT);
		setLandscapeByRow(1, 5, 9, LandscapeType.ROUGHDIRT);
		setLandscapeByRow(2, 6, 9, LandscapeType.DIRT);
		setLandscapeByRow(3, 6, 9, LandscapeType.DIRT);
		setLandscapeByRow(7, 6, 9, LandscapeType.DIRT);
		setLandscapeByRow(8, 6, 9, LandscapeType.DIRT);
		
		setLandscapeByColumn(0, 7, 9, LandscapeType.ROUGHDIRT);
		setLandscapeByColumn(1, 7, 8, LandscapeType.ROUGHDIRT);
		
		setLandscapeByRow(2, 0, 1, LandscapeType.WATER);
		setLandscapeByRow(3, 0, 4, LandscapeType.WATER);
		setLandscapeByRow(4, 0, 5, LandscapeType.WATER);
		setLandscapeByRow(5, 1, 5, LandscapeType.WATER);
		setLandscapeByRow(6, 2, 6, LandscapeType.WATER);
		setLandscapeByRow(9, 4, 6, LandscapeType.WATER);
	}


	private void setObstacles() {
        setObstaclesByRow(0, 0, 7, ObstacleType.MOUNTAINS);
        setObstaclesByRow(1, 0, 2, ObstacleType.MOUNTAINS);
        setObstaclesByRow(1, 5, 8, ObstacleType.MOUNTAINS);
        setObstaclesByRow(2, 2, 3, ObstacleType.MOUNTAINS);
        
		ArrayList<Location> treeLocations = new ArrayList<Location>();
		treeLocations.add(new Location(5, 0));
		treeLocations.add(new Location(6, 0));
        treeLocations.add(new Location(5, 6));
        treeLocations.add(new Location(2, 6));
        treeLocations.add(new Location(2, 7));
        treeLocations.add(new Location(3, 7));
        treeLocations.add(new Location(4, 11));
        treeLocations.add(new Location(4, 9));
        treeLocations.add(new Location(4, 10));
        treeLocations.add(new Location(6, 9));
        treeLocations.add(new Location(9, 2));
        treeLocations.add(new Location(9, 7));
        treeLocations.add(new Location(9, 9));
        setObstacles(treeLocations, ObstacleType.TREES);
        
        ArrayList<Location> rockLocations = new ArrayList<Location>();
        rockLocations.add(new Location(6, 1));
        rockLocations.add(new Location(9, 3));
        rockLocations.add(new Location(9, 8));
        rockLocations.add(new Location(9, 10));
        setObstacles(rockLocations, ObstacleType.ROCK);
	}

}

