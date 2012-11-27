package com.trainpuzzle.model.level;

import java.util.ArrayList;
import java.util.List;

public class Campaign implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String campaignName;
	private List<CampaignLevel> campaignLevels = new ArrayList<CampaignLevel>();
	private int currentLevel;
	
	public Campaign() {
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
	}
	
	public void completeLevel(int levelNumber) {
		campaignLevels.get(levelNumber - 1).isCompleted = true;
		
		if (levelNumber < numberOfLevels()){
			unlockNextLevel(levelNumber);
		}

	}
	private void unlockNextLevel(int currentLevelNumber) {
		unlockLevel(currentLevelNumber + 1);
	}
	
	private void unlockLevel(int levelNumber) {
		campaignLevels.get(levelNumber - 1).isLocked = false;
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
	
    public CampaignLevel getCurrentLevel() {
		return campaignLevels.get(currentLevel-1);
	}
    public int numberOfLevels(){
    	return campaignLevels.size();
    }
    public int getCurrentLevelNumber() {
		return currentLevel;
	}
    
	public void selectLevel(int levelNumber) {
		this.currentLevel = levelNumber;
	}
	
	public void reset() {
		for(CampaignLevel campaignLevel: campaignLevels) {
			campaignLevel.reset();
		}
		campaignLevels.get(0).isLocked = false;
		
	}
}