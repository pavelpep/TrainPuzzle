package com.trainpuzzle.model.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;

import com.trainpuzzle.model.board.Cargo.CargoType;
import com.trainpuzzle.observe.Observable;
import com.trainpuzzle.observe.Observer;

public class Train implements Observable {
 
	private Location location = new Location(0,0); 
	private CompassHeading heading = CompassHeading.EAST;
	private Set<Observer> observerList = new HashSet<Observer>();
	private Observer observerLoadedlevel;
	private List<TrainCar> trainCars = new ArrayList<TrainCar>();
	private HashMap<Cargo.CargoType, Integer> numOfCargoes = new HashMap<Cargo.CargoType, Integer>();
	
	public Train() {
		add4Cars();
		initializeNumCargoes();
	}
	
	public Train(Location location, CompassHeading heading) {	
		this();
		this.location = location;
		this.heading = heading;	
	}
	
	public void initializeNumCargoes() {
		for(CargoType cargoType: CargoType.values()){
		this.numOfCargoes.put(cargoType, 0);
		}
	}
	
	public void add4Cars() {
		trainCars.add(new TrainCar(location, heading));
		trainCars.add(new TrainCar(location, heading));
		trainCars.add(new TrainCar(location, heading));
		trainCars.add(new TrainCar(location, heading));
	}
	
	public void register(Observer observer) {
	    if(observerList == null) {
	    	  observerList = new HashSet<Observer>();
	    }
		observerList.add(observer);
	}
	
	public void registerLoadedLevel(Observer observer) {
		this.observerLoadedlevel = observer;
	}
	
	public void notifyAllObservers() {
		for(Observer observer : observerList) {
			observer.notifyChange(this);
		}
	}
	
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
	
	private void moveCars() {
		int lastCar = trainCars.size() - 1;
		
		for(int i = 0; i < lastCar; i++) {
			trainCars.get(i).setLocation(trainCars.get(i+1).getLocation());
			trainCars.get(i).setHeading(trainCars.get(i+1).getHeading());
		}
		trainCars.get(lastCar).setLocation(this.location);
		trainCars.get(lastCar).setHeading(this.heading);
	}
	
	public List<Cargo> dropOff(List<Cargo> requestedCargo) {
		List<Cargo>  cargoDroppedOff = new ArrayList<Cargo>();
		
		for(Cargo cargo: requestedCargo) {
			for(TrainCar trainCar: trainCars) {
				if(trainCar.hasCargo()) {
					if(trainCar.getCargo().getType() == cargo.getType()) {
						Cargo cargoDropped = trainCar.dropCargo();
						decrementNumber(cargoDropped);
						this.observerLoadedlevel.notifyChange(this);
						notifyAllObservers();
						cargoDroppedOff.add(cargoDropped);
						break;
					}
				}
			}
		}
		return cargoDroppedOff; 
	}
	
	private void decrementNumber(Cargo cargo) {
		Integer currentNumOfCargoes = 0; 
		currentNumOfCargoes = numOfCargoes.get(cargo.getType());
		currentNumOfCargoes = currentNumOfCargoes - 1;		
		numOfCargoes.put(cargo.getType(), currentNumOfCargoes);
	}
		
	public void pickUpAt(Station station) {
		LinkedList<Cargo> exportCargoes = new LinkedList<Cargo>(station.getExportCargo());
		if (exportCargoes.size() == 0){
			return;
		}
		if (totalNumofCargos() >= trainCars.size()) {
			throwAndPickOneCargo(station);
			this.observerLoadedlevel.notifyChange(this);
			notifyAllObservers();
			return;
		}
		
		
		for(Cargo cargo: exportCargoes) {			
			for(TrainCar trainCar: trainCars) {
				if(!trainCar.hasCargo()){
					station.sendExportCargo(cargo);
					trainCar.addCargo(cargo);
					incrementNumber(cargo);
					this.observerLoadedlevel.notifyChange(this);
					notifyAllObservers();
					break;
				}
			}
		}	
	}
	
	private int totalNumofCargos(){
		int totalNumOfCargos = 0;
		for (CargoType cargoType:CargoType.values()){
			totalNumOfCargos = totalNumOfCargos + this.numOfCargoes.get(cargoType);
		}
		return totalNumOfCargos;
	}
	
	private void throwAndPickOneCargo(Station station){
		/*for (int i=0;i < trainCars.size();i++){
			System.out.println("throwAndPick-trainCarCargos="+trainCars.get(i).getCargo().getType()+"i="+i);
		}*/
		
		decrementNumber(trainCars.get(0).getCargo());
		for(int trainCarNum = 0; trainCarNum < (trainCars.size()-1); trainCarNum++) {
			TrainCar currentTrainCar = trainCars.get(trainCarNum);
			TrainCar nextTrainCar = trainCars.get(trainCarNum+1);
			Cargo nextCargo = nextTrainCar.dropCargo();
			currentTrainCar.addCargo(nextCargo);
		}
		
		Cargo cargo = station.getFirstExportCargo();
		System.out.println("firstExportCargo="+cargo.getType());
		trainCars.get(trainCars.size()-1).addCargo(cargo);
		incrementNumber(cargo);
	}
	
	private void incrementNumber(Cargo cargo) {
		Integer currentNumOfCargoes = 0; 
		currentNumOfCargoes = numOfCargoes.get(cargo.getType());
		currentNumOfCargoes = currentNumOfCargoes + 1;		
		numOfCargoes.put(cargo.getType(), currentNumOfCargoes);
	}
	
	public void resetTrainCars() {
		for(TrainCar trainCar : trainCars) {
			trainCar.setLocation(this.location);
			trainCar.setHeading(this.heading);
			trainCar.resetCargo();
		}
		notifyAllObservers();
	}
	
	public void resetCargo(){
			for (CargoType cargoType: CargoType.values()){
			this.numOfCargoes.put(cargoType, 0);
			this.observerLoadedlevel.notifyChange(this);
			}
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