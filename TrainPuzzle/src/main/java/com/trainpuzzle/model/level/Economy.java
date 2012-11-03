package com.trainpuzzle.model.level;

import java.util.HashMap;

import com.trainpuzzle.model.board.*;

import com.trainpuzzle.infrastructure.Images;

public class Economy implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	public static final int NO_LIMIT = -1;
	private int budget = 1000;
	private HashMap<TrackType, Integer> numberOfTrack = new HashMap<TrackType, Integer>();
	
	public Economy(){
		for(TrackType trackType:TrackType.values()){
			numberOfTrack.put(trackType, NO_LIMIT);	
		}
		budget = NO_LIMIT;
	}
	public Economy(int budget) {
		for(TrackType trackType:TrackType.values()){
			numberOfTrack.put(trackType, NO_LIMIT);	
		}
		this.budget = budget;
	}
	
	public void useOnePieceOfTrack(TrackType trackType){
		if(budget != NO_LIMIT) {
			this.budget = budget + trackType.getPrice();
		}
		Integer currentNumOfThisTrack=numberOfTrack.get(trackType);
		if(currentNumOfThisTrack !=NO_LIMIT) {
			currentNumOfThisTrack--;
			numberOfTrack.put(trackType, currentNumOfThisTrack);
		}
		if(trackType.getParent() != null) {
			useOnePieceOfTrack(trackType.getParent());
		}
	}
	
	public void retrunOnePieceOfTrack(TrackType trackType){
		if(budget != NO_LIMIT) {
			this.budget = budget + trackType.getPrice();
		}
		Integer currentNumOfThisTrack=numberOfTrack.get(trackType);
		if(currentNumOfThisTrack !=NO_LIMIT) {
			currentNumOfThisTrack++;
			numberOfTrack.put(trackType, currentNumOfThisTrack);
		}
		if(trackType.getParent() != null) {
			retrunOnePieceOfTrack(trackType.getParent());
		}
	}
	
	public boolean isAvailable(TrackType trackType){
		if(budget == NO_LIMIT || budget >= trackType.getPrice()) {
			if(trackType.getParent() == null){
				return numberOfTrack.get(trackType) > 0 || numberOfTrack.get(trackType) == NO_LIMIT;
			}
			if(numberOfTrack.get(trackType) > 0 || numberOfTrack.get(trackType) == NO_LIMIT) {
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
	public void setTrackLimit(TrackType trackType, int limit) {
		numberOfTrack.put(trackType, limit);
	}
	public Integer getTrackLimit(TrackType trackType) {
		return numberOfTrack.get(trackType);
	}
	public HashMap<TrackType, Integer> getTrackLimits() {
		return this.numberOfTrack;
	}
	
}


	

