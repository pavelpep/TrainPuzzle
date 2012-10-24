package com.trainpuzzle.model.level;

import com.trainpuzzle.model.board.*;
import com.trainpuzzle.model.board.Obstacle.ObstacleType;
import com.trainpuzzle.model.board.Station.StationType;
import com.trainpuzzle.model.level.victory_condition.*;

public class Level {
	private int levelNumber = 1;
	private Board board = new Board();
	private Location startLocation = new Location(0,0);
	private Location endLocation = new Location(0,1);
	private VictoryConditionEvaluator victoryConditions;
	private Economy economy = new Economy();
	
		
	public Level(int levelNumber) {
		this.levelNumber = levelNumber;
		initializeEmptyVictoryConditions();
	}
	
	public Level(int levelNumber, Board board, Location startLocation, Location endLocation, Economy economy) {
		this.levelNumber = levelNumber;
		this.board = board;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		initializeEmptyVictoryConditions();
		this.economy = economy;
	}
	
	private void initializeEmptyVictoryConditions() {
		Station placeholderStation = new Station(StationType.GREEN, endLocation, CompassHeading.NORTH);
		setVictoryConditions(new LeafVictoryCondition(new Event(1, placeholderStation,"")));
	}
	
	/* 
	 * **********************
	 * Getters & Setters	*
	 * **********************
	 */
	
	public Board getBoard() {
		return board;
	}
	
	public void setBoard(Board board) {
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
	
	public void setVictoryConditions(VictoryCondition victoryConditions) {
		this.victoryConditions = new VictoryConditionEvaluator(victoryConditions);
	}

	public Economy getEconomy() {
		return economy;
	}
	
	public void setEconomy(Economy economy) {
		this.economy = economy;
	}
}