package com.trainpuzzle.model.board;

import java.util.Iterator;
import java.util.LinkedList;

public class TrainCar {

	private Location location; 
	private CompassHeading heading;
	private Cargo cargo;
	//LinkedList<Cargo> cargoesOnCar = new LinkedList<Cargo>();


	public TrainCar() {
		
	}
	
	public TrainCar(Location location, CompassHeading heading) {	
		setLocation(location);
		setHeading(heading);
	}
	
	public boolean hasCargo() {
		return cargo != null;
	}
	
	public void addCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	public Cargo dropCargo() {
		Cargo currentCargo = cargo;
		cargo = null;
		
		return currentCargo;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public CompassHeading getHeading() {
		return heading;
	}
	
	public void setHeading(CompassHeading heading) {
		this.heading = heading;
	}	
	public Cargo getCargo() {
		return cargo;
	}
}

	/*public void loadCargoes(LinkedList<Cargo> cargoesToLoad){
		Cargo cargoOnTrain = null;
		for(Cargo cargo : cargoesToLoad){
			if (this.cargoesOnCar.contains(cargo)){
				cargoOnTrain = getCargoFromList(this.cargoesOnCar,cargo);
				cargoOnTrain.incrementCargo();
			}
			this.cargoesOnCar.addLast(cargo);
		}
	}

	public LinkedList<Cargo> unloadCaroges(LinkedList<Cargo> cargoesWanted){
		Cargo cargoOnTrain = null;
		LinkedList<Cargo> cargoesStillWanted = cargoesWanted;
		if (cargoesStillWanted.isEmpty()) return cargoesStillWanted;
		for (Cargo cargoWanted: cargoesWanted){
			if (this.cargoesOnCar.contains(cargoWanted)){
				cargoOnTrain = getCargoFromList(this.cargoesOnCar,cargoWanted);
				cargoOnTrain.decrementCargo();
				cargoWanted.decrementCargo();
				removeNullCargoInList(this.cargoesOnCar,cargoOnTrain);
				removeNullCargoInList(cargoesStillWanted, cargoWanted);
			}
		}
		return cargoesStillWanted;
	}



	private LinkedList<Cargo> removeNullCargoInList(LinkedList<Cargo> cargoesList,
			Cargo cargo) {
		if (cargo.getNumberOfCargo() < 1)  {
			cargoesList.remove(cargo);
		}
		return cargoesList;
	}

	private Cargo getCargoFromList(LinkedList<Cargo> cargoesList, Cargo wantedCargo) {
		Cargo cargoOnTrain = null;
		Iterator<Cargo> iterator = cargoesList.iterator();
		while (iterator.hasNext()){
			cargoOnTrain = iterator.next();
			if (cargoOnTrain.equals(wantedCargo)){
				break;
			}
		}
		return cargoOnTrain;		
	}*/
