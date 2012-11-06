package com.trainpuzzle.model.board;


public class Cargo implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	public enum CargoType {
		COTTON,
		IRON,
		STEEL
	}
	
	private CargoType cargo;


	public Cargo(CargoType type) {
		this.cargo = type;
		
	}
	
	public CargoType getType() {
		return this.cargo;
	}
	
	public void setType(CargoType cargo) {
		this.cargo = cargo;
	}
	
}