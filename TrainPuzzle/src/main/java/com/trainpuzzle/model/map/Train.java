package com.trainpuzzle.model.map;
import java.util.HashSet;
import java.util.Set;

import com.trainpuzzle.observe.Observable;
import com.trainpuzzle.observe.Observer;

public class Train implements Observable{
 
	private Location location; 
	private CompassHeading heading;
	Set<Observer> observerList = new HashSet<Observer>();
	
	/* Public Interface */
	
	public Train() {
		setLocation(new Location(0,0));
	}
	
	public Train(Location location) {	
		setLocation(location);
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
		this.location = location;
		
	}
	
	public CompassHeading getHeading() {
		return heading;
	}
	
	public void setHeading(CompassHeading heading) {
		this.heading = heading;
		notifyAllObservers();
	}
}