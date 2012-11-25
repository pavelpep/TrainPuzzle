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

public class LevelSix extends LevelOutline {
	
	
	@Override
	public Level createLevel() {
		this.board = new Board(15, 15, LandscapeType.DIRT);
        this.root = new AndVictoryCondition();
        
        initStations();
        setLandscape();
        setObstacles();
        
        Economy economy = createEconomy();
        		        
    	return new Level(6, this.board, createStartLocation(), this.root, economy);
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
        stations.add(new Station(StationType.GREEN, new Location(8, 3), CompassHeading.WEST));
        stations.add(new Station(StationType.RED, new Location(1, 7), CompassHeading.SOUTH));

        stations.add(new Station(StationType.RED, new Location(13, 10), CompassHeading.NORTH));
        stations.add(new Station(StationType.RED, new Location(8, 14), CompassHeading.NORTH));
        
        stations.add(new Station(StationType.RED, new Location(0, 13), CompassHeading.SOUTH));
        
        setStations(stations);
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

        /*/
        ArrayList<Location> rockLocations = new ArrayList<Location>();
        rockLocations.add(new Location(4, 5));
        rockLocations.add(new Location(3, 2));
        setObstacles(rockLocations, ObstacleType.ROCK);
        */
	}

}
