package com.trainpuzzle.model.level;

import com.trainpuzzle.model.map.*;

public class Level {
	private Board map;
	private Location startLocation;
	private Location endLocation;
	private int levelNumber;
	
	/* Public Interface */
	
	public Level(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	
	public Level(int levelNumber, Board map, Location startLocation, Location endLocation) {
		this.levelNumber = levelNumber;
		this.map = map;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
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
	
	public int getLevelNumber() {
		return this.levelNumber;
	}
	
	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}
}