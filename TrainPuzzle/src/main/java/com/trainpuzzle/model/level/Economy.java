package com.trainpuzzle.model.level;

import java.util.HashMap;

import com.trainpuzzle.model.board.*;

import com.trainpuzzle.infrastructure.Images;

public class Economy implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	public static final int NO_LIMIT = -1;
	private int budget = 1000;
	private HashMap<TrackType, Integer> numOfIndividualTrackPlaced = new HashMap<TrackType, Integer>();
	
	public Economy(){
		for(TrackType trackType:TrackType.values()){
			numOfIndividualTrackPlaced.put(trackType, 0);
			
		}
	}
	
	public void useOnePieceOfTrack(TrackType trackType){
		Integer currentNumOfThisTrack=numOfIndividualTrackPlaced.get(trackType);
		currentNumOfThisTrack++;
		numOfIndividualTrackPlaced.put(trackType, currentNumOfThisTrack);
	}
	
	public void retrunOnePieceOfTrack(TrackType trackType){
		Integer currentNumOfThisTrack=numOfIndividualTrackPlaced.get(trackType);
		currentNumOfThisTrack--;
		numOfIndividualTrackPlaced.put(trackType, currentNumOfThisTrack);
		
	}
	
	public boolean isAvailable(TrackType trackType){
	return true;	
	}
	
	public int getBudget() {
		return budget;
	}
	public void setBudget(int budget) {
		this.budget = budget;
	}
	
}


	

