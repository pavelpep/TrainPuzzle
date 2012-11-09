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
    	campaignLevels.add(new CampaignLevel(false,false,1));
    	campaignLevels.add(new CampaignLevel(false,false,2));
    	campaignLevels.add(new CampaignLevel(false,false,3));
    	
	}
    public Campaign(String name) {
    	currentLevel = 1;
    	campaignName = name;
    	campaignLevels.add(new CampaignLevel(false,false,1));
    	campaignLevels.add(new CampaignLevel(true,false,2));
    	campaignLevels.add(new CampaignLevel(true,false,3));
    	
	}
	
	public void completeLevel(int levelNumber) {
		campaignLevels.get(levelNumber - 1).isCompleted = true;
		try 
		{
			unlockLevel(levelNumber);
		}
		finally{}
	}
	
	private void unlockLevel(int levelNumber){
		campaignLevels.get(levelNumber).isLocked = false;
	}
	
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String name) {
		this.campaignName = name;
	}
	public List<CampaignLevel> getCampaignLevels() {
		return campaignLevels;
	}
    public CampaignLevel getCurrentLevel() {
		return campaignLevels.get(currentLevel-1);
	}
    public int getCurrentLevelNumber() {
		return currentLevel;
	}
	public void setCurrentLevelNumber(int currentLevel) {
		this.currentLevel = currentLevel;
	}
	
	
}
