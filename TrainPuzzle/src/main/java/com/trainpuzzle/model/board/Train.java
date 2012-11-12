package com.trainpuzzle.model.board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Iterator;

import com.trainpuzzle.observe.Observable;
import com.trainpuzzle.observe.Observer;

public class Train implements Observable{
 
	private Location location = new Location(0,0); 
	private CompassHeading heading = CompassHeading.EAST;
	Set<Observer> observerList = new HashSet<Observer>();
	List<TrainCar> trainCars = new ArrayList<TrainCar>();
	
	
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
        moveCars();
		this.location = location;
		notifyAllObservers();
	}
	
	private void moveCars(){
		trainCars.get(0).setLocation(trainCars.get(1).getLocation());
		trainCars.get(1).setLocation(trainCars.get(2).getLocation());
		trainCars.get(2).setLocation(this.location);
		
		trainCars.get(0).setHeading(trainCars.get(1).getHeading());
		trainCars.get(1).setHeading(trainCars.get(2).getHeading());
		trainCars.get(2).setHeading(this.heading);
	}
	
	public List<Cargo> dropOff(List<Cargo> requestedCargo){
		List<Cargo>  cargoDroppedOff = new ArrayList<Cargo>();
		
		
		
		return cargoDroppedOff; 
	}
	
	public List<Cargo> pickUp(List<Cargo> availableCargo){
		List<Cargo>  cargoPickedUp = new ArrayList<Cargo>();
		
		
		
		return cargoPickedUp; 
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
}	