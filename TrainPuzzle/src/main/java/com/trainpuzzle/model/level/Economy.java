package com.trainpuzzle.model.level;

import java.util.HashMap;

import com.trainpuzzle.model.board.*;

import com.trainpuzzle.infrastructure.Images;

public class Economy implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	public static final int NO_LIMIT = -1;
	private int budget = 1000;
	private int totalTrackLimit = 100;
	private HashMap<TrackType, Integer> individualTrackLimit = new HashMap<TrackType, Integer>();
	private HashMap<TrackType, Integer> numOfIndividualTrackPlaced = new HashMap<TrackType, Integer>();
	private int singleTrackLimit = 20;
	private int crossTrackLimit = 20;
	
	public Economy(){
		for(TrackType trackType:TrackType.values()){
			individualTrackLimit.put(trackType, 10);
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
	public int getTotalTrackLimit() {
		return totalTrackLimit;
	}
	public void setTotalTrackLimit(int totalTrackLimit) {
		this.totalTrackLimit = totalTrackLimit;
	}
	
	public int getSingleTrackLimit() {
		return singleTrackLimit;
	}
	public void setSingleTrackLimit(int singleTrackLimit) {
		this.singleTrackLimit = singleTrackLimit;
	}
	public int getCrossTrackLimit() {
		return crossTrackLimit;
	}
	public void setCrossTrackLimit(int crossTrackLimit) {
		this.crossTrackLimit = crossTrackLimit;
	}
}
