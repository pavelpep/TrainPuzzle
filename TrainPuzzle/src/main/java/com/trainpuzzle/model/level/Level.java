package com.trainpuzzle.model.level;

import com.trainpuzzle.model.map.*;


public class Level {
	private Board map;
	private Location startLocation;
	private Location endLocation;
	
	/* Public Interface */
	
	public Level(int levelNumber) {
		
		switch(levelNumber) {
			case 1:
				createLevelOne();
				break;
			default:
				createLevelOne(); //since we only have one level
				break;
		} 
	}
	
	/* Private Functions */
	
	private void createLevelOne() {
		map = new Board();
		addSomeWaterTiles();
		addSomeTrackTiles();
		startLocation = new Location(4,0);
		endLocation = new Location(4,19);
	}
	
	private void addSomeWaterTiles() {
		
		map.getTile(10, 10).setLandscapeType("water");
		map.getTile(5, 5).setLandscapeType("water");
		map.getTile(2, 3).setLandscapeType("water");
		
	}
	
	private void addSomeTrackTiles() {
		/*
		for(int column = 0; column < 20; column++){
			map.getTile(4,column).setTrack(new Track());
		}
		*/
		
		map.getTile(4,0).setTrack(new Track());
		map.getTile(4,1).setTrack(new Track());
		map.getTile(4,2).setTrack(new Track(new Connection(Heading.WEST, Heading.SOUTHEAST)));
		map.getTile(5,3).setTrack(new Track(new Connection(Heading.NORTHWEST, Heading.SOUTHEAST)));
		map.getTile(6,4).setTrack(new Track(new Connection(Heading.NORTHWEST, Heading.SOUTHEAST)));
		map.getTile(7,5).setTrack(new Track(new Connection(Heading.NORTHWEST, Heading.EAST)));
		map.getTile(7,6).setTrack(new Track(new Connection(Heading.WEST, Heading.EAST)));
		map.getTile(7,7).setTrack(new Track(new Connection(Heading.WEST, Heading.EAST)));
		map.getTile(7,8).setTrack(new Track(new Connection(Heading.WEST, Heading.EAST)));
		map.getTile(7,9).setTrack(new Track(new Connection(Heading.WEST, Heading.EAST)));
		map.getTile(7,10).setTrack(new Track(new Connection(Heading.WEST, Heading.NORTHEAST)));
		map.getTile(6,11).setTrack(new Track(new Connection(Heading.SOUTHWEST, Heading.NORTHEAST)));
		map.getTile(5,12).setTrack(new Track(new Connection(Heading.SOUTHWEST, Heading.NORTHEAST)));
		map.getTile(4,13).setTrack(new Track(new Connection(Heading.SOUTHWEST, Heading.EAST)));
		map.getTile(4,14).setTrack(new Track());
		map.getTile(4,15).setTrack(new Track());
		map.getTile(4,16).setTrack(new Track());
		map.getTile(4,17).setTrack(new Track());
		map.getTile(4,18).setTrack(new Track());
		map.getTile(4,19).setTrack(new Track());
		
		
		
		
		
	}
	
	/* Setters and Getters */
	
	public Board getMap() {
		return map;
	}
	
	public void setMap(Board map) {
		this.map = map;
	}
	
	public Location getStartLocation() {
		return this.startLocation;
	}
	
	public Location getEndLocation() {
		return this.endLocation;
	}	
}