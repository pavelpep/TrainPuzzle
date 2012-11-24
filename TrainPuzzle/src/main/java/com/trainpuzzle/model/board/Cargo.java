package com.trainpuzzle.model.board;

public class Cargo implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	public enum CargoType {
		COTTON("Cotton"),
		IRON("Iron"),
		WOOD("Wood"),
		COAL("Coal"),
		STEEL("Steel");
				
		private String name;
		
		private CargoType(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
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
	
	public void incrementCargo() {
		this.numberOfCargo++;
	}
	
	public void decrementCargo() {
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
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Cargo other = (Cargo) obj;
		return other.getType() ==cargoType;
	}
	
}