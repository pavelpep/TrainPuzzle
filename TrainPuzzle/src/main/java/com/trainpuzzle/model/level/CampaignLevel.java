package com.trainpuzzle.model.level;

public class CampaignLevel {
	public boolean isLocked;
	public boolean isCompleted;
	public boolean hasUserSave;
	public int levelNumber;
	
	public CampaignLevel(boolean isLocked, boolean isCompleted, int levelNumber){
		this.isLocked = isLocked;
		this.isCompleted = isCompleted;
		this.hasUserSave = false;
		this.levelNumber = levelNumber;
	}
	
	public void reset(){
		isLocked = true;
		isCompleted = false;
		hasUserSave = false;
	}
}
