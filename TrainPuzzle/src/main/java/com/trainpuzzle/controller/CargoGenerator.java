package com.trainpuzzle.controller;

import com.trainpuzzle.model.board.Cargo;
import com.trainpuzzle.model.board.Station;
import com.trainpuzzle.model.board.Cargo.CargoType;


public class CargoGenerator {
	private Station station;
	private int generatingInterval; 
	private CargoType generateType; 
	
	public CargoGenerator(Station station, int generatingInterval, CargoType type) {
		this.station = station;
		station.getCanGenerateCargoTypes().put(type, true);
		this.generatingInterval = generatingInterval;
		this.generateType = type;
	}
	
	public void generateCargo (int time) {
		if (time % (this.generatingInterval) == 0) {
			Cargo cargo = new Cargo(generateType);
			station.addExportCargo(cargo);
		}
	}
	
	public Station getStation() {
		return station;
	}
	

	public int getGeneratingInterval() {
		return generatingInterval;
	}

	public CargoType getGenerateType() {
		return generateType;
	}

	public boolean equals(Object obj){
		return this.station.equals(((CargoGenerator)obj).getStation())
				&& this.generateType == ((CargoGenerator)obj).getGenerateType();
	}

}
