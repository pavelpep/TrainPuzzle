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
	}
	public Economy(int[] trackLimit) {
		TrackType[] trackTypes = TrackType.values();
		for(int i = 0;i < 13;i++) {
			if(trackLimit.length <= i) {
				numberOfTrack.put(trackTypes[i], NO_LIMIT);
			}
			else {
				numberOfTrack.put(trackTypes[i], trackLimit[i]);
			}
		}
	}
	
	public void useOnePieceOfTrack(TrackType trackType){
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
		if(trackType.getParent() == null){
			return numberOfTrack.get(trackType) > 0 || numberOfTrack.get(trackType) == NO_LIMIT;
		}
		if(numberOfTrack.get(trackType) > 0 || numberOfTrack.get(trackType) == NO_LIMIT) {
			return isAvailable(trackType.getParent());
		}
		return false;
	}
	
	public int getBudget() {
		return budget;
	}
	public void setBudget(int budget) {
		this.budget = budget;
	}
	
}


	

