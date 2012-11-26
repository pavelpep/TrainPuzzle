package com.trainpuzzle.factory.level_strategy;

import java.util.ArrayList;
import java.util.HashMap;

import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.board.TrackType;
import com.trainpuzzle.model.board.Landscape.LandscapeType;
import com.trainpuzzle.model.board.Obstacle.ObstacleType;
import com.trainpuzzle.model.board.Station.StationType;
import com.trainpuzzle.model.level.Economy;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.level.victory_condition.AndVictoryCondition;


public class LevelEight extends LevelOutline {

	/*
	private void setObstacles() {
		
		setObstaclesByRow(2, 0, 2, ObstacleType.MOUNTAINS);
		setObstaclesByRow(3, 0, 1, ObstacleType.MOUNTAINS);
		
		setObstaclesByRow(9, 1, 1, ObstacleType.MOUNTAINS);
		setObstaclesByRow(10, 1, 4, ObstacleType.MOUNTAINS);
		setObstaclesByColumn(7, 7, 4, ObstacleType.TREES);
		
		setObstaclesByColumn(0, 5, 4, ObstacleType.MOUNTAINS);
		setObstaclesByColumn(5, 6, 5, ObstacleType.MOUNTAINS);
		
		setObstaclesByColumn(9, 9, 5, ObstacleType.TREES);
		
		setObstaclesByColumn(6, 8, 6, ObstacleType.MOUNTAINS);
		setObstaclesByColumn(10, 14, 6, ObstacleType.MOUNTAINS);
		setObstaclesByColumn(10, 14, 7, ObstacleType.MOUNTAINS);
		
		setObstaclesByColumn(3, 8, 8, ObstacleType.MOUNTAINS);
		
		setObstaclesByRow(3, 11, 14, ObstacleType.MOUNTAINS);
		setObstaclesByRow(7, 11, 11, ObstacleType.TREES);
		 
        
        ArrayList<Location> rockLocations = new ArrayList<Location>();
        rockLocations.add(new Location(4, 5));
        rockLocations.add(new Location(3, 2));
        setObstacles(rockLocations, ObstacleType.ROCK);
        
	}
	
	*/
	
	@Override
	public Level createLevel() {
		this.board = new Board(11, 15);
        this.root = new AndVictoryCondition();
        
        initStations();
        setObstacles();
        
        Economy economy = createEconomy();
        		        
    	return new Level(8, this.board, createStartLocation(), this.root, economy);
	}

	private Economy createEconomy() {
		HashMap<TrackType, Integer> trackLimits = createTrackLimits();

        Economy economy = new Economy(NO_ECONOMY_LIMIT, trackLimits);
		return economy;
	}

	private Location createStartLocation() {
		Location startLocation = new Location(5,0);
        setStartLocation(startLocation, CompassHeading.WEST, CompassHeading.EAST, TrackType.STRAIGHT_TRACK);
		return startLocation;
	}

	private void initStations() {
		ArrayList<Station> stations = new ArrayList<Station>();
        stations.add(new Station(StationType.RED, new Location(4, 14), CompassHeading.SOUTH));
        
        setStations(stations);
	}

	private HashMap<TrackType, Integer> createTrackLimits() {        
		HashMap<TrackType, Integer> trackLimits = new HashMap<TrackType,Integer>();
        trackLimits.put(TrackType.TRACK, 15);
        trackLimits.put(TrackType.STRAIGHT, 5);
        trackLimits.put(TrackType.CURVE, 5);
        trackLimits.put(TrackType.INTERSECTION, 1);
        trackLimits.put(TrackType.SWITCH, 1);
        trackLimits.put(TrackType.STRAIGHT_TRACK, 5);
        trackLimits.put(TrackType.DIAGONAL_TRACK, 5);
        trackLimits.put(TrackType.CURVELEFT_TRACK, 5);
        trackLimits.put(TrackType.CURVERIGHT_TRACK, 5);
        trackLimits.put(TrackType.INTERSECTION_TRACK, 1);
        trackLimits.put(TrackType.DIAGONAL_INTERSECTION_TRACK, 1);
        trackLimits.put(TrackType.CURVELEFT_STRAIGHT_SWITCH, 1);
        trackLimits.put(TrackType.CURVERIGHT_STRAIGHT_SWITCH, 1);
        
        return trackLimits;
	}

	private void setObstacles() {
		
		setObstaclesByColumn(2, 3, 6, ObstacleType.TREES);
		
		
		setObstaclesByRow(1, 4, 9, ObstacleType.TREES);
		setObstaclesByRow(9, 11, 9, ObstacleType.TREES);
		setObstaclesByRow(1, 4, 10, ObstacleType.TREES);
		setObstaclesByRow(6, 7, 10, ObstacleType.TREES);
        
        /*
        ArrayList<Location> rockLocations = new ArrayList<Location>();
        rockLocations.add(new Location(4, 5));
        rockLocations.add(new Location(3, 2));
        setObstacles(rockLocations, ObstacleType.ROCK);
        */
	}

}

