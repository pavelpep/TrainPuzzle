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

public class LevelTwo extends LevelOutline {
	
	
	@Override
	public Level createLevel() {
        this.root = new AndVictoryCondition();
        root.setDefaultModel();

        initStations();
        setLandscape();
        setObstacles();
        
        Economy economy = createEconomy();
        		        
    	return new Level(2, this.board, createStartLocation(), this.root, economy);
	}

	private Economy createEconomy() {
		HashMap<TrackType, Integer> trackLimits = createTrackLimits();

        Economy economy = new Economy(NO_ECONOMY_LIMIT, trackLimits);
		return economy;
	}

	private Location createStartLocation() {
		Location startLocation = new Location(4,3);
        setStartLocation(startLocation, CompassHeading.WEST, CompassHeading.EAST, TrackType.STRAIGHT_TRACK);
		return startLocation;
	}

	private void initStations() {
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
	}

	private HashMap<TrackType, Integer> createTrackLimits() {        
		HashMap<TrackType, Integer> trackLimits = new HashMap<TrackType,Integer>();
        trackLimits.put(TrackType.TRACK, 37);
        trackLimits.put(TrackType.STRAIGHT, 18);
        trackLimits.put(TrackType.CURVE, 15);
        trackLimits.put(TrackType.INTERSECTION, 2);
        trackLimits.put(TrackType.SWITCH, 2);
        trackLimits.put(TrackType.STRAIGHT_TRACK, 18);
        trackLimits.put(TrackType.DIAGONAL_TRACK, 18);
        trackLimits.put(TrackType.CURVELEFT_TRACK, 10);
        trackLimits.put(TrackType.CURVERIGHT_TRACK, 10);
        trackLimits.put(TrackType.INTERSECTION_TRACK, 2);
        trackLimits.put(TrackType.DIAGONAL_INTERSECTION_TRACK, 2);
        trackLimits.put(TrackType.CURVELEFT_STRAIGHT_SWITCH,2);
        trackLimits.put(TrackType.CURVERIGHT_STRAIGHT_SWITCH, 2);
		return trackLimits;
	}

	private void setLandscape() {
		setLandscapeByRow(0, 5, 10, LandscapeType.WATER);
        setLandscapeByRow(1, 5, 9, LandscapeType.WATER);
        setLandscapeByRow(9, 12, 14, LandscapeType.WATER);
        setLandscapeByRow(10, 12, 15, LandscapeType.WATER);
        
	}

	private void setObstacles() {
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
        
        
	}

}
