package com.trainpuzzle.model.level;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.trainpuzzle.model.board.TrackType;
import com.trainpuzzle.observe.Observable;
import com.trainpuzzle.observe.Observer;

public class Economy implements java.io.Serializable, Observable {
	
	private transient Set<Observer> observerList = new HashSet<Observer>();

	private static final long serialVersionUID = 1L;
	public static final int NO_LIMIT = -1;
	private int budget = 1000;
	private HashMap<TrackType, Integer> numberOfAvailableTrack = new HashMap<TrackType, Integer>();
	
	public Economy() {
		for(TrackType trackType:TrackType.values()) {
			numberOfAvailableTrack.put(trackType, NO_LIMIT);	
		}
		budget = NO_LIMIT;
	}
	public Economy(int budget, HashMap<TrackType, Integer> trackLimits) {
		for(TrackType trackType:TrackType.values()) {
			numberOfAvailableTrack.put(trackType, trackLimits.get(trackType));	
		}
		this.budget = budget;
	}
	
	public void useOnePieceOfTrack(TrackType trackType) {
		Integer currentNumOfThisTrack = numberOfAvailableTrack.get(trackType);

		if(budget != NO_LIMIT) {
			this.budget = budget + trackType.getPrice();
		}
		if(currentNumOfThisTrack != NO_LIMIT) {
			currentNumOfThisTrack--;
			numberOfAvailableTrack.put(trackType, currentNumOfThisTrack);
		}
		if(trackType.getParent() != null) {
			useOnePieceOfTrack(trackType.getParent());
		}
		notifyAllObservers();
	}
	
	public void returnOnePieceOfTrack(TrackType trackType) {
		Integer currentNumOfThisTrack = numberOfAvailableTrack.get(trackType);
		
		if(budget != NO_LIMIT) {
			this.budget = budget + trackType.getPrice();
		}
		if(currentNumOfThisTrack != NO_LIMIT) {
			currentNumOfThisTrack++;
			numberOfAvailableTrack.put(trackType, currentNumOfThisTrack);
		}
		if(trackType.getParent() != null) {
			returnOnePieceOfTrack(trackType.getParent());
		}
		notifyAllObservers();
	}
	
	public boolean isAvailable(TrackType trackType) {
		boolean isRootOfTrackType = trackType.getParent() == null;
		boolean isRootAvailable = numberOfAvailableTrack.get(trackType) > 0 
				|| numberOfAvailableTrack.get(trackType) == NO_LIMIT;
		boolean trackTypeIsAvailable = numberOfAvailableTrack.get(trackType) > 0 
				|| numberOfAvailableTrack.get(trackType) == NO_LIMIT;
		
		if(budget == NO_LIMIT || budget >= trackType.getPrice()) {
			if(isRootOfTrackType) {				
				return isRootAvailable;
			}			
			if(trackTypeIsAvailable) {
				return isAvailable(trackType.getParent());
			}
		}
		return false;
	}
	
	public int getBudget() {
		return budget;
	}
	
	public void setBudget(int budget) {
		this.budget = budget;
	}
	
	public void setNumOfAvailableTrack(TrackType trackType, int limit) {
		numberOfAvailableTrack.put(trackType, limit);
	}
	
	public Integer getNumOfAvailableTrack(TrackType trackType) {
		return numberOfAvailableTrack.get(trackType);
	}
	
	public HashMap<TrackType, Integer> getTrackLimits() {
		return this.numberOfAvailableTrack;
	}
	
	public void register(Observer observer){
		if(observerList == null) {
    	  observerList = new HashSet<Observer>();
		}
		observerList.add(observer);
	}
		
	public void notifyAllObservers() {
		for(Observer observer : observerList) {
			observer.notifyChange(this);
		}
	} 
	
}