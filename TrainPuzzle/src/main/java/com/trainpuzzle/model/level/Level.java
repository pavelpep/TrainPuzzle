package com.trainpuzzle.model.level;

import javax.swing.tree.DefaultMutableTreeNode;

import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.CompassHeading;
import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.board.Station.StationType;
import com.trainpuzzle.model.level.victory_condition.Event;
import com.trainpuzzle.model.level.victory_condition.LeafVictoryCondition;
import com.trainpuzzle.model.level.victory_condition.VictoryCondition;

public class Level implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private int levelNumber = 1;
	private Board board = new Board();
	private Location startLocation = new Location(0,0);
	private VictoryCondition victoryConditions;
	private Economy economy = new Economy();
	
	public Level(int levelNumber) {
		this.levelNumber = levelNumber;
		initializeEmptyVictoryConditions();
	}

	public Level(int levelNumber, Board board, Location startLocation, 
			VictoryCondition victoryConditions, Economy economy) {
		
		this.levelNumber = levelNumber;
		this.board = board;
		this.startLocation = startLocation;
		this.victoryConditions = victoryConditions; 
		this.economy = economy;
	}
	
	private void initializeEmptyVictoryConditions() {
		Station placeholderStation = new Station(StationType.GREEN, startLocation, CompassHeading.NORTH);
		setVictoryConditions(new LeafVictoryCondition(new Event(1, placeholderStation)));
	}
	
	 // Getters & Setters
	
	public Board getBoard() {
		return board;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public Location getStartLocation() {
		return this.startLocation;
	}
		
	public int getLevelNumber() {
		return this.levelNumber;
	}
	
	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	
	public void setVictoryConditions(VictoryCondition victoryConditions) {
		this.victoryConditions = victoryConditions;
	}
	
	public VictoryCondition getVictoryConditions() {
		return victoryConditions;
	}
	
	public Economy getEconomy() {
		return economy;
	}
	
	public void setEconomy(Economy economy) {
		this.economy = economy;
	}

}