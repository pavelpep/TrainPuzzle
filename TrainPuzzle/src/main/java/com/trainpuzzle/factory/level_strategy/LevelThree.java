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

public class LevelThree extends LevelOutline {
	
	
	@Override
	public Level createLevel() {
		this.board = new Board(10, 10);
        this.root = new AndVictoryCondition();
        
        initStations();
        setLandscape();
        setObstacles();
        
        Economy economy = createEconomy();
        		        
    	return new Level(3, this.board, createStartLocation(), this.root, economy);
	}

	private Economy createEconomy() {
		HashMap<TrackType, Integer> trackLimits = createTrackLimits();

        Economy economy = new Economy(NO_ECONOMY_LIMIT, trackLimits);
		return economy;
	}

	private Location createStartLocation() {
		Location startLocation = new Location(0,0);
        setStartLocation(startLocation, CompassHeading.WEST, CompassHeading.EAST, TrackType.STRAIGHT_TRACK);
		return startLocation;
	}

	private void initStations() {
		ArrayList<Station> stations = new ArrayList<Station>();
        stations.add(new Station(StationType.GREEN, new Location(7, 5), CompassHeading.EAST));
        stations.add(new Station(StationType.RED, new Location(1, 8), CompassHeading.SOUTH));
        
        setPassStations(stations);
	}

	private HashMap<TrackType, Integer> createTrackLimits() {        
		HashMap<TrackType, Integer> trackLimits = new HashMap<TrackType,Integer>();
        trackLimits.put(TrackType.TRACK, 29);
        trackLimits.put(TrackType.STRAIGHT, 10);
        trackLimits.put(TrackType.CURVE, 15);
        trackLimits.put(TrackType.INTERSECTION, 2);
        trackLimits.put(TrackType.SWITCH, 2);
        trackLimits.put(TrackType.STRAIGHT_TRACK, 10);
        trackLimits.put(TrackType.DIAGONAL_TRACK, 10);
        trackLimits.put(TrackType.CURVELEFT_TRACK, 10);
        trackLimits.put(TrackType.CURVERIGHT_TRACK, 10);
        trackLimits.put(TrackType.INTERSECTION_TRACK, 2);
        trackLimits.put(TrackType.DIAGONAL_INTERSECTION_TRACK, 2);
        trackLimits.put(TrackType.CURVELEFT_STRAIGHT_SWITCH, 2);
        trackLimits.put(TrackType.CURVERIGHT_STRAIGHT_SWITCH, 2);
        
        return trackLimits;
	}

	private void setLandscape() {
		setLandscapeByColumn(1, 4, 0, LandscapeType.WATER);
		setLandscapeByColumn(1, 2, 1, LandscapeType.WATER);
		setLandscapeByColumn(7, 7, 3, LandscapeType.WATER);
		setLandscapeByColumn(7, 8, 4, LandscapeType.WATER);
		setLandscapeByColumn(4, 8, 8, LandscapeType.WATER);
		setLandscapeByColumn(4, 8, 9, LandscapeType.WATER);
	}

	private void setObstacles() {
		setObstaclesByRow(2, 4, 6, ObstacleType.TREES);

        setObstaclesByRow(3, 4, 9, ObstacleType.TREES);
        setObstaclesByColumn(4, 5, 2, ObstacleType.TREES);
        setObstaclesByColumn(4, 5, 3, ObstacleType.TREES);
        
        ArrayList<Location> rockLocations = new ArrayList<Location>();
        rockLocations.add(new Location(4, 5));
        rockLocations.add(new Location(3, 2));
        setObstacles(rockLocations, ObstacleType.ROCK);
	}

}
