package com.trainpuzzle.model.level;

import java.util.LinkedList;

import test.trainpuzzle.model.board.*;

import com.trainpuzzle.model.board.Location;
import com.trainpuzzle.model.board.Train;

/**
 * 
 * @deprecated this class has been replaced with {@link test.trainpuzzle.model.level.victory_condition.VictoryCondition}
 */
@Deprecated
public class VictoryConditionOld {
	public static final Location NOENDPOINT= new Location(-1,-1); 
	private Location endPoint;
	private LinkedList<Location> locationsMustGoThrough = new LinkedList<Location>();
	
	VictoryConditionOld() {
		endPoint = new Location(10,10);
	}
	
	public VictoryConditionOld(Location endPoint) {
		this.endPoint = endPoint;
	}
	
	VictoryConditionOld(Location endPoint,Location[] locationsMustGoThrough) {
		this.endPoint = endPoint;
		for(Location location : locationsMustGoThrough){
			if(!location.equals(endPoint)){
				this.locationsMustGoThrough.add(location);
			}
		}
	}
	
	public void removePassedLocation(Train train) {
		Location currentLocation = train.getLocation();
		for(Location location : this.locationsMustGoThrough){
			if(location.equals(currentLocation)){
				this.locationsMustGoThrough.remove(location);
				break;
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