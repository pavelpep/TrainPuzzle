package com.trainpuzzle.model.board;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Iterator;

import com.trainpuzzle.observe.Observable;
import com.trainpuzzle.observe.Observer;

public class Train implements Observable{
 
	private Location location = new Location(0,0); 
	private CompassHeading heading = CompassHeading.EAST;
	Set<Observer> observerList = new HashSet<Observer>();
	Queue<TrainCar> trainCars = new LinkedList<TrainCar>();
	LinkedList<Cargo> cargoesOnTrain = new LinkedList<Cargo>();
	
	/* Public Interface */
	
	public Train() {
		
	}
	
	public Train(Location location, CompassHeading heading) {	
		this.location = location;
		this.heading = heading;
		
		//add 3 traincars
		trainCars.add(new TrainCar(location, heading));
		trainCars.add(new TrainCar(location, heading));
		trainCars.add(new TrainCar(location, heading));
	}
	
	public void register(Observer observer){
		observerList.add(observer);
	}
	
	public void notifyAllObservers(){
		for(Observer observer : observerList) {
			observer.notifyChange(this);
		}
	}
	
	/*Getters and Setters */

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		trainCars.poll();
		trainCars.add(new TrainCar(this.location, this.heading));
		this.location = location;
		notifyAllObservers();
	}
	
	public void resetTrainCars(){
		for(TrainCar trainCar : trainCars) {
			trainCar.setLocation(this.location);
			trainCar.setHeading(this.heading);
		}
		notifyAllObservers();
	}
	
	public CompassHeading getHeading() {
		return heading;
	}
	
	public void setHeading(CompassHeading heading) {
		this.heading = heading;
		notifyAllObservers();
	}
	
	public TrainCar[] getTrainCars() {
		TrainCar[] traincars = new TrainCar[trainCars.size()];
		trainCars.toArray(traincars);
		return traincars;
	}
	
	public void loadCargoes(LinkedList<Cargo> cargoesToLoad){
		Cargo cargoOnTrain = null;
		for(Cargo cargo : cargoesToLoad){
			if (this.cargoesOnTrain.contains(cargo)){
				cargoOnTrain = getCargoFromList(this.cargoesOnTrain,cargo);
				cargoOnTrain.incrementCargo();
			}
			this.cargoesOnTrain.addLast(cargo);
		}
	}
	
	public LinkedList<Cargo> unloadCaroges(LinkedList<Cargo> cargoesWanted){
		Cargo cargoOnTrain = null;
		LinkedList<Cargo> cargoesStillWanted = cargoesWanted;
		for (Cargo cargoWanted: cargoesWanted){
			if (this.cargoesOnTrain.contains(cargoWanted)){
				cargoOnTrain = getCargoFromList(this.cargoesOnTrain,cargoWanted);
				cargoOnTrain.decrementCargo();
				cargoWanted.decrementCargo();
				removeNullCargoInList(this.cargoesOnTrain,cargoOnTrain);
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
	}
	
}