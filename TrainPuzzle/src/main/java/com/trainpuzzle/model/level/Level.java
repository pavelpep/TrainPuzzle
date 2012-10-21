package com.trainpuzzle.model.level;

import com.trainpuzzle.model.map.*;

public class Level {
	private Board board;
	private Location startLocation;
	private Location endLocation;
	private int levelNumber;
	
	/* Public Interface */
	
	public Level(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	
	public Level(int levelNumber, Board board, Location startLocation, Location endLocation) {
		this.levelNumber = levelNumber;
		this.board = board;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
	}
	
	/* Setters and Getters */
	
	public Board getMap() {
		return board;
	}
	
	public void setMap(Board board) {
		this.board = board;
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