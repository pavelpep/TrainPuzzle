package com.trainpuzzle.model.level;

 import java.util.HashMap;

import com.trainpuzzle.infrastructure.Images;

public class Economy implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	public static final int NO_LIMIT = -1;
	private int budget = 1000;
	private int totalTrackLimit = 100;
	private HashMap trackLimit = new HashMap();
	private int singleTrackLimit = 20;
	private int crossTrackLimit = 20;
	public Economy(){
		
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
