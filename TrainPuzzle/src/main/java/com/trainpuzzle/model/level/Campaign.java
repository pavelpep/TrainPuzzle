package com.trainpuzzle.model.level;

import com.trainpuzzle.factory.LevelFactory;

public class Campaign implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String campaignName = "Campaign 1";	


	private int firstLevel = 1;
	private int lastLevel = 10;
	private int currentLevel = 1;
	
	public Campaign() {
		
	}
	
	public void completeCurrentLevel() {
		if(currentLevel >= firstLevel && currentLevel <= lastLevel) {
				currentLevel = currentLevel + 1;
		}
	}
	
	public int getCurrentLevel() {
		return this.currentLevel;
	}
	
	public Level loadLevel(int levelNumber) {
		
		Level levelLoaded;
		LevelFactory levelFactory = new LevelFactory();
		
		switch(levelNumber) {
		case 1: levelLoaded = levelFactory.createLevelOne();
				break;
		case 2: levelLoaded = levelFactory.createLevelTwo();
				break;
		case 3: levelLoaded = levelFactory.createLevelThree();
				break;
		default:levelLoaded = levelFactory.createLevelOne();
				break;
		}
		return levelLoaded;
	}


	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
}
