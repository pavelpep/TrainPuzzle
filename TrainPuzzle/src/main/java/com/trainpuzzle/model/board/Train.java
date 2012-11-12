package com.trainpuzzle.model.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.trainpuzzle.observe.Observable;
import com.trainpuzzle.observe.Observer;

public class Train implements Observable{
 
	private Location location = new Location(0,0); 
	private CompassHeading heading = CompassHeading.EAST;
	Set<Observer> observerList = new HashSet<Observer>();
	List<TrainCar> trainCars = new ArrayList<TrainCar>();
	private HashMap<Cargo.CargoType, Integer> numOfCargoes = new HashMap<Cargo.CargoType, Integer>();
	
	
	
	/* Public Interface */

	public Train() {
		add3Cars();
		initializeNumCargoes();
	}
	
	public Train(Location location, CompassHeading heading) {	
		this.location = location;
		this.heading = heading;
		add3Cars();
		initializeNumCargoes();
	}
	
	public void initializeNumCargoes(){
		this.numOfCargoes.put(Cargo.CargoType.COTTON, 0);
		this.numOfCargoes.put(Cargo.CargoType.IRON, 0);
		this.numOfCargoes.put(Cargo.CargoType.WOOD, 0);
	}
	
	public void add3Cars(){
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
	
	public HashMap<Cargo.CargoType, Integer> getNumOfCargoes() {
		return numOfCargoes;
	}
	
	private void moveCars(){
		int lastCar = trainCars.size() - 1;
		
		for(int i = 0; i < lastCar; i++){
			trainCars.get(i).setLocation(trainCars.get(i+1).getLocation());
			trainCars.get(i).setHeading(trainCars.get(i+1).getHeading());
		}
		
		trainCars.get(lastCar).setLocation(this.location);
		trainCars.get(lastCar).setHeading(this.heading);
	}
	
	public List<Cargo> dropOff(List<Cargo> requestedCargo){
		List<Cargo>  cargoDroppedOff = new ArrayList<Cargo>();
		
		for(Cargo cargo: requestedCargo){
			for(TrainCar trainCar: trainCars){
				if(trainCar.hasCargo()){
					if(trainCar.getCargo().getType() == cargo.getType()){
						Cargo cargoDropped = trainCar.dropCargo();
						decrementNumber(cargoDropped);
						cargoDroppedOff.add(cargoDropped);
					}
				}
			}
		}
		return cargoDroppedOff; 
	}
	
	private void decrementNumber(Cargo cargo){
		Integer currentNumOfCargoes = 0; 
		currentNumOfCargoes = numOfCargoes.get(cargo.getType());
		currentNumOfCargoes = currentNumOfCargoes - 1;		
		numOfCargoes.put(cargo.getType(), currentNumOfCargoes);
		System.out.println("currentNumOfCargo=" + cargo.getType() + numOfCargoes.get(cargo.getType()));	
	}
		
	public List<Cargo> pickUp(List<Cargo> availableCargo){
		List<Cargo>  cargoPickedUp = new ArrayList<Cargo>();
		
		for(Cargo cargo: availableCargo){
			for(TrainCar trainCar: trainCars){
				if(trainCar.hasCargo() == false){
						Cargo cargoTaken = new Cargo(cargo.getType());
						cargoPickedUp.add(cargoTaken);
						trainCar.addCargo(cargoTaken);
						incrementNumber(cargoTaken);
						break;
					}
				}
		}
		return cargoPickedUp; 
	}
	
	
	private void incrementNumber(Cargo cargo){
		Integer currentNumOfCargoes = 0; 
		currentNumOfCargoes = numOfCargoes.get(cargo.getType());
		currentNumOfCargoes = currentNumOfCargoes + 1;		
		numOfCargoes.put(cargo.getType(), currentNumOfCargoes);
		System.out.println("currentNumOfCargo=" + cargo.getType() + numOfCargoes.get(cargo.getType()));
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