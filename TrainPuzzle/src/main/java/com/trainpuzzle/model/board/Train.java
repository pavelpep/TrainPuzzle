package com.trainpuzzle.model.board;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.trainpuzzle.observe.Observable;
import com.trainpuzzle.observe.Observer;

public class Train implements Observable{
 
	private Location location = new Location(0,0); 
	private CompassHeading heading = CompassHeading.EAST;
	Set<Observer> observerList = new HashSet<Observer>();
	Queue<TrainCar> trainCars = new LinkedList<TrainCar>();
	
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
/*
	public void setLocation(int row, int column ) {
		this.location.setRow(row);
		this.location.setColumn(column);
	}
*/
	public void setLocation(Location location) {
		trainCars.poll();
		trainCars.add(new TrainCar(this.location, this.heading));
		this.location = location;
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
		return (TrainCar[]) this.trainCars.toArray();
	}

}