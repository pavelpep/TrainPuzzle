package com.trainpuzzle.controller;

import com.trainpuzzle.model.board.Station;
import java.util.ArrayList;

public class CargoGenerator implements Runnable{
	private Simulator simulator; 
	
	public CargoGenerator(Simulator simulator) {
		this.simulator = simulator;
	}
	
	public void run(){
		ArrayList<Station> cargoFactory = new ArrayList<Station>();
		cargoFactory = simulator.getBoard().getCargoFactories();
		for (Station station: cargoFactory){
			station.generateExportCargo();
		}
	}
			
}
