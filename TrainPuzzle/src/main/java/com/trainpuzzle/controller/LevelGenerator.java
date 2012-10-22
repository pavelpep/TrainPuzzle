package com.trainpuzzle.controller;

import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Connection;
import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Obstacle;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.board.StationTrackPosition;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.board.Landscape.LandscapeType;
import com.trainpuzzle.model.board.Station.StationType;
import com.trainpuzzle.model.level.Economy;
import com.trainpuzzle.model.level.Level;

//purposely created without the public tag because we only want this class accessible by the CampaignManager (which is in the same package)
class LevelGenerator {

    private Board board;
    private Level level;
    private Location startLocation;
    private Location endLocation;
    private Economy economy;

    public LevelGenerator() {
    }
    
    public Level createLevelOne() {
		this.board = new Board();
		addStationTiles();
		addSomeWaterTiles();
		addSomeTrackTiles();
		addSomeObstacles();
		this.startLocation = new Location(4,0);
		this.endLocation = new Location(4,19);
		this.economy = new Economy();
    	this.level = new Level(1, this.board, this.startLocation, this.endLocation, this.economy);
    	return this.level;
	}
	
	private void addStationTiles() {
		this.board.getTile(6, 8).setStation(new Station(StationType.GREEN_FRONT, new Location(6,8), StationTrackPosition.SOUTH));
		this.board.getTile(4, 3).setStation(new Station(StationType.RED_FRONT, new Location(4,3), StationTrackPosition.SOUTH));
	}
	
	private void addSomeWaterTiles() {
		this.board.getTile(10, 10).setLandscapeType(LandscapeType.WATER);
		this.board.getTile(5, 5).setLandscapeType(LandscapeType.WATER);
		this.board.getTile(2, 3).setLandscapeType(LandscapeType.WATER);	
	}
	
	private void addSomeObstacles() {
		//FIXME: random obstacle not being set to right tile 
		this.board.getTile(7, 7).setObstacle(new Obstacle(Obstacle.ObstacleType.TREES));
		this.board.getTile(7, 8).setObstacle(new Obstacle(Obstacle.ObstacleType.ROCK));
	}
	
	private void addSomeTrackTiles() {
		this.board.getTile(4,0).setTrack(new Track());
		this.board.getTile(4,1).setTrack(new Track());
		this.board.getTile(4,2).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.SOUTHEAST)));
		this.board.getTile(5,3).setTrack(new Track(new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTHEAST)));
		this.board.getTile(6,4).setTrack(new Track(new Connection(CompassHeading.NORTHWEST, CompassHeading.SOUTHEAST)));
		this.board.getTile(7,5).setTrack(new Track(new Connection(CompassHeading.NORTHWEST, CompassHeading.EAST)));
		this.board.getTile(7,6).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.EAST)));
		this.board.getTile(7,7).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.EAST)));
		this.board.getTile(7,8).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.EAST)));
		this.board.getTile(7,9).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.EAST)));
		this.board.getTile(7,10).setTrack(new Track(new Connection(CompassHeading.WEST, CompassHeading.NORTHEAST)));
		this.board.getTile(6,11).setTrack(new Track(new Connection(CompassHeading.SOUTHWEST, CompassHeading.NORTHEAST)));
		this.board.getTile(5,12).setTrack(new Track(new Connection(CompassHeading.SOUTHWEST, CompassHeading.NORTHEAST)));
		this.board.getTile(4,13).setTrack(new Track(new Connection(CompassHeading.SOUTHWEST, CompassHeading.EAST)));
		this.board.getTile(4,14).setTrack(new Track());
		this.board.getTile(4,15).setTrack(new Track());
		this.board.getTile(4,16).setTrack(new Track());
		this.board.getTile(4,17).setTrack(new Track());
		this.board.getTile(4,18).setTrack(new Track());
		this.board.getTile(4,19).setTrack(new Track());
	}
	 
    
}