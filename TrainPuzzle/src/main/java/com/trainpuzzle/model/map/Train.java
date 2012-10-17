package com.trainpuzzle.model.map;
import java.util.HashSet;
import java.util.Set;

import com.trainpuzzle.observe.Observable;
import com.trainpuzzle.observe.Observer;

public class Train implements Observable{
 
	private Location location; 
	private Heading heading;
	Set<Observer> observerList = new HashSet<Observer>();
	
	/* Public Interface */
	
	public Train() {
		this.location = new Location(0,0);
	}
	
	public Train(int row, int column) {	
		this.location = new Location(row,column);
	}
	
	public void register(Observer observer) {
		observerList.add(observer);
		
	}

	public void notifyAllObservers() {
		for(Observer observer : observerList){
			observer.notifyChange();
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
	
	public Heading getHeading() {
		return heading;
	}
	
	public void setHeading(Heading heading) {
		this.heading = heading;
	}
}