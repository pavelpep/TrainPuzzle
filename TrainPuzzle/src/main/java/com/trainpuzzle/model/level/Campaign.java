package com.trainpuzzle.model.level;

import java.util.ArrayList;
import java.util.List;

import com.trainpuzzle.exception.LevelLockedException;

public class Campaign implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String campaignName;
	private List<CampaignLevel> campaignLevels = new ArrayList<CampaignLevel>();
	private int currentLevel;
	public boolean hasMasterFiles;
	
	public Campaign() {
		hasMasterFiles = false;
    	currentLevel = 1;
    	campaignName = "Campaign1";
    	campaignLevels.add(new CampaignLevel(false, false, 1));
    	campaignLevels.add(new CampaignLevel(false, false, 2));
    	campaignLevels.add(new CampaignLevel(false, false, 3));
    	campaignLevels.add(new CampaignLevel(false, false, 4));
    	campaignLevels.add(new CampaignLevel(false, false, 5));
    	campaignLevels.add(new CampaignLevel(false, false, 6));
    	campaignLevels.add(new CampaignLevel(false, false, 7));
    	campaignLevels.add(new CampaignLevel(false, false, 8));
    	campaignLevels.add(new CampaignLevel(false, false, 9));
    	campaignLevels.add(new CampaignLevel(false, false, 10));
    	campaignLevels.add(new CampaignLevel(false, false, 11));
    	campaignLevels.add(new CampaignLevel(false, false, 12));
	}
	
	public void completeCurrentLevel() {
		completeLevel(currentLevel);
		unlockNextLevel();
	}
	
	public void completeLevel(int levelNumber) {
		getLevel(levelNumber).isCompleted = true;
		
		if (levelNumber < numberOfLevels()){
			unlockLevel(levelNumber + 1);
		}
	}
	
    public int numberOfLevels(){
    	return campaignLevels.size();
    }
	
	public boolean thereIsANextLevel(){
		return (currentLevel < numberOfLevels());
	}
	
	public boolean nextLevelIsUnlocked(){
		if(thereIsANextLevel()){
			return (!getNextLevel().isLocked);
		}else{
			return false;
		}
	}
	
	private void unlockNextLevel() {
		if(thereIsANextLevel()){
			unlockLevel(currentLevel + 1);
		}
	}
	
	private void unlockLevel(int levelNumber) {
		getLevel(levelNumber).isLocked = false;
	}
	
	public String getName() {
		return campaignName;
	}
	
	public void setName(String name) {
		this.campaignName = name;
	}
	
	public List<CampaignLevel> getCampaignLevels() {
		return campaignLevels;
	}
	
    public CampaignLevel getLevel(int levelNumber) {
    	int levelIndex = levelNumber-1;
		return campaignLevels.get(levelIndex);
	}
    
    public CampaignLevel getCurrentLevel() {
		return getLevel(currentLevel);
	}
    
    private CampaignLevel getNextLevel(){
    	return getLevel(currentLevel + 1);
    }
    
    public int getCurrentLevelNumber() {
		return currentLevel;
	}
    
	public void selectLevel(int levelNumber) throws LevelLockedException {
		if(getLevel(levelNumber).isLocked) {
			throw new LevelLockedException("Level " + levelNumber + " is locked.");
		}else{
			this.currentLevel = levelNumber;
		}
	}
	
	public void selectNextLevel() throws LevelLockedException{
		selectLevel(currentLevel + 1);
	}
	
	public void reset() {
		for(CampaignLevel campaignLevel: campaignLevels) {
			campaignLevel.reset();
		}
		unlockLevel(1);
		
		try {
			selectLevel(1);
		} catch (LevelLockedException e) {
			
			e.printStackTrace();
		}
	}
}