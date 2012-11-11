package com.trainpuzzle.model.board;


public class Cargo implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	public enum CargoType {
		COTTON,
		IRON,
		WOOD
	}
	
	private CargoType cargoType;
	private int numberOfCargo = 1; 


	public Cargo(CargoType type) {
		this.cargoType = type;
	}
	
	public Cargo(CargoType type, int numberOfCargo) {
		this.cargoType = type;
		this.numberOfCargo = numberOfCargo;
	}
	
	public int getNumberOfCargo() {
		return numberOfCargo;
	}
	

	public void setNumberOfCargo(int numberOfCargo) {
		this.numberOfCargo = numberOfCargo;
	}
	
	public void incrementCargo(){
		this.numberOfCargo++;
	}
	
	public void decrementCargo(){
		this.numberOfCargo--;
	}	
	
	public CargoType getType() {
		return this.cargoType;
	}
	
	public void setType(CargoType cargo) {
		this.cargoType = cargo;
	}

	@Override
	public int hashCode() {
		return cargoType.ordinal();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean isEqual = this.cargoType.equals(((Cargo)obj).cargoType) && 
				this.numberOfCargo == ((Cargo)obj).numberOfCargo;
		return isEqual;
	}
	
}