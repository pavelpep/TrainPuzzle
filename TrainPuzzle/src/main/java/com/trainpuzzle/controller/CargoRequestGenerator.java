package com.trainpuzzle.controller;

import javax.swing.tree.DefaultMutableTreeNode;

import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.Cargo;
import com.trainpuzzle.model.board.Cargo.CargoType;
import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.level.victory_condition.IfThenVictoryCondition;
import com.trainpuzzle.model.level.victory_condition.LeafVictoryCondition;
import com.trainpuzzle.model.level.victory_condition.DropCargoEvent;
import com.trainpuzzle.model.level.victory_condition.LogicalVictoryCondition;
import com.trainpuzzle.model.level.victory_condition.TreeNodeUserObject;

public class CargoRequestGenerator {
	private Station station;
	private IfThenVictoryCondition parentVictoryCondition;
	private int time; 
	private CargoType requestType;
	
	public CargoRequestGenerator(Station station, LogicalVictoryCondition condition, int time, CargoType type) {
		this.station = station;
		station.getCargoTypeExist().put(type, true);
		this.time = time;
		this.parentVictoryCondition =new IfThenVictoryCondition();
		this.requestType =type;
		changeName();
		condition.addChild(parentVictoryCondition);
	}
	
	
	public Station getStation() {
		return station;
	}



	public CargoType getRequestType() {
		return requestType;
	}



	private void changeName() {
		Location location =station.getStationLocation();
		String name = "It generarate requesting cargo " + requestType.getName() +" station at ("+location.getRow()+","+location.getColumn()+")" + " every " +time +" game time";
		
		TreeNodeUserObject userObject = new TreeNodeUserObject(parentVictoryCondition,name);
		DefaultMutableTreeNode displayNode = new DefaultMutableTreeNode(userObject);
		parentVictoryCondition.setDisplayNode(displayNode);
		
	}
	
	public void generateRequest (int time) {
		if (time % (this.time) == 0) {
			Cargo cargo = new Cargo(requestType);
			station.addImportCargo(cargo);
			DropCargoEvent event = new DropCargoEvent(station,cargo);
			parentVictoryCondition.addChild(new LeafVictoryCondition(event));
		}
	}
	public void reset(Board board) {
		this.parentVictoryCondition.removeChildrens();
		Location location = station.getStationLocation();
		station = board.getTile(location).getStation();
	}
}
