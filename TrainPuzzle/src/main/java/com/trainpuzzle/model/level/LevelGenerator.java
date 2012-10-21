package com.trainpuzzle.model.level;

import com.trainpuzzle.model.map.Board;
import com.trainpuzzle.model.map.CompassHeading;
import com.trainpuzzle.model.map.Connection;
import com.trainpuzzle.model.map.Location;
import com.trainpuzzle.model.map.Obstacle;
import com.trainpuzzle.model.map.Station;
import com.trainpuzzle.model.map.StationTrackPosition;
import com.trainpuzzle.model.map.Track;
import com.trainpuzzle.model.map.Landscape.LandscapeType;
import com.trainpuzzle.model.map.Station.StationType;

public class LevelGenerator {

    private Board map;
    private Level level;
    private Location startLocation;
    private Location endLocation;
    private Economy economy;

    public LevelGenerator() {
    	
    }
    
    public Level createLevelOne() {
		this.map = new Board();
		addStationTiles();
		addSomeWaterTiles();
		addSomeTrackTiles();
		addSomeObstacles();
		this.startLocation = new Location(4,0);
		this.endLocation = new Location(4,19);
		this.economy = new Economy();
    	this.level = new Level(1, this.map, this.startLocation, this.endLocation, this.economy);
    	return this.level;
	}
	
	private void addStationTiles() {
		this.map.getTile(6, 8).setStation(new Station(StationType.GREEN_FRONT, new Location(6,8), StationTrackPosition.SOUTH));
		this.map.getTile(4, 3).setStation(new Station(StationType.RED_FRONT, new Location(4,3), StationTrackPosition.SOUTH));
	}
	
	private void addSomeWaterTiles() {
		this.map.getTile(10, 10).setLandscapeType(LandscapeType.WATER);
		this.map.getTile(5, 5).setLandscapeType(LandscapeType.WATER);
		this.map.getTile(2, 3).setLandscapeType(LandscapeType.WATER);	
	}
	
	private void addSomeObstacles() {
		// BUGBUG random obstacle not being set to right tile 
		this.map.getTile(7, 7).setObstacle(new Obstacle(Obstacle.ObstacleType.TREES));
		this.map.getTile(7, 8).setObstacle(new Obstacle(Obstacle.ObstacleType.ROCK));
	}
	
	private void addSomeTrackTiles() {
		this.map.getTile(4,0).setTrack(new Track());
		this.map.getTile(4,1).setTrack(new Track());
		this.map.getTile(4,2).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.SOUTHEAST)));
		this.map.getTile(5,3).setTrack(new Track(new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTHEAST)));
		this.map.getTile(6,4).setTrack(new Track(new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTHEAST)));
		this.map.getTile(7,5).setTrack(new Track(new Connection(CompassHeading.NORTHWEST, CompassHeading.EAST)));
		this.map.getTile(7,6).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.EAST)));
		this.map.getTile(7,7).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.EAST)));
		this.map.getTile(7,8).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.EAST)));
		this.map.getTile(7,9).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.EAST)));
		this.map.getTile(7,10).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.NORTHEAST)));
		this.map.getTile(6,11).setTrack(new Track(new Connection(CompassHeading.SOUTHWEST, CompassHeading.NORTHEAST)));
		this.map.getTile(5,12).setTrack(new Track(new Connection(CompassHeading.SOUTHWEST, CompassHeading.NORTHEAST)));
		this.map.getTile(4,13).setTrack(new Track(new Connection(CompassHeading.SOUTHWEST, CompassHeading.EAST)));
		this.map.getTile(4,14).setTrack(new Track());
		this.map.getTile(4,15).setTrack(new Track());
		this.map.getTile(4,16).setTrack(new Track());
		this.map.getTile(4,17).setTrack(new Track());
		this.map.getTile(4,18).setTrack(new Track());
		this.map.getTile(4,19).setTrack(new Track());
	}
	 
    
}