package com.trainpuzzle.model.level;

import java.util.LinkedList;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.trainpuzzle.controller.CargoRequestGenerator;
import com.trainpuzzle.controller.CargoGenerator;
import com.trainpuzzle.controller.Simulator;
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
	private List<CargoRequestGenerator> cargorequestors = new LinkedList<CargoRequestGenerator>();
	private int timeLimit = Simulator.NOT_IME_LIMIT;

	private List<CargoGenerator> cargoGenerators = new LinkedList<CargoGenerator>(); 
	
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

	//The number of cargoRquestors can not be more than 2
	//There can not be simultaneously a cargoGenerator and a cragoRequestor at a same station
	//Don't add two same types of requestors at a same station, such as two ironFactory generators  
	public void addCargoRequestGenerator(CargoRequestGenerator newRequestor) {
		Station station = newRequestor.getStation();
		int numOfCargoGenerator = station.getNumOfCargoGenerator();
		int numOfCargoRequestor = station.getNumOfCargoRequestor();
		if (numOfCargoRequestor<2 && numOfCargoGenerator==0){
			this.cargorequestors.add(newRequestor);
			numOfCargoRequestor = numOfCargoRequestor + 1;
			station.setNumOfCargoRequestor(numOfCargoRequestor);			
			}
	}
	
	//The number of cargoGenerators can not be more than 2
	//There can not be simultaneously a cargoGenerator and a cragoRequestor at a same station
	//Don't add two same types of generators at a same station, such as two ironFactory generators  
	public void addCargoGenerator(CargoGenerator newGenerator) {
		Station station = newGenerator.getStation();
		int numOfCargoGenerator = station.getNumOfCargoGenerator();
		int numOfCargoRequestGenerator = station.getNumOfCargoRequestor();
		if (numOfCargoGenerator<2 && numOfCargoRequestGenerator==0){
			this.cargoGenerators.add(newGenerator);
			numOfCargoGenerator = numOfCargoGenerator + 1;
			station.setNumOfCargoGenerator(numOfCargoGenerator);			
			}
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

	public List<CargoRequestGenerator> getCargorequestors() {
		return cargorequestors;
	}
	
	public List<CargoGenerator> getCargoGenerators() {
		return cargoGenerators;
	}

	public void setCargorequestors(List<CargoRequestGenerator> cargorequestors) {
		this.cargorequestors = cargorequestors;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

}