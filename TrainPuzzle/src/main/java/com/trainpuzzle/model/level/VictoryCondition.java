package com.trainpuzzle.model.level;
import java.util.LinkedList;

import com.trainpuzzle.model.map.*;
/* Future Implementation */
public class VictoryCondition {
	public static final Location NOENDPOINT= new Location(-1,-1); 
	private Location endPoint;
	private LinkedList<Location> locationsMustGoThrough;
	
	VictoryCondition() {
		endPoint = new Location(10,10);
		this.locationsMustGoThrough =new LinkedList<Location>();
		this.locationsMustGoThrough.add(endPoint);
	}
	
	VictoryCondition(Location endPoint,Location[] locationsMustGoThrough) {
		this.endPoint = endPoint;
		for(Location location : locationsMustGoThrough){
			if(!location.equals(endPoint)){
				this.locationsMustGoThrough.add(location);
			}
		}
	}
	
	public void removePassedLocation(Location currentLocation) {
		if(currentLocation != null){
			for(Location location : this.locationsMustGoThrough){
				if(location.equals(currentLocation)){
					this.locationsMustGoThrough.remove(location);
					break;
				}
			}
		}
	}
	public boolean isVictoryConditionSatisfied(Train train) {
		if(endPoint.equals(NOENDPOINT)){
			return locationsMustGoThrough.isEmpty();
		}
		else{
			return locationsMustGoThrough.isEmpty() && endPoint.equals(train.getLocation());
		}
	}
	
}