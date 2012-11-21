package com.trainpuzzle.controller;

import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.Cargo;
import com.trainpuzzle.model.board.Cargo.CargoType;
import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.level.victory_condition.IfThenVictoryCondition;
import com.trainpuzzle.model.level.victory_condition.LeafVictoryCondition;
import com.trainpuzzle.model.level.victory_condition.DropCargoEvent;
import com.trainpuzzle.model.level.victory_condition.LogicalVictoryCondition;

public class CargoRequestGenerator {
	private Station station;
	private IfThenVictoryCondition parentVictoryCondition;
	private int time; 
	private CargoType requestType;
	
	public CargoRequestGenerator(Station station, LogicalVictoryCondition condition, int time, CargoType type) {
		this.station = station;
		this.time = time;
		this.parentVictoryCondition =new IfThenVictoryCondition();
		condition.addChild(parentVictoryCondition);
		this.requestType =type;
		changeName();
	}
	
	private void changeName() {
		Location location =station.getStationLocation();
		String name = "It generarate requesting cargo " + requestType.getName() +" station at ("+location.getRow()+","+location.getColumn()+")" + " every " +time +" game time";
		this.parentVictoryCondition.setName(name);
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
