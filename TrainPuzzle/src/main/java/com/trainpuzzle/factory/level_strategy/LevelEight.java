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

	@Override
	public Level createLevel() {
		this.board = new Board(11, 13);
        this.root = new AndVictoryCondition();
        root.setDefaultModel();
        
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
        stations.add(new Station(StationType.RED, new Location(4, 12), CompassHeading.SOUTH));
        
        setStations(stations);
	}

	private HashMap<TrackType, Integer> createTrackLimits() {        
		HashMap<TrackType, Integer> trackLimits = new HashMap<TrackType,Integer>();
        trackLimits.put(TrackType.TRACK, 25);
        trackLimits.put(TrackType.STRAIGHT, 20);
        trackLimits.put(TrackType.CURVE, 20);
        trackLimits.put(TrackType.INTERSECTION, 3);
        trackLimits.put(TrackType.SWITCH, 3);
        trackLimits.put(TrackType.STRAIGHT_TRACK, 12);
        trackLimits.put(TrackType.DIAGONAL_TRACK, 12);
        trackLimits.put(TrackType.CURVELEFT_TRACK, 12);
        trackLimits.put(TrackType.CURVERIGHT_TRACK, 12);
        trackLimits.put(TrackType.INTERSECTION_TRACK, 2);
        trackLimits.put(TrackType.DIAGONAL_INTERSECTION_TRACK, 2);
        trackLimits.put(TrackType.CURVELEFT_STRAIGHT_SWITCH, 2);
        trackLimits.put(TrackType.CURVERIGHT_STRAIGHT_SWITCH, 2);
        
        return trackLimits;
	}

	private void setObstacles() {
		ObstacleType trees = ObstacleType.TREES;
		
		setObstaclesByColumn(2, 4, 3, trees);
		setObstaclesByColumn(6, 7, 3, trees);
		setObstaclesByColumn(7, 8, 5, trees);
		setObstaclesByColumn(3, 4, 7, trees);
		setObstaclesByColumn(7, 8, 7, trees);
		setObstaclesByColumn(5, 7, 8, trees);
		setObstaclesByColumn(2, 4, 9, trees);
		setObstaclesByColumn(2, 3, 11, trees);
		
		setObstaclesByRow(1, 7, 10, trees);
		setObstaclesByRow(2, 5, 6, trees);
		setObstaclesByRow(5, 4, 6, trees);
		setObstaclesByRow(9, 0, 3, trees);
		setObstaclesByRow(9, 8, 10, trees);
		setObstaclesByRow(10, 0, 3, trees);
		setObstaclesByRow(10, 5, 6, trees);
        
        
        ArrayList<Location> treesLocations = new ArrayList<Location>();
        treesLocations.add(new Location(0, 1));
        treesLocations.add(new Location(0, 4));
        treesLocations.add(new Location(0, 6));
        treesLocations.add(new Location(0, 11));
        treesLocations.add(new Location(2, 2));
        treesLocations.add(new Location(2, 8));
        treesLocations.add(new Location(3, 4));
        treesLocations.add(new Location(4, 5));
        treesLocations.add(new Location(5, 10));
        treesLocations.add(new Location(6, 4));
        treesLocations.add(new Location(6, 9));
        treesLocations.add(new Location(7, 11));
        treesLocations.add(new Location(8, 1));
        treesLocations.add(new Location(8, 10));
        treesLocations.add(new Location(9, 6));
        setObstacles(treesLocations, trees);
        
	}

}

