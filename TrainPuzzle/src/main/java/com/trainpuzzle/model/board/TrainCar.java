package com.trainpuzzle.model.board;

import java.util.Iterator;
import java.util.LinkedList;

public class TrainCar {

	private Location location; 
	private CompassHeading heading;
	//private Cargo cargo;
	LinkedList<Cargo> cargoesOnCar = new LinkedList<Cargo>();
	
	public LinkedList<Cargo> getCargoesOnCar() {
		return cargoesOnCar;
	}

	public boolean hasCargo(){
		return !this.cargoesOnCar.isEmpty();
	}

	public TrainCar() {
		
	}
	
	public TrainCar(Location location, CompassHeading heading) {	
		setLocation(location);
		setHeading(heading);
	}
	
/*	public boolean hasCargo() {
		return cargo != null;
	}
	
	public void addCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	public Cargo dropCargo() {
		Cargo currentCargo = cargo;
		cargo = null;
		
		return currentCargo;
	}*/

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
/*	public Cargo getCargo() {
		return cargo;
	}
}*/

	public void loadCargoes(LinkedList<Cargo> cargoesToLoad){
		Cargo cargoOnCar = null;
		for(Cargo cargo : cargoesToLoad){
			cargoOnCar = getCargoFromCar(cargo);
			if (!cargoOnCar.equals(null)){
				cargoOnCar.incrementCargo();
			}
			else this.cargoesOnCar.addLast(cargo);
		}
	}

	public LinkedList<Cargo> unloadCaroges(LinkedList<Cargo> cargoesWanted){
		Cargo cargoOnCar = null;
		LinkedList<Cargo> cargoesStillWanted = cargoesWanted;
		if (cargoesStillWanted.isEmpty()) return cargoesStillWanted;
		for (Cargo cargoWanted: cargoesStillWanted){
				cargoOnCar = getCargoFromCar(cargoWanted);
				if (!cargoOnCar.equals(null)){
				cargoOnCar.decrementCargo();
				cargoWanted.decrementCargo();
				this.cargoesOnCar = removeNullCargoInList(this.cargoesOnCar,cargoOnCar);
				cargoesStillWanted = removeNullCargoInList(cargoesStillWanted, cargoWanted);
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

	private Cargo getCargoFromCar(Cargo wantedCargo) {
		Cargo cargoInList = null;
		Iterator<Cargo> iterator = this.cargoesOnCar.iterator();
		while (iterator.hasNext()){
			cargoInList = iterator.next();
			if (cargoInList.getType() == wantedCargo.getType()){
				break;
			}
		}
		return cargoInList;		
	}

}