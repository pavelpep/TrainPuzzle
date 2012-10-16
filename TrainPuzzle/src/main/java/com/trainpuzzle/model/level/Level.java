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
		for(int column = 0; column < 20; column++){
		 map.getTile(4,column).setTrack(new Track());
		}
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